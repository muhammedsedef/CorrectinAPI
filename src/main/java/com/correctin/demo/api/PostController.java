package com.correctin.demo.api;

import com.correctin.demo.constant.ApiEndpoints;
import com.correctin.demo.dto.CreateCheckedPostRequest;
import com.correctin.demo.dto.CreatePostRequest;
import com.correctin.demo.dto.PostUpdateRequest;
import com.correctin.demo.entity.CheckedPost;
import com.correctin.demo.entity.Post;
import com.correctin.demo.exception.BadRequestException;
import com.correctin.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @PostMapping()
    public ResponseEntity<Post> save(@Valid @RequestBody CreatePostRequest createPostRequest) {
        return ResponseEntity.ok(this.postService.save(createPostRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "true") Boolean status
    ){
        return ResponseEntity.ok(this.postService.getPostById(true, id));
    }

    @GetMapping("/all")
    ResponseEntity<Map<String, Object>> getAll(
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false, defaultValue = "true") Boolean status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortBy).ascending());
        Page posts = this.postService.getAll(pageable, status);

        Map<String, Object> response = new HashMap<>();

        ArrayList<Post> postList = new ArrayList<>();
        posts.getContent().forEach(post -> {
            postList.add((Post) post);
            //postList.add(modelMapper.map(post, UserResponseDto.class));
        });
        response.put("posts", postList);
        response.put("currentPage", posts.getNumber());
        response.put("totalItems", posts.getTotalElements());
        response.put("totalPages", posts.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @Valid @RequestBody PostUpdateRequest postUpdateRequest) {
        return ResponseEntity.ok(this.postService.updatePost(id, postUpdateRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        if(!this.postService.deletePost(id))
            throw new BadRequestException("User cannot deleted by given id: " + id);
        return ResponseEntity.ok("Successfully deleted post");
    }
}
