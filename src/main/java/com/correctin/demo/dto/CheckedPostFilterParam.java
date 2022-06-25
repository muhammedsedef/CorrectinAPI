package com.correctin.demo.dto;

import lombok.Data;

@Data
public class CheckedPostFilterParam {
    private int page = 0;
    private int size = 5;
    private String sortBy;
    private String orderBy;
    private Boolean status;
    private Long id;
    private Long userId; // who is check
    private Long oldPostId;
    private Long oldPostUserId; // which user's post checked
    private Long nativeLanguageId;
    private Long foreignLanguageId;
}
