package com.correctin.demo.api;

import com.correctin.demo.constant.ApiEndpoints;
import com.correctin.demo.dto.CreateCheckedPostRequest;
import com.correctin.demo.entity.CheckedPost;
import com.correctin.demo.exception.BadRequestException;
import com.correctin.demo.service.CheckedPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
}
