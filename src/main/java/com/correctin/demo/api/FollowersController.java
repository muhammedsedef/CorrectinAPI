package com.correctin.demo.api;

import com.correctin.demo.constant.ApiEndpoints;
import com.correctin.demo.dto.UserResponseDto;
import com.correctin.demo.entity.Followers;
import com.correctin.demo.service.FollowersService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = ApiEndpoints.FOLLOWERS_API_BASE_URL)
public class FollowersController {

    private final FollowersService followersService;
    private final ModelMapper modelMapper;

    public FollowersController(FollowersService followersService, ModelMapper modelMapper) {
        this.followersService = followersService;
        this.modelMapper = modelMapper;
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
    public ResponseEntity<Boolean> withdrawFollowRequest(
            @PathVariable Long id,
            @RequestParam (defaultValue = "follower") String type
    ) {
        return ResponseEntity.ok(this.followersService.withdrawFollowRequest(id, type));
    }

    @GetMapping("/followers-requests")
    public ResponseEntity<Map<String, Object>> showFollowRequest(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());

        Page<Followers> followerRequestPage = this.followersService.showFollowRequest(pageable);
        Map<String, Object> response = new HashMap<>();

        ArrayList<UserResponseDto> followerRequests = new ArrayList<>();
        followerRequestPage.getContent().forEach(followerPage -> {
            followerRequests.add(modelMapper.map(followerPage.getFrom(), UserResponseDto.class));
        });

        response.put("followerRequests", followerRequests);
        response.put("currentPage", followerRequestPage.getNumber());
        response.put("totalItems", followerRequestPage.getTotalElements());
        response.put("totalPages", followerRequestPage.getTotalPages());
        return ResponseEntity.ok(response);
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
