package com.gym.gymbackend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberInfoDto {
    private String name;
    private String membershipName;
    private String createDate;
    private String expirationDate;
    private String restDays;
    private int checkCount;
    private String clothes;
}
