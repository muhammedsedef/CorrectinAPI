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
    @Transactional
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
    // if type == following => withdraw active user's following request, id represents the person I want to follow
    // else : reject coming to my following request, id represents the person who want to follow me
    public Boolean withdrawFollowRequest(Long id, String type) {
        User activeUser = this.userService.getUserDetails();
        User rejectedUser = this.userService.getById(true, id);

        // following request withdraw
        if(type.equals("following")) {
            Followers record = this.followersRepository.findByFromAndTo(activeUser, rejectedUser).orElseThrow(() -> {
                throw new BadRequestException("There is no exist following request anymore");
            });
            this.followersRepository.delete(record);
            return true;
        }

        // incoming request rejected operation
        Followers record = this.followersRepository.findByFromAndTo(rejectedUser, activeUser).orElseThrow(() -> {
            throw new BadRequestException("There is no exist following request anymore");
        });
        this.followersRepository.delete(record);
        return true;
    }

    @Override
    @Transactional
    public Page<Followers> showFollowRequest(Pageable pageable) {
        User activeUser = this.userService.getUserDetails();
        Page<Followers> followersPage = this.followersRepository.findByIsAcceptedAndTo(false, activeUser, pageable);
        return followersPage;

    }

    @Override
    @Transactional
    public Map<String, Object> showFollowersAndFollowing(Long id, Pageable pageable) {
        User user = this.userService.getById(true, id);
        Page<Followers> followersResult = this.followersRepository.findByIsAcceptedAndTo(true, user, pageable);

        ArrayList<UserResponseDto> followers= new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        // To find followers
        followersResult.getContent().forEach(follower -> {
            followers.add(modelMapper.map(follower.getFrom(), UserResponseDto.class));
        });

        response.put("followers", followers);
        response.put("followersCurrentPage", followersResult.getNumber());
        response.put("followersTotalItems", followersResult.getTotalElements());
        response.put("followersTotalPages", followersResult.getTotalPages());

        Page<Followers> followingResult = this.followersRepository.findByIsAcceptedAndFrom(true, user, pageable);
        ArrayList<UserResponseDto> followings = new ArrayList<>();

        // To find following users
        followingResult.getContent().forEach(following -> {
            followings.add(modelMapper.map(following.getTo(), UserResponseDto.class));
        });

        response.put("followings", followings);
        response.put("followingsCurrentPage", followingResult.getNumber());
        response.put("followingsTotalItems", followingResult.getTotalElements());
        response.put("followingsTotalPages", followingResult.getTotalPages());

        return response;
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
