package com.icia.member.repository;

import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<해당entity클래스이름, pk타입>
// JpaRepository를 상속받으면 @Repository는 필요 없이 알아서 해준다.
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {


}
