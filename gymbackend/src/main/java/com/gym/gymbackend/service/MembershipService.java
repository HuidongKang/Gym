package com.gym.gymbackend.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gym.gymbackend.domain.Member;
import com.gym.gymbackend.domain.Membership;
import com.gym.gymbackend.repository.MemberRepository;
import com.gym.gymbackend.repository.MembershipRepository;

@Service
public class MembershipService {

    @Autowired
    MembershipRepository membershipRepository;
    @Autowired
    MemberRepository memberRepository;

    @Transactional
    public String 회원권생성() {
        // 회원과의 연관관계 임시로 제거
        HashMap<Long, String> id = new HashMap<>();
        List<Member> members = memberRepository.findAll();

        // 연관관계 제거 전 모든 회원들의 회원권 임시로 해쉬맵에 저장
        for(Member m : members){
            Long memberId = m.getId();
            String membershipName = "1개월";
            if(m.getMembership() != null){
                membershipName= m.getMembership().getMembershipName();
            }
            id.put(memberId, membershipName);

            m.setMembership(null);
        }

        // 기존 회원권 모두 삭제, ID 자동증가 초기화
        membershipRepository.deleteAll();
        membershipRepository.mResetId();

        // 회원권 생성
        Membership[] memberships = new Membership[4];
        memberships[0] = new Membership();
        memberships[1] = new Membership();
        memberships[2] = new Membership();
        memberships[3] = new Membership();

        // 회원권 이름 설정
        memberships[0].setMembershipName("1개월");
        memberships[1].setMembershipName("3개월");
        memberships[2].setMembershipName("6개월");
        memberships[3].setMembershipName("1년");

        // 기간 설정
        memberships[0].setMonth(1);
        memberships[1].setMonth(3);
        memberships[2].setMonth(6);
        memberships[3].setMonth(12);

        // 가격 설정
        memberships[0].setPrice("100000");
        memberships[1].setPrice("220000");
        memberships[2].setPrice("400000");
        memberships[3].setPrice("700000");

        // DB에 저장
        try {
            membershipRepository.save(memberships[0]);
            membershipRepository.save(memberships[1]);
            membershipRepository.save(memberships[2]);
            membershipRepository.save(memberships[3]);
        } catch (Exception e) {
            return "회원권 생성 실패";
        }

        // 회원과의 연관관계 다시 설정
        for(Member m : members){
            String membershipName = id.get(m.getId());
            Membership membership = membershipRepository.findByMembershipName(membershipName);
            m.setMembership(membership);
        }

        return "성공";
    }

    @Transactional(readOnly = true)
    public int 회원권생성확인() {
        return membershipRepository.findAll().size();
    }
}
