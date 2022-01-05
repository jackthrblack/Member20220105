package com.icia.member.controller;

import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member/*")
@RequiredArgsConstructor // final 키워드가 붙은 필드만으로 생성자를 만들어줌
public class MemberController {

    private final MemberService ms;


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("save")
    public String save_form(Model model) {
        model.addAttribute("member", new MemberSaveDTO());
        return "member/save";
    }

    // 여기서 @notblank를 적용해줘야한다.
    @PostMapping("member/save")
    public String save(@Validated @ModelAttribute("member") MemberSaveDTO m, BindingResult bindingResult) {
        System.out.println(m);
        System.out.println("m = " + m);

        if(bindingResult.hasErrors()){
            return "member/save";
        }

        ms.save(m);

        return "redirect:/member/login";
    }

    @GetMapping("member/login")
    public String login_form(Model model) {
        model.addAttribute("login", new MemberLoginDTO());
        return "member/login";
    }

    @PostMapping("member/login")
    public String login(@Validated @ModelAttribute("login") MemberLoginDTO memberLoginDTO, BindingResult bindingResult) {
        System.out.println("memberLoginDTO = " + memberLoginDTO + ", bindingResult = " + bindingResult);
        if(bindingResult.hasErrors()){
            return "member/login";
        }
        return "index";
    }
}
