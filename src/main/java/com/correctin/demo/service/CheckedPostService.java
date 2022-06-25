package com.correctin.demo.service;

import com.correctin.demo.dto.CheckedPostFilterParam;
import com.correctin.demo.dto.CreateCheckedPostRequest;
import com.correctin.demo.entity.CheckedPost;
import org.springframework.data.domain.Page;

public interface CheckedPostService {
    CheckedPost save(CreateCheckedPostRequest createPostRequest);

    Boolean deletePost(Long id);

    Page<CheckedPost> getAll(CheckedPostFilterParam allParams);
}
