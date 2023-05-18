package com.gym.gymbackend.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Membership {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String membershipName;
    private String price;
    private int month;

    private LocalDateTime createDate;

    @PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
