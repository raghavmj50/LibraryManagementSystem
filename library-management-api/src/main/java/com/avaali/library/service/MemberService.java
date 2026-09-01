package com.avaali.library.service;

import com.avaali.library.dto.request.MemberRequest;
import com.avaali.library.dto.response.MemberResponse;
import com.avaali.library.entity.Member;
import com.avaali.library.exception.DuplicateResourceException;
import com.avaali.library.exception.LoanLimitExceededException;
import com.avaali.library.exception.MemberHasActiveLoansException;
import com.avaali.library.exception.MemberNotFoundException;
import com.avaali.library.mapper.MemberMapper;
import com.avaali.library.repository.LoanRepository;
import com.avaali.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;

    public MemberResponse createMember(MemberRequest request) {

        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "Member with this email already exists"
            );
        }

        Member member = MemberMapper.create(request);

        member.setRegisteredOn(LocalDate.now());

        Member savedMember = memberRepository.save(member);

        Long activeLoanCount =
                loanRepository.countByMemberIdAndReturnDateIsNull(
                        savedMember.getId()
                );

        return MemberMapper.doResponse(
                savedMember,
                activeLoanCount
        );
    }

    @Cacheable(value = "Memeber" ,
    key=" 'name=' +#name + 'email=' + #email + 'size=' + #pageable.pageSize + 'page=' + #pageable.pageNumber + 'sort=' + #pageable.sort ")
    public Page<MemberResponse> getMembers(
            String name,
            String email,
            Pageable pageable) {

        Page<Member> members;

        if ((name == null || name.isBlank())
                && (email == null || email.isBlank())) {

            members = memberRepository.findAll(pageable);

        } else if (email == null || email.isBlank()) {

            members = memberRepository
                    .findByNameContainingIgnoreCase(
                            name,
                            pageable
                    );

        } else if (name == null || name.isBlank()) {

            members = memberRepository
                    .findByEmailContainingIgnoreCase(
                            email,
                            pageable
                    );

        } else {

            members = memberRepository
                    .findByNameContainingIgnoreCaseAndEmailContainingIgnoreCase(
                            name,
                            email,
                            pageable
                    );
        }

        return members.map(member -> {

            Long activeLoanCount =
                    loanRepository.countByMemberIdAndReturnDateIsNull(
                            member.getId()
                    );

            return MemberMapper.doResponse(
                    member,
                    activeLoanCount
            );
        });
    }

    @Cacheable(value="Member" , key = "#id")
    public MemberResponse getMemberById(Integer id) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() ->
                        new MemberNotFoundException(
                                "Member not found"
                        ));

        Long activeLoanCount =
                loanRepository.countByMemberIdAndReturnDateIsNull(id);

        return MemberMapper.doResponse(
                member,
                activeLoanCount
        );
    }

    public MemberResponse updateMember(
            Integer id,
            MemberRequest request) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() ->
                        new MemberNotFoundException(
                                "Member not found"
                        ));

        // Check whether the new email belongs to another member
        if (!member.getEmail().equalsIgnoreCase(request.getEmail())
                && memberRepository.existsByEmail(request.getEmail())) {

            throw new DuplicateResourceException(
                    "Member with this email already exists"
            );
        }

        member.setName(request.getName());
        member.setEmail(request.getEmail());
        member.setPhone(request.getPhone());

        Member updatedMember = memberRepository.save(member);

        Long activeLoanCount =
                loanRepository.countByMemberIdAndReturnDateIsNull(
                        updatedMember.getId()
                );

        return MemberMapper.doResponse(
                updatedMember,
                activeLoanCount
        );
    }

    public void deleteMember(Integer id) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() ->
                        new MemberNotFoundException(
                                "Member not found"
                        ));

        Long activeLoanCount =
                loanRepository.countByMemberIdAndReturnDateIsNull(id);

        if (activeLoanCount > 0) {
            throw new MemberHasActiveLoansException(
                    "Member has active loans"
            );
        }

        memberRepository.delete(member);
    }
}