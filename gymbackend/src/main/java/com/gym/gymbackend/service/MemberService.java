package com.gym.gymbackend.service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gym.gymbackend.domain.Member;
import com.gym.gymbackend.domain.Membership;
import com.gym.gymbackend.domain.dto.MemberInfoDto;
import com.gym.gymbackend.repository.EntryRepository;
import com.gym.gymbackend.repository.MemberRepository;
import com.gym.gymbackend.repository.MembershipRepository;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MembershipRepository membershipRepository;
    @Autowired
    private EntryRepository entryRepository;

    @Transactional
    public String 회원권연장(HashMap<String, Object> req){
        int memberId = Integer.parseInt(req.get("id").toString());
        String membershipName = req.get("membership").toString();
        
        // 날짜 추출
        String startYear = req.get("startYear").toString();
        String startMonth = req.get("startMonth").toString();
        String startDay = req.get("startDay").toString();
        LocalDateTime startDate = LocalDateTime.of(Integer.parseInt(startYear), Integer.parseInt(startMonth), Integer.parseInt(startDay), 0, 0);

        Member member = memberRepository.findById(memberId).get();

        Membership membership = membershipRepository.findByMembershipName(membershipName);
        
        
        // 더티체킹 => DB 반영
        member.setCreateDate(startDate);
        LocalDateTime expDate = startDate.plusMonths(membership.getMonth());
        member.setExpirationDate(expDate);
        member.setMembership(membership);
        
        return "성공";
    }

    @Transactional(readOnly = true)
    public boolean 로그인중복체크(String password){
        int count = memberRepository.countByPassword(password);
        if(count > 1){
            return true;
        }
        else{
            return false;
        }
    }

    @Transactional(readOnly = true)
    public MemberInfoDto 회원정보불러오기(int memberId){
        Member member = memberRepository.findById(memberId).get();
        MemberInfoDto memberInfoDto = new MemberInfoDto();

        // 이름, 옷 대여 여부 설정
        memberInfoDto.setName(member.getMemberName());
        memberInfoDto.setClothes(member.getRentClothes());

        // 만료 날짜, 등록 날짜 설정
        String createDate = member.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        String expirationDate = member.getExpirationDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        memberInfoDto.setCreateDate(createDate);
        memberInfoDto.setExpirationDate(expirationDate);

        // 남은 일수 설정
        String restDays = Long.toString(ChronoUnit.DAYS.between(LocalDateTime.now(), member.getExpirationDate()) + 1);
        memberInfoDto.setRestDays(restDays);

        // 회원권 설정
        String membershipName = membershipRepository.mSelectMembershipName(member.getMembership().getId());
        memberInfoDto.setMembershipName(membershipName);

        // 출석 일수 설정
        int checkCount = entryRepository.countByMember(member);
        memberInfoDto.setCheckCount(checkCount);

        return memberInfoDto;
    }

    @Transactional(readOnly=true)
    public List<Member> 로그인(String password){
        return memberRepository.findByPassword(password); 
    }

    @Transactional(readOnly=true)
    public Member 중복로그인(String password, Long id){
        return memberRepository.findByPasswordAndId(password, id);
    }
    
    @Transactional
    public String 회원가입(HashMap<String, Object> req) {
        Member member = new Member();
        member.setMemberName(req.get("memberName").toString());
        if (req.get("age") != null) {
            member.setAge(Integer.parseInt(req.get("age").toString()));
        }
        member.setJob(req.get("job").toString());
        member.setAddress(req.get("address").toString());
        member.setGender(req.get("gender").toString());
        member.setRentClothes(req.get("rentClothes").toString());

        // 휴대폰 번호 중복 체크
        String phone = req.get("phone").toString();
        Member tempMember = memberRepository.findByPhone(phone);
        if(tempMember == null){
            member.setPhone(phone);
        } else{
            return "중복된 번호입니다.";
        }

        // 회원권 id 추출 후 설정
        String membershipName = req.get("membership").toString();
        int membershipId;

        switch (membershipName) {
            case "1개월":
                membershipId = 1;
                break;
            case "3개월":
                membershipId = 2;
                break;
            case "6개월":
                membershipId = 3;
                break;
            case "1년":
                membershipId = 4;
                break;
            default:
                membershipId = 1;
        }
        try {
            Membership membership = membershipRepository.findById(membershipId).get();

            member.setMembership(membership);
            // createDate, expiredDate 설정
            member.setCreateDate(LocalDateTime.now());
            member.setExpirationDate(member.getCreateDate().plusMonths(membership.getMonth()));
        } catch (Exception e) {
            return "회원가입 실패: "+e.getMessage();
        }

        // 비밀번호를 휴대폰 뒷 4자리로
        String password = req.get("phone").toString().substring(req.get("phone").toString().length() - 4);
        member.setPassword(password);

        // createDate는 DB 생길 때 생성, expiredDate는 createDate가 생긴 후 만들어야 함
        // String expiredString = req.get("createDate").toString();
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        // LocalDateTime dateTime = LocalDateTime.parse(expiredString, formatter);
        // member.setExpirationDate(dateTime.plusMonths(1));

        memberRepository.save(member);
        return "성공";
    }
}
