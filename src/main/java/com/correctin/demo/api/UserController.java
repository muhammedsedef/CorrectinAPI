package com.correctin.demo.api;

import com.correctin.demo.constant.ApiEndpoints;
import com.correctin.demo.dto.CreateUserRequest;
import com.correctin.demo.dto.LoginRequest;
import com.correctin.demo.dto.UserResponseDto;
import com.correctin.demo.dto.UserUpdateRequest;
import com.correctin.demo.entity.User;
import com.correctin.demo.exception.NotFoundException;
import com.correctin.demo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;


@RestController
@RequestMapping(value = ApiEndpoints.USER_API_BASE_URL)
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(
            @PathVariable("id") Long id,
            @RequestParam(required = false, defaultValue = "true") Boolean status
    ) {
        User user = userService.getById(status, id);
        if(user == null)
            throw new NotFoundException("No such record by given id: " + id);
        return ResponseEntity.ok(modelMapper.map(user, UserResponseDto.class));
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAll(
            //@RequestParam(required = false) Long nativeLanguageId,
            //@RequestParam(required = false) Long foreignLanguageId,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortBy).ascending());
        Page users = this.userService.getAll(pageable);

        Map<String, Object> response = new HashMap<>();

        ArrayList<UserResponseDto> userList = new ArrayList<>();
        users.getContent().forEach(user -> {
            userList.add(modelMapper.map(user, UserResponseDto.class));
        });
        response.put("users", userList);
        response.put("currentPage", users.getNumber());
        response.put("totalItems", users.getTotalElements());
        response.put("totalPages", users.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<Map<String, Object>> findByFullName(
            @RequestParam("s") String search,
            @RequestParam(defaultValue = "true", required = false) Boolean status,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortBy).ascending());
        Page<User> users = userService.findByFullName(status, search, pageable);
        if(users == null)
            throw new NotFoundException("User not found by given searching criteria : " + search);

        Map<String, Object> response = new HashMap<>();

        ArrayList<UserResponseDto> userList = new ArrayList<>();
        users.getContent().forEach(user -> {
            userList.add(modelMapper.map(user, UserResponseDto.class));
        });
        response.put("users", userList);
        response.put("currentPage", users.getNumber());
        response.put("totalItems", users.getTotalElements());
        response.put("totalPages", users.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Boolean result = this.userService.deleteUser(id);
        if(!result)
            throw new NotFoundException("User not found by given id : " + id);
        return ResponseEntity.ok("Successfully deleted");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable("id") Long id, @Valid @RequestBody UserUpdateRequest userUpdateRequest
    ) {
        UserResponseDto responseDto = modelMapper.map(this.userService.updateUser(id, userUpdateRequest), UserResponseDto.class);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/info")
    public ResponseEntity<UserResponseDto> getUserDetails() {
        User user = this.userService.getUserDetails();
        return ResponseEntity.ok(modelMapper.map(user, UserResponseDto.class));
    }
}
