package com.correctin.demo.dto;

import lombok.Data;

@Data
public class CheckedPostResponse extends BaseResponse{

    private Long id;

    private String postBody;

    private String comment;

    private PostResponse oldPost;

    private UserResponseDto user;
}
