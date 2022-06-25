package com.correctin.demo.dto;

import lombok.Data;

@Data
public class PostFilterParam {
    private int page = 0;
    private int size = 5;
    private String sortBy;
    private String orderBy;
    private Boolean status;
    private Long userId;
    private Long nativeLanguageId;
    private Long foreignLanguageId;
}
