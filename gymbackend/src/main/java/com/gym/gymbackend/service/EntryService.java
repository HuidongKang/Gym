package com.gym.gymbackend.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gym.gymbackend.domain.Entry;
import com.gym.gymbackend.domain.Member;
import com.gym.gymbackend.repository.EntryRepository;
import com.gym.gymbackend.repository.MemberRepository;

@Service
public class EntryService {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntryRepository entryRepository;

    @Transactional
    public String 출입체크(HashMap<String, Object> req) { // 출입체크 성공: 1, 실패: 0
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
