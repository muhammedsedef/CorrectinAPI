package com.correctin.demo.service.impl;

import com.correctin.demo.dto.*;
import com.correctin.demo.entity.Post;
import com.correctin.demo.entity.User;
import com.correctin.demo.exception.BadRequestException;
import com.correctin.demo.exception.NotFoundException;
import com.correctin.demo.repository.CheckedPostRepository;
import com.correctin.demo.repository.PostRepository;
import com.correctin.demo.service.PostService;
import com.correctin.demo.service.UserService;
import com.correctin.demo.specifications.CheckedPostSpecification;
import com.correctin.demo.specifications.PostSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CheckedPostRepository checkedPostRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public Post save(CreatePostRequest createPostRequest) {
        Post post = modelMapper.map(createPostRequest, Post.class);

        //finding active user which means that user insert a new post
        User user = this.userService.getUserDetails();
        post.setUser(user);
        return this.postRepository.save(post);
    }

    @Override
    public Post getPostById(Boolean status, Long id) {
        return this.postRepository.findByStatusAndId(status, id);
    }

//    @Override
//    @Transactional
//    public Page<Post> getAll(Pageable pageable, Boolean status) {
//        User activeUser = this.userService.getUserDetails();
//        //List<Post> post = this.postRepository.findByUser(activeUser);
//        return this.postRepository.findByStatusAndUserId(status, activeUser.getId(), pageable);
//        //return this.postRepository.findByUserId(activeUser.getId());
//        //return this.postRepository.findByStatusAndUserId(status,2L, pageable);
//    }

    @Override
    @Transactional
    public Page<Post> getAll(PostFilterParam allParams){
        int page = 0;
        int size = 5;
        Pageable pageable = null;
        Specification<Post> specification = null;
        if(allParams != null) {
            specification = PostSpecification.getFilteredPosts(allParams);
            page = allParams.getPage();
            size = allParams.getSize();
            pageable = PageRequest.of(page,size);
            return this.postRepository.findAll(specification, pageable);
        }
        return this.postRepository.findByStatus(true, pageable);

    }

    @Override
    public Boolean deletePost(Long id) {
        try{
            Optional<Post> post = this.postRepository.findById(id);
            if(!post.isPresent())
                throw new NotFoundException("Post not found by given id: " + id);
            post.get().setStatus(false);
            this.postRepository.save(post.get());
            return true;
        }catch(Exception e) {
            throw new BadRequestException("Error occur while deleting post by given id: " + id);
        }
    }

    @Override
    public Post updatePost(Long id, PostUpdateRequest postUpdateRequest) {
        Post oldPost = this.postRepository.findByStatusAndId(true,id);
        if(oldPost == null)
            throw new NotFoundException("Post not found by given id : " + id);
        oldPost.setPostBody(postUpdateRequest.getPostBody());
        oldPost.setPostTitle(postUpdateRequest.getPostTitle());

        // Get active user for update => updatedBy field on post...
        User activeUser = this.userService.getUserDetails();
        oldPost.setUpdatedBy(activeUser.getFirstName() + " " + activeUser.getLastName());
        return this.postRepository.save(oldPost);
    }
}
