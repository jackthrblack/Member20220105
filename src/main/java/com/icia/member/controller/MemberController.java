package com.icia.member.controller;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

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

        try{
            ms.save(m);
        } catch (IllegalStateException e){
            // e.getMessage()에는 서비스에서 지정한 예외메시지가 담겨있음
            bindingResult.reject("emailCheck", e.getMessage());
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
    public String login(@Validated @ModelAttribute("login") MemberLoginDTO memberLoginDTO, BindingResult bindingResult,
                        HttpSession session) {
        System.out.println("memberLoginDTO = " + memberLoginDTO + ", bindingResult = " + bindingResult);

        // 각 각의 오류들을 하나씩 보여준것.
        if(bindingResult.hasErrors()){
            return "member/login";
        }

        boolean loginResult = ms.login(memberLoginDTO);

        // if(ms.login(memberLoginDTO)){}
        if(loginResult){
            session.setAttribute("loginEmail", memberLoginDTO.getMemberEmail());
            return "redirect:/member/findAll";
        }else{
            // 로그인 결과를 글로벌오류(Global Error) => 특정 필드만 체크된 결과가 아니라 전체적인 에러를 처리할때 사용
            bindingResult.reject("loginFail", "이메일 또는 비밀번호가 틀립니다!! 틀리다고!! 틀려!!!");
            return "member/login";
        }
            }

    // 상세조회
    // /member/2, /member/15 => /member/{memberId}
    //@PathVariable => 주소(경로)상에 있는 변수를 가져와서 사용할때
    @GetMapping("{memberId}")
    public String findById(@PathVariable("memberId") Long memberId, Model model){
        System.out.println("memberId = " + memberId );
        MemberDetailDTO member=ms.findById(memberId);
        model.addAttribute("member",member);
        return "member/detail";
    }

    // 목록출력(/member)
    @GetMapping
    public String findAll(Model model){
        List<MemberDetailDTO>memberList=ms.findAll();
        model.addAttribute("memberList",memberList);
        return "member/findAll";
    }
}
