package com.example.employee.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private Double salary;
    private String department;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    private String createdBy;
    private String updatedBy;

    private Long version;
}
