package com.icia.member.service;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.entity.MemberEntity;
import com.icia.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
   private final MemberRepository mr;

    @Override
    public Long save(MemberSaveDTO m) {
        /*
            1. MemberSaveDTO -> MemberEntity에 옮기기(MemberEntity의 saveMember 메서드)
            2. MemberRepository의 save 메서드 호출하면서 MemberEntity 객체 전달
        */
        MemberEntity memberEntity = MemberEntity.saveMember(m);

        mr.save(memberEntity).getId(); // 리턴타입이 void라도 .get을해주면 그 값을 가져올 수 있다.
        return mr.save(memberEntity).getId();
    }

    @Override
    public MemberDetailDTO findById(Long memberId) {
        /*
            1. MemberRepository로 부터 해당 회원의 정보를 MemberEntity로 가져옴
            2. MemberEntity를 MemberDetailDTO로 바꿔서 컨트롤러로 리턴.
        */
        // .findBuId(pk값)
        // Optional -> 자바 유틸패키지에서 소속되어있는 클래스
        // 널포인트를 감싸서준다
        // .get() 옵셔널을 벗겨주는 메서드

        // 1번
        MemberEntity member = mr.findById(memberId).get();

        // 2번
        MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDTO(member);
        System.out.println("memberDetailDTO = " + memberDetailDTO);
        System.out.println("memberDetailDTO.toString() = " + memberDetailDTO.toString());
        return memberDetailDTO;
    }
}
