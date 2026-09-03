package com.example.employee.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponseDto {

    private Long userId;
    private String name;
    private String email;
    private String message;
}