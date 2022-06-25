package com.correctin.demo.dto;

import lombok.Data;

@Data
public class PostResponse extends BaseResponse{

    private Long id;

    private String postTitle;

    private String postBody;

    private UserResponseDto user;
}
