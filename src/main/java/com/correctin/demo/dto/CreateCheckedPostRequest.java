package com.correctin.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateCheckedPostRequest {

    @NotNull
    private Long oldPostId;

    @NotNull
    @Size(min=3)
    private String postBody;
}
