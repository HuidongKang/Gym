package com.gym.gymbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gym.gymbackend.domain.Membership;

public interface MembershipRepository extends JpaRepository<Membership, Integer>{
    
    @Query(value="SELECT membershipName FROM membership WHERE id = :membershipId", nativeQuery = true)
    String mSelectMembershipName(int membershipId);

    @Modifying
    @Query(value="ALTER TABLE membership AUTO_INCREMENT=1", nativeQuery = true)
    void mResetId();

    Membership findByMembershipName(String membershipName);
}
