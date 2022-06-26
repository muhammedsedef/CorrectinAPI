package com.correctin.demo.service.impl;

import com.correctin.demo.dto.ChangePasswordRequest;
import com.correctin.demo.dto.CreateUserRequest;
import com.correctin.demo.dto.UserUpdateRequest;
import com.correctin.demo.entity.Followers;
import com.correctin.demo.entity.Language;
import com.correctin.demo.entity.User;
import com.correctin.demo.exception.AccessDeniedException;
import com.correctin.demo.exception.BadCredentialsException;
import com.correctin.demo.exception.BadRequestException;
import com.correctin.demo.exception.NotFoundException;
import com.correctin.demo.repository.FollowersRepository;
import com.correctin.demo.repository.LanguageRepository;
import com.correctin.demo.repository.UserRepository;
import com.correctin.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final LanguageRepository languageRepository;


    @Override
    @Transactional
    public User save(CreateUserRequest createUserRequest){

        Language nativeLanguage = getLanguage(createUserRequest.getNativeLanguage());
        Language foreignLanguage = getLanguage(createUserRequest.getForeignLanguage());

        User user = modelMapper.map(createUserRequest, User.class);
        user.setCreatedBy(user.getFirstName() + " " + user.getLastName());
        user.setUpdatedBy(user.getFirstName() + " " + user.getLastName());
        user.setForeignLanguage(foreignLanguage);
        user.setNativeLanguage(nativeLanguage);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return this.userRepository.save(user);
    }

    @Override
    public User getById(Boolean status, Long id) {
        return this.userRepository.findByStatusAndId(status, id);
    }

    @Override
    public Page<User> getAll(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    @Override
    public Page<User> findByFullName(Boolean status, String search, Pageable pageable) {
        return this.userRepository.findByStatusAndFullNameContaining(status, search, pageable);
    }

    @Override
    public Boolean deleteUser(Long id)  {
        Optional<User> user = this.userRepository.findById(id);

        if(user.isPresent()) {
            user.get().setStatus(false);
            this.userRepository.save(user.get());
            return true;
        }else {
            return false;
        }
    }

    @Override
    @Transactional
    public User updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        User user = this.userRepository.findByStatusAndId(true, id);
        User activeUser = this.getUserDetails();
        if(user == null)
            throw new NotFoundException("User not found by given id : " + id);

        Language nativeLanguage = getLanguage(userUpdateRequest.getNativeLanguageId());
        Language foreignLanguage = getLanguage(userUpdateRequest.getForeignLanguageId());
        user.setFirstName(userUpdateRequest.getFirstName());
        user.setLastName(userUpdateRequest.getLastName());
        user.setEmail(userUpdateRequest.getEmail());
        user.setNativeLanguage(nativeLanguage);
        user.setForeignLanguage(foreignLanguage);
        user.setUpdatedBy(activeUser.getFirstName() + " " + activeUser.getLastName());
        return this.userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> {
            throw new NotFoundException("User not found by given by email: " + email);
        });
    }

    @Override
    public Boolean changePassword(ChangePasswordRequest changePasswordRequest) {
        User user = this.getUserByEmail(changePasswordRequest.getEmail());
        User activeUser = this.getUserDetails();
        if(user != activeUser)
            throw new AccessDeniedException("You cannot access here");
        if(!bCryptPasswordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword()))
            throw new BadCredentialsException("Old Password is incorrect!");
        user.setPassword(bCryptPasswordEncoder.encode(changePasswordRequest.getNewPassword()));
        this.userRepository.save(user);
        return true;
    }

    @Override
    public User getUserDetails() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.userRepository.findByEmail(email).get();
    }

    protected Language getLanguage(Long languageId) {
        Language language = this.languageRepository.findById(languageId).
                orElseThrow(() -> {
            return new NotFoundException("Language not found by given id: " + languageId);
        });
        return language;
    }
}
