package com.icia.member.repository;

import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<해당entity클래스이름, pk타입>
// JpaRepository를 상속받으면 @Repository는 필요 없이 알아서 해준다.
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
        // 이메일을 조건으로 회원 조회
        // (select * from member_table where member_email=?)

        /*
        *   메서드 리턴타입: MemberEntity
        *   메서드 이름: findByMemberEmail
        *   메서드 매개변수: String memberEmail
        * */

    MemberEntity findByMemberEmail(String memberEmail);
}
