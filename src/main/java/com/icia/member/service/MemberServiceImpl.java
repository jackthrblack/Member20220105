package com.icia.member.service;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.entity.MemberEntity;
import com.icia.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository mr;

    @Override
    public Long save(MemberSaveDTO m) {
        /*
            1. MemberSaveDTO -> MemberEntity에 옮기기(MemberEntity의 saveMember 메서드)
            2. MemberRepository의 save 메서드 호출하면서 MemberEntity 객체 전달
        */
        MemberEntity memberEntity = MemberEntity.saveMember(m);
        // 사용자가 입력한 이메일 중복체크
        MemberEntity emailCheckResult = mr.findByMemberEmail(m.getMemberEmail());
        // 이메일 중복체크 결과가 null이 아니라면 예외를 발생시킴
        // 예외종류 : IllegalStateException, 예외메시지: 중복된 이메일입니다!!
        if(emailCheckResult != null){
            throw new IllegalStateException("중복된 이메일입니ㅏㄷ!!");
            // throw 예외처리하는 방법 => 나를 호출한 곳에다가 예외를 떠넘기는 의미
        }
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

    @Override
    public boolean login(MemberLoginDTO memberLoginDTO) {
        // 1. 사용자가 입력한 이메일을 조건으로 DB에서 조회(select * from member_table where member_email=?)
        // pk값이 아닌걸로 할때 이렇게 해줌 => 별도의 메서드를 레파지토리에 정의해줘야함.
        MemberEntity memberEntity = mr.findByMemberEmail(memberLoginDTO.getMemberEmail());

        // 2. 비밀번호 일치여부 확인
        if (memberEntity != null) {
            if (memberLoginDTO.getMemberPassword().equals(memberEntity.getMemberPassword())) {
                return true;
            } else {
                return false;
            }
        } else{
            return false;
        }
    }

    @Override
    public List<MemberDetailDTO> findAll() {
        List<MemberEntity> memberEntityList = mr.findAll();
        //List<MemberEntity> -> List<MemberDetailDTO>
        List<MemberDetailDTO> memberDetailDTOList = new ArrayList<>();
        for (MemberEntity e: memberEntityList) {
            // Entity 객체를 MemberDetailDTO로 변환하고 memberList에 담음.
            memberDetailDTOList.add(MemberDetailDTO.toMemberDetailDTO(e));
            // MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDTO(e);
            // memberList.add(memberDetailDTO);
        }
        return memberDetailDTOList;
    }
}
