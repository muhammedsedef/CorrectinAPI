package com.correctin.demo.service;

import com.correctin.demo.dto.CreateCheckedPostRequest;
import com.correctin.demo.dto.CreatePostRequest;
import com.correctin.demo.dto.PostUpdateRequest;
import com.correctin.demo.entity.CheckedPost;
import com.correctin.demo.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Post save(CreatePostRequest createPostRequest);

    Post getPostById(Boolean status, Long id);

    Page<Post> getAll(Pageable pageable, Boolean status);

    Boolean deletePost(Long id);

    Post updatePost(Long id, PostUpdateRequest postUpdateRequest);
}
