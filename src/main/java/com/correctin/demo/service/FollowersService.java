package com.correctin.demo.service;

import com.correctin.demo.dto.UserResponseDto;
import com.correctin.demo.entity.Followers;
import com.correctin.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface FollowersService {

    Boolean sendFollowRequest(Long id);

    Boolean acceptFollowRequest(Long id);

    Boolean withdrawFollowRequest(Long id);

    // show active user's follow requests
    Page<Followers> showFollowRequest(Pageable pageable);

    //Map<String, Object> getAllFollowAndFollowers(Long id, int page, int size);
}
