package com.correctin.demo.service;

import com.correctin.demo.entity.Followers;
import com.correctin.demo.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface FollowersService {

    Boolean sendFollowRequest(Long id);

    Boolean acceptFollowRequest(Long id);

    Boolean withdrawFollowRequest(Long id);

    //Map<String, Object> getAllFollowAndFollowers(Long id, int page, int size);
}
