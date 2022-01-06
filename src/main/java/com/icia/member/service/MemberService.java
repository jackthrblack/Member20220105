package com.icia.member.service;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;

import java.util.List;

public interface MemberService {
    Long save(MemberSaveDTO m);

    MemberDetailDTO findById(Long memberId);

    boolean login(MemberLoginDTO memberLoginDTO);

    List<MemberDetailDTO> findAll();
}
