package com.correctin.demo.service.impl;

import com.correctin.demo.dto.CreateUserRequest;
import com.correctin.demo.dto.UserUpdateRequest;
import com.correctin.demo.entity.Language;
import com.correctin.demo.entity.User;
import com.correctin.demo.exception.NotFoundException;
import com.correctin.demo.repository.LanguageRepository;
import com.correctin.demo.repository.UserRepository;
import com.correctin.demo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final LanguageRepository languageRepository;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder, LanguageRepository languageRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.languageRepository = languageRepository;
    }

    @Override
    public User save(CreateUserRequest createUserRequest){

        Long nativeLanguageId = createUserRequest.getNativeLanguage();
        Language nativeLanguage = languageRepository.findById(nativeLanguageId)
                .orElseThrow(() -> {
                    return new NotFoundException("Native language not found by given id: " + nativeLanguageId);
                });
        Long foreignLanguageId = createUserRequest.getForeignLanguage();
        Language foreignLanguage = languageRepository.findById(foreignLanguageId)
                .orElseThrow(() -> {
                    return new NotFoundException("Foreign language not found by given id: " + foreignLanguageId);
                });
//        User savedUser = new User();
//        savedUser.setNativeLanguage(nativeLanguage);
//        savedUser.setForeignLanguage(foreignLanguage);
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
    public User getByFirstName(Boolean status, String firstName) {
        return this.userRepository.findByStatusAndFirstName(status, firstName);
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
    public User updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        User user = this.userRepository.findByStatusAndId(true, id);
        if(user == null)
            throw new NotFoundException("User not found by given id : " + id);

        user.setFirstName(userUpdateRequest.getFirstName());
        user.setLastName(userUpdateRequest.getLastName());
        user.setEmail(userUpdateRequest.getEmail());
        user.setNativeLanguage(modelMapper.map(userUpdateRequest.getNativeLanguage(), Language.class));
        user.setForeignLanguage(modelMapper.map(userUpdateRequest.getForeignLanguage(), Language.class));
        user.setUpdatedBy(user.getFirstName() + " " + user.getLastName());
        return this.userRepository.save(user);
    }

    @Override
    public User getUserDetails() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.userRepository.findByEmail(email).get();
    }

}
