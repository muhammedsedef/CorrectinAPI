package com.correctin.demo.api;

import com.correctin.demo.constant.ApiEndpoints;
import com.correctin.demo.dto.PostResponse;
import com.correctin.demo.entity.Followers;
import com.correctin.demo.service.FollowersService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = ApiEndpoints.FOLLOWERS_API_BASE_URL)
public class FollowersController {

    private final FollowersService followersService;

    public FollowersController(FollowersService followersService) {
        this.followersService = followersService;
    }

    @PostMapping("{id}")
    public ResponseEntity<Boolean> sendFollowRequest(@PathVariable Long id) {
        return ResponseEntity.ok(this.followersService.sendFollowRequest(id));
    }

    @PutMapping("/accept/{id}")
    public ResponseEntity<Boolean> acceptFollowRequest(@PathVariable Long id) {
        return ResponseEntity.ok(this.followersService.acceptFollowRequest(id));
    }

    @PutMapping("/withdraw/{id}")
    public ResponseEntity<Boolean> withdrawFollowRequest(@PathVariable Long id) {
        return ResponseEntity.ok(this.followersService.withdrawFollowRequest(id));
    }

//    @GetMapping("{id}")
//    public ResponseEntity<Followers> getAllFollowerAndFollowers(
//            @PathVariable Long id,
//            @RequestParam(required = false, defaultValue = "0") int page,
//            @RequestParam(required = false, defaultValue = "5") int size
//    ){
//        Map<String, Object> records = this.followersService.getAllFollowAndFollowers(id, page, size);
//        Map<String, Object> response = new HashMap<>();
//
//        ArrayList<PostResponse> followersList = new ArrayList<>();
//        records.get("followers").forEach(post -> {
//            postList.add(modelMapper.map(post, PostResponse.class));
//            //postList.add((Post) post);
//        });
//        response.put("posts", postList);
//        response.put("currentPage", posts.getNumber());
//        response.put("totalItems", posts.getTotalElements());
//        response.put("totalPages", posts.getTotalPages());
//
//        return ResponseEntity.ok(response);
//
//    }

}
