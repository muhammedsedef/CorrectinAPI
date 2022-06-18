package com.correctin.demo.api;

import com.correctin.demo.constant.ApiEndpoints;
import com.correctin.demo.dto.CreateCheckedPostRequest;
import com.correctin.demo.entity.CheckedPost;
import com.correctin.demo.entity.Post;
import com.correctin.demo.exception.BadRequestException;
import com.correctin.demo.service.CheckedPostService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping()
    public ResponseEntity<CheckedPost> saveCheckedPost(@Valid @RequestBody CreateCheckedPostRequest createCheckedPostRequest) {
        return ResponseEntity.ok(this.checkedPostService.save(createCheckedPostRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> deleteCheckedPost(@PathVariable Long id) {
        if(!this.checkedPostService.deletePost(id))
            throw new BadRequestException("User cannot deleted by given id: " + id);
        return ResponseEntity.ok("Successfully deleted post");
    }

//    @GetMapping("")
//    ResponseEntity<Map<String, Object>> getAll(
//            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
//            @RequestParam(required = false) Long nativeLanguageId,
//            @RequestParam(required = false) Long foreignLanguageId,
//            @RequestParam(required = false) Long userId,
//            @RequestParam(required = false, defaultValue = "true") Boolean status,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "5") int size
//    ){
//        Pageable pageable = PageRequest.of(page,size, Sort.by(sortBy).ascending());
//        Page checkedPosts = this.checkedPostService.getAll(pageable, status);
//
//
//        Map<String, Object> response = new HashMap<>();
//
//        ArrayList<CheckedPost> checkedPostList = new ArrayList<>();
//        checkedPosts.getContent().forEach(post -> {
//            checkedPostList.add((CheckedPost) post);
//            //postList.add(modelMapper.map(post, UserResponseDto.class));
//        });
//        response.put("checkedPosts", checkedPostList);
//        response.put("currentPage", checkedPosts.getNumber());
//        response.put("totalItems", checkedPosts.getTotalElements());
//        response.put("totalPages", checkedPosts.getTotalPages());
//
//        return ResponseEntity.ok(response);
//    }
}
