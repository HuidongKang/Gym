package com.gym.gymbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gym.gymbackend.domain.Member;

// JpaRepository를 상속 받으면 어노테이션 없이 IoC 자동 등록
public interface MemberRepository extends JpaRepository<Member, Integer>{

    @Query(value="SELECT id FROM member WHERE DATE(expirationDate) = DATE(NOW())", nativeQuery = true)
    List<Integer> mSelectExpMember();
    
    List<Member> findByPassword(String password);

    int countByPassword(String password);

    Member findByPasswordAndId(String password, Long id);

    Member findByPhone(String phone);
}