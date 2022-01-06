package com.icia.member;

// 테스트를 디펜던시해주는 것 junit => 자바에서 제공
import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.entity.MemberEntity;
import com.icia.member.repository.MemberRepository;
import com.icia.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
// 서비스클래스에다가 만든 메서드들이 내가 원하는 동작을하는지 테스트하는 목적
// 테스트는 독립적이여야 한다.
public class MemberTest {
    /*
    * MemberServiceImpl.save() 메서드가 잘 동작하는지 테스트
    *
    * 회원가입테스트
    * save.html 에서 회원정보 입력 후 가입클릭
    * DB 확인
    * */

    @Autowired
    private MemberService ms;

    @Autowired
    private MemberRepository mr;

    @Test
    @Transactional // 테스트 시작할 때 새로운 트랜잭션 시작
    @Rollback
    @DisplayName("회원가입 테스트")
    public void memberSaveTest(){
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO();
        memberSaveDTO.setMemberEmail("테스트회원이메일1");
        memberSaveDTO.setMemberPassword("테스트비번1");
        memberSaveDTO.setMemberName("테스트이름1");

        ms.save(memberSaveDTO);
    }

    @Test
    @Transactional // 테스트 시작할 때 새로운 트랜잭션 시작
    @Rollback // insert 했던것들을 다시 취소함 => 이걸 안하면 테스트는 디비에 남아 다시 종속적인 관계로 돌아온다.
    @DisplayName("회원조회 테스트")
    public void memberDetailTest(){
        // given: 테스트 조건 설정
            // 1. 새로운 회원을 등록하고 해당회원의 번호르(member_id)를 가져옴.
        // 1.1 테스트용 데이터 객체 생성
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO("조회용회원이메일","조회용회원비번","조회용회원이름");
        // 1.2 테스용 데이터를 DB에 저장하고 member_id를 가져옴.
        Long memberId = ms.save(memberSaveDTO);

        // when: 테스트 수행
            // 2. 1번에서 가져온 회원번호를 가지고 조회 기능 수행
        MemberDetailDTO findMember = ms.findById(memberId);

        // then: 테스트 결과 검증
            // 3. 1번에서 가입한 회원의 정보와 2번에서 회원의 정보가 일치하면 테스트 통과 일치하지 않으면 테스트 시류ㅐ
        // memberSaveDTO의 이메일값과 findMember의 이메일 값이 일치하는지 확인.
        assertThat(memberSaveDTO.getMemberEmail()).isEqualTo(findMember.getMemberEmail());
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("로그이네스트")
    public void loginTest(){
        // 1. 새로운 회원을 만든 후 해당 회원 이메일을 가져온다
        /*MemberSaveDTO memberSaveDTO = new MemberSaveDTO();
        memberSaveDTO.setMemberEmail("로그인이메일1");
        memberSaveDTO.setMemberPassword("로그인비번1");

        ms.save(memberSaveDTO);

        // 2. 1번에서 가져온 회원 이메일 가지고 로그인 기능 수행
        MemberLoginDTO memberLoginDTO = new MemberLoginDTO();
        MemberEntity memberEntity = mr.findByMemberEmail(memberLoginDTO.getMemberEmail());
        // 3. 1번에서 가입한 회원의 이메일과 2번에서 회원의 정보가 일치하면 통과
        assertThat(memberLoginDTO.getMemberPassword()).isEqualTo(memberEntity.getMemberPassword());*/


        //1. 테스트용 회원가잆(MemberSaveDTO)
        // 2. 로그인용 객체 생성(MemberLoginDTO)
        // 1.2. 수행할 때 도일한 이메일, 패스워드를 사용하도록함.
        // 3. 로그인 수행
        // 4. 로그인결과가 true인지 확인

        // given
       String testMemberEmail = "로그인테스트이메일";
       String testMemberPassword = "로그인테스트 비밀번호";
       String testMemberName = "로그인테스트이름";

       // 1.
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO(testMemberEmail,testMemberPassword,testMemberName);
        ms.save(memberSaveDTO);

        //when
        //2.
        MemberLoginDTO memberLoginDTO = new MemberLoginDTO(testMemberEmail, testMemberPassword);
        boolean loginResult = ms.login(memberLoginDTO);

        //then
        assertThat(loginResult).isEqualTo(true);
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("회원목록 테스트")
    public void memberListTest(){
        /*
        *   member_table에 아무 데이터가 없는 상태에서
        *   3명의 회원을 가입시킨 후 memberList 사이즈를 조회하여 3이면 테스트 통과과
       * */

       /* MemberSaveDTO memberSaveDTO = new MemberSaveDTO("리스트회원1", "리스트회원pw1", "리스트회원이름1");
        ms.save(memberSaveDTO);
        memberSaveDTO = new MemberSaveDTO("리스트회원2", "리스트회원pw2", "리스트회원이름2");
        ms.save(memberSaveDTO);
        memberSaveDTO = new MemberSaveDTO("리스트회원3", "리스트회원pw3", "리스트회원이름3");
        ms.save(memberSaveDTO);*/
        // save를 마지막에 하나만 놔두면 마지막것만 세이브한다. 왜냐하면 덮어씌운줄 알고

      /*  for(int i=0; i<=3; i++){
            MemberSaveDTO memberSaveDTO = new MemberSaveDTO("리스트회원"+i, "리스트회원pw"+i, "리스트회원이름"+i);
            ms.save(memberSaveDTO);
        }*/

        // IntStream, Arrow Function(화살표함수)
        // reangeClosed(시작 값, 끝 값)
        IntStream.rangeClosed(1,3).forEach(i->{
            ms.save(new MemberSaveDTO("리스트회원"+i, "리스트회원pw"+i, "리스트회원이름"+i));
        });

        List<MemberDetailDTO> list = ms.findAll();
        assertThat(list.size()).isEqualTo(3);
    }
}
