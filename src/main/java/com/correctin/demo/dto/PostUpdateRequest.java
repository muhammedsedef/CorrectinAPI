package com.correctin.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PostUpdateRequest {
    @NotNull
    @Size(min=3, max=100)
    private String postTitle;

    @NotNull
    @Size(min=3)
    private String postBody;

}
