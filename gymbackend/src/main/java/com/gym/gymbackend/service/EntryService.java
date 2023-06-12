package com.gym.gymbackend.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gym.gymbackend.domain.Entry;
import com.gym.gymbackend.domain.Member;
import com.gym.gymbackend.domain.dto.EntryDto;
import com.gym.gymbackend.repository.EntryRepository;
import com.gym.gymbackend.repository.MemberRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
public class EntryService {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntryRepository entryRepository;

    @PersistenceContext
    EntityManager em;

    @Transactional(readOnly = true)
    public List<EntryDto> 출입조회(){
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT DATE_FORMAT(createDate, '%Y년 %m월 %d일 %H시 %i분 %s초'), ");
        sb.append("(SELECT memberName FROM member WHERE member.id = entry.memberId), ");
        sb.append("(SELECT phone FROM member WHERE member.id = entry.memberId) FROM entry ");
        sb.append("ORDER BY createDate DESC");

        Query query = em.createNativeQuery(sb.toString());

        List<EntryDto> result = query.getResultList();
        return result;
    }


    @Transactional
    public String 출입체크(HashMap<String, Object> req) {
        Entry entry = new Entry();
        int id = Integer.parseInt(req.get("id").toString());
        Member member = memberRepository.findById(id).get();

        int count = entryRepository.mCountMemberIdToday(id); // 오늘 이미 출입 체크 했는지 확인
        if (count > 0) {
            return "오늘 이미 출입체크를 하셨습니다.";
        } else {
            entry.setMember(member);
            entryRepository.save(entry);
            return "성공";
        }
    }
}
