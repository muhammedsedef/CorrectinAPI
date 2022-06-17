package com.correctin.demo.dto;

import lombok.Data;


@Data
public class UserResponseDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private LanguageDto nativeLanguage;

    private LanguageDto foreignLanguage;

    private Boolean status;
}
