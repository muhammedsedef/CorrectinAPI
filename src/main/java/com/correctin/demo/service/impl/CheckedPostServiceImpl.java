package com.correctin.demo.service.impl;

import com.correctin.demo.dto.CheckedPostFilterParam;
import com.correctin.demo.dto.CreateCheckedPostRequest;
import com.correctin.demo.dto.PostFilterParam;
import com.correctin.demo.entity.CheckedPost;
import com.correctin.demo.entity.Post;
import com.correctin.demo.entity.User;
import com.correctin.demo.exception.BadRequestException;
import com.correctin.demo.exception.NotFoundException;
import com.correctin.demo.exception.UniqueConstraintException;
import com.correctin.demo.repository.CheckedPostRepository;
import com.correctin.demo.service.CheckedPostService;
import com.correctin.demo.service.PostService;
import com.correctin.demo.service.UserService;
import com.correctin.demo.specifications.CheckedPostSpecification;
import com.correctin.demo.specifications.PostSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckedPostServiceImpl implements CheckedPostService {
    private final CheckedPostRepository checkedPostRepository;
    private final PostService postService;
    private final UserService userService;

    @Override
    @Transactional
    public CheckedPost save(CreateCheckedPostRequest createCheckedPostRequest) {

        CheckedPost isCheckedPostExist = this.checkedPostRepository.findByOldPostId(createCheckedPostRequest.getOldPostId());
        if(isCheckedPostExist != null)
            throw new UniqueConstraintException("That post already checked, you cannot check again.");

        Post oldPost = this.postService.getPostById(true, createCheckedPostRequest.getOldPostId());
        if(oldPost == null)
            throw new NotFoundException("Post not found by given id : " + createCheckedPostRequest.getOldPostId());

        User activeUser = this.userService.getUserDetails();
        CheckedPost checkedPost = new CheckedPost();

        checkedPost.setOldPost(oldPost);
        checkedPost.setCreatedBy(activeUser.getFirstName() + " " + activeUser.getLastName());
        checkedPost.setPostBody(createCheckedPostRequest.getPostBody());
        checkedPost.setComment(createCheckedPostRequest.getComment());
        checkedPost.setUser(activeUser);

        return this.checkedPostRepository.save(checkedPost);
    }

    @Override
    public Boolean deletePost(Long id) {
        try{
            Optional<CheckedPost> post = this.checkedPostRepository.findById(id);
            if(!post.isPresent())
                throw new NotFoundException("Post not found by given id: " + id);
            post.get().setStatus(false);
            this.checkedPostRepository.save(post.get());
            return true;
        }catch(Exception e) {
            throw new BadRequestException("Error occur while deleting post by given id: " + id);
        }
    }

    @Override
    public Page<CheckedPost> getAll(CheckedPostFilterParam allParams) {
        int page = 0;
        int size = 5;
        Pageable pageable = null;
        Specification<CheckedPost> specification = null;
        if(allParams != null) {
            specification = CheckedPostSpecification.getFilteredCheckedPosts(allParams);
            page = allParams.getPage();
            size = allParams.getSize();
            pageable = PageRequest.of(page,size);
            return this.checkedPostRepository.findAll(specification, pageable);
        }
        return this.checkedPostRepository.findByStatus(true, pageable);

    }
}
