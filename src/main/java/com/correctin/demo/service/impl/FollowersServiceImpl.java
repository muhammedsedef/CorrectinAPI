package com.correctin.demo.service.impl;

import com.correctin.demo.dto.PostResponse;
import com.correctin.demo.dto.UserResponseDto;
import com.correctin.demo.entity.Followers;
import com.correctin.demo.entity.Post;
import com.correctin.demo.entity.User;
import com.correctin.demo.exception.BadRequestException;
import com.correctin.demo.exception.NotFoundException;
import com.correctin.demo.repository.FollowersRepository;
import com.correctin.demo.service.FollowersService;
import com.correctin.demo.service.UserService;
import com.correctin.demo.specifications.PostSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FollowersServiceImpl implements FollowersService {

    private final UserService userService;
    private final FollowersRepository followersRepository;
    private final ModelMapper modelMapper;

    @Override
    public Boolean sendFollowRequest(Long id) {
        User activeUser = this.userService.getUserDetails();
        User followingUser = this.userService.getById(true, id);
        if(followingUser == null)
            throw new NotFoundException("User cannot found by given id: " + id);

        if(this.followersRepository.findByFromAndTo(activeUser, followingUser).isPresent())
            throw new BadRequestException("You have already send follow request to: " + followingUser.getFullName());
        try{
            Followers followers = new Followers();
            followers.setFrom(activeUser);
            followers.setTo(followingUser);
            followers.setCreatedBy(activeUser.getFullName());
            this.followersRepository.save(followers);
        }catch(Exception e){
            throw new BadRequestException("Error occured while follow to " + followingUser.getFullName());
        }
        return true;
    }

    @Override
    @Transactional
    // Accept follow request, id: accepted follower id
    public Boolean acceptFollowRequest(Long id) {

        User activeUser = this.userService.getUserDetails();
        User followerUser = this.userService.getById(true, id);

        Followers record = this.followersRepository.findByFromAndTo(followerUser, activeUser).orElseThrow(() -> {
            throw new BadRequestException("There is no exist following request anymore");
        });
        record.setIsAccepted(true);
        record.setUpdatedBy(activeUser.getFullName());
        this.followersRepository.save(record);
        return true;

    }

    @Override
    // Withdraw follow request, id: is who want to follow you
    public Boolean withdrawFollowRequest(Long id) {
        User activeUser = this.userService.getUserDetails();
        User followingUser = this.userService.getById(true, id);

        Followers record = this.followersRepository.findByFromAndTo(activeUser, followingUser).orElseThrow(() -> {
            throw new BadRequestException("There is no exist following request anymore");
        });
        this.followersRepository.delete(record);
        return true;
    }

    @Override
    public Page<Followers> showFollowRequest(Pageable pageable) {
        User activeUser = this.userService.getUserDetails();
        Page<Followers> followersPage = this.followersRepository.findByStatusAndTo(true, activeUser, pageable);
        return followersPage;

    }

//    @Override
//    public Map<String, Object> getAllFollowAndFollowers(Long id, int page, int size) {
//        User activeUser = this.userService.getUserDetails();
//        Pageable pageable = PageRequest.of(page,size);
//        List<User> followers = null;
//        List<User> followings = null;
//
//        Page<Followers> followersPage = this.followersRepository.findByTo(activeUser, pageable);
//        long followersNumber = followersPage.getTotalElements();
//        followersPage.getContent().forEach(follower -> {
//            followers.add(follower.getTo());
//        });
//
//        Page<Followers> followingPage = this.followersRepository.findByFrom(activeUser, pageable);
//        followingPage.getContent().forEach(following -> {
//            followings.add(following.getFrom());
//        });
//        long followingNumber = followingPage.getTotalElements();
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("followers", followers);
//        response.put("following", followings);
//        response.put("followersNumber", followersNumber);
//        response.put("followingNumber", followingNumber);
//
//        return response;
//    }


}
