package com.gym.gymbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gym.gymbackend.domain.Entry;
import com.gym.gymbackend.domain.Member;

public interface EntryRepository extends JpaRepository<Entry, Integer>{
    
    int countByMember(Member member);

    @Query(value="SELECT COUNT(memberId) FROM entry WHERE DATE(createDate) = DATE(NOW()) AND memberId = :memberId", nativeQuery = true)
    int mCountMemberIdToday(int memberId);
}
