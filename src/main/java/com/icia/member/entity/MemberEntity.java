package com.icia.member.entity;


import com.icia.member.dto.MemberSaveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="member_table")
public class MemberEntity { //빨간줄 이유 : entity를 사용할때 pk를 무조건 가지고 있어야하는데 없어서
    // pk지정
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //오토 인크리먼트트
    @Column(name="member_id") // 별도 컬럼이름 지정
    // Long null을 생각해서 씀.
    private Long id;

    // memberEmail: 크기50,  unique
    @Column(length = 50, unique = true)
    private String memberEmail; //카멜케이스를 쓰면 디비에서 _를 붙인다.

    //memberPassword: 크기20
    @Column(length = 20)
    private String memberPassword;

    // 별도의 지정할게 없으면 @column 생략 가능 / default 크기 255로 지정
    private String memberName;

    /*
           DTO클래스 객체를 전달받아 Entity 클래스 필드값으로 세팅하고
           Entity 객체를 리턴하는 메서드 선언

            static 메서드(정적메서드): 클래스 메서드, 객체를 만들지 않고도 바로 호출 가능
            스테틱이 없을 경우 객체를 생성해야한다.
    */
    // 팩토리 메서드 => 해당 메서드에 static
    // 생성자 노출 x, 리턴사용가능, 메서드이기 때문에 이름을 마음대로 설정 가능.
    public static MemberEntity saveMember(MemberSaveDTO m){
        MemberEntity memberEntity = new MemberEntity();// 우변을 먼저 사용하면 좌변을 자동완성 alt + enter
        memberEntity.setMemberEmail(m.getMemberEmail());
        memberEntity.setMemberPassword(m.getMemberPassword());
        memberEntity.setMemberName(m.getMemberName());
        return memberEntity;
    }
}
