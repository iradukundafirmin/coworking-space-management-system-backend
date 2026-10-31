package com.coworking.coworkingspacemanagementsystem.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.coworking.coworkingspacemanagementsystem.entity.Member;
import com.coworking.coworkingspacemanagementsystem.repository.MemberRepository;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public String saveMember(Member member) {
        memberRepository.save(member);
        return "Member saved successfully";
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> getMemberById(UUID id) {
        return memberRepository.findById(id);
    }

    public Optional<Member> updateMember(UUID id, Member member) {
        if (memberRepository.existsById(id)) {
            member.setId(id);
            return Optional.of(memberRepository.save(member));
        }
        return Optional.empty();
    }

    public String deleteMember(UUID id) {
        Optional<Member> member = memberRepository.findById(id);

        if (member.isPresent()) {
            memberRepository.delete(member.get());
            return "Member deleted successfully";
        }

        return "The member with that id does not exist";
    }
}