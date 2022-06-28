package com.correctin.demo.service;

import com.correctin.demo.dto.*;
import com.correctin.demo.entity.Post;
import org.springframework.data.domain.Page;


public interface PostService {
    Post save(CreatePostRequest createPostRequest);

    Post getPostById(Boolean status, Long id);

    //Page<Post> getAll(Pageable pageable, Boolean status);
    Page<Post> getAll(PostFilterParam allParams);

    Boolean deletePost(Long id);

    Post updatePost(Long id, PostUpdateRequest postUpdateRequest);

    void updateIsChecked(Post oldPost);
}
