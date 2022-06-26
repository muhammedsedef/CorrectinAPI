package com.correctin.demo.api;

import com.correctin.demo.constant.ApiEndpoints;
import com.correctin.demo.dto.*;
import com.correctin.demo.exception.BadRequestException;
import com.correctin.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = ApiEndpoints.POST_API_BASE_URL)
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final ModelMapper modelMapper;

    @PostMapping()
    public ResponseEntity<PostResponse> save(@Valid @RequestBody CreatePostRequest createPostRequest) {
        return ResponseEntity.ok(modelMapper.map(this.postService.save(createPostRequest), PostResponse.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "true") Boolean status
    ){
        return ResponseEntity.ok(modelMapper.map(this.postService.getPostById(status, id), PostResponse.class));
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAll(
            @ModelAttribute PostFilterParam allParams
    ){

        Page posts = this.postService.getAll(allParams);
        Map<String, Object> response = new HashMap<>();

        ArrayList<PostResponse> postList = new ArrayList<>();
        posts.getContent().forEach(post -> {
            postList.add(modelMapper.map(post, PostResponse.class));
            //postList.add((Post) post);
        });
        response.put("posts", postList);
        response.put("currentPage", posts.getNumber());
        response.put("totalItems", posts.getTotalElements());
        response.put("totalPages", posts.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long id, @Valid @RequestBody PostUpdateRequest postUpdateRequest) {
        return ResponseEntity.ok(modelMapper.map(this.postService.updatePost(id, postUpdateRequest), PostResponse.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        if(!this.postService.deletePost(id))
            throw new BadRequestException("User cannot deleted by given id: " + id);
        return ResponseEntity.ok("Successfully deleted post");
    }
}
