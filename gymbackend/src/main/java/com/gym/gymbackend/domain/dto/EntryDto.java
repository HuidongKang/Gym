package com.gym.gymbackend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EntryDto {
    private String createDate;
    private String memberName;
    private String phone;

}
