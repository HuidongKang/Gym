package com.gym.gymbackend.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String memberName;
    private String gender;
    private int age;
    private String address;
    private String job;
    @Column(nullable = false, unique = true)
    private String phone;
    @Column(nullable = false)
    private String password;
    
    @JoinColumn(name = "membershipId")
    @ManyToOne
    private Membership membership;
    @Column(nullable = false)
    private String rentClothes;
    private LocalDateTime expirationDate;
    private LocalDateTime createDate;
}