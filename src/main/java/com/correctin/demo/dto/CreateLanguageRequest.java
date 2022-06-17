package com.correctin.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateLanguageRequest {

    @NotNull
    private Long id;

    @NotNull
    @Size(min=3)
    private String languageName;
}
