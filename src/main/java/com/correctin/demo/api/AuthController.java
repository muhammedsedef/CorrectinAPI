package com.correctin.demo.api;

import com.correctin.demo.constant.ApiEndpoints;
import com.correctin.demo.dto.CreateUserRequest;
import com.correctin.demo.dto.LoginRequest;
import com.correctin.demo.dto.UserResponseDto;
import com.correctin.demo.entity.User;
import com.correctin.demo.exception.BadCredentialsException;
import com.correctin.demo.security.JWTUtil;
import com.correctin.demo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(ApiEndpoints.AUTH_API_BASE_URL)
public class AuthController {

//    @Autowired
//    private UserDetailServiceImpl userDetailService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authManager;

    //throws AuthenticationException
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequest loginRequest) {
        try{
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
            authManager.authenticate(authInputToken);
            String token = jwtUtil.generateToken(loginRequest.getEmail());
            return ResponseEntity.ok(Collections.singletonMap("jwt-token", token));
        }catch(AuthenticationException e) {
            throw new BadCredentialsException("Wrong email or password!");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody CreateUserRequest createUserRequest) {
        User user = this.userService.save(createUserRequest);
        return ResponseEntity.ok(modelMapper.map(user, UserResponseDto.class));
    }
}
