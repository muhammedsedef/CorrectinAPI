package com.correctin.demo.service;

import com.correctin.demo.dto.CreateCheckedPostRequest;
import com.correctin.demo.entity.CheckedPost;

public interface CheckedPostService {
    CheckedPost save(CreateCheckedPostRequest createPostRequest);

    Boolean deletePost(Long id);
}
