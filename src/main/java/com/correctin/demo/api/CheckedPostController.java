package com.correctin.demo.api;

import com.correctin.demo.constant.ApiEndpoints;
import com.correctin.demo.dto.*;
import com.correctin.demo.entity.CheckedPost;
import com.correctin.demo.entity.Post;
import com.correctin.demo.exception.BadRequestException;
import com.correctin.demo.service.CheckedPostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = ApiEndpoints.CHECKED_POST_API_BASE_URL)
@RequiredArgsConstructor
public class CheckedPostController {

    private final CheckedPostService checkedPostService;
    private final ModelMapper modelMapper;

    @PostMapping()
    public ResponseEntity<CheckedPostResponse> saveCheckedPost(@Valid @RequestBody CreateCheckedPostRequest createCheckedPostRequest) {
        return ResponseEntity.ok(modelMapper.map(this.checkedPostService.save(createCheckedPostRequest), CheckedPostResponse.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> deleteCheckedPost(@PathVariable Long id) {
        if(!this.checkedPostService.deletePost(id))
            throw new BadRequestException("User cannot deleted by given id: " + id);
        return ResponseEntity.ok("Successfully deleted post");
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAll(
            @ModelAttribute CheckedPostFilterParam allParams
    ){

        Page checkedPosts = this.checkedPostService.getAll(allParams);
        Map<String, Object> response = new HashMap<>();

        ArrayList<CheckedPostResponse> postList = new ArrayList<>();
        checkedPosts.getContent().forEach(post -> {
            postList.add(modelMapper.map(post, CheckedPostResponse.class));
        });
        response.put("checkedPosts", postList);
        response.put("currentPage", checkedPosts.getNumber());
        response.put("totalItems", checkedPosts.getTotalElements());
        response.put("totalPages", checkedPosts.getTotalPages());

        return ResponseEntity.ok(response);
    }
}
