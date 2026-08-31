package com.avaali.library.mapper;

import com.avaali.library.dto.request.MemberRequest;
import com.avaali.library.dto.response.MemberResponse;
import com.avaali.library.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public static Member create(MemberRequest request) {

        Member member = new Member();

        member.setName(request.getName());
        member.setEmail(request.getEmail());
        member.setPhone(request.getPhone());

        return member;
    }

    public static MemberResponse doResponse(
            Member savedMember,
            Long activeLoanCount) {

        MemberResponse response = new MemberResponse();

        response.setId(savedMember.getId());
        response.setName(savedMember.getName());
        response.setEmail(savedMember.getEmail());
        response.setPhone(savedMember.getPhone());
        response.setRegisteredOn(savedMember.getRegisteredOn());
        response.setActiveLoanCount(activeLoanCount);

        return response;
    }
}