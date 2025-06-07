package com.hezix.shaudifymain.service;

import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.entity.user.dto.CreateUserDto;
import com.hezix.shaudifymain.entity.user.dto.ReadUserDto;
import com.hezix.shaudifymain.exception.EntityNotFoundException;
import com.hezix.shaudifymain.exception.PasswordAndPasswordConfirmationNotEquals;
import com.hezix.shaudifymain.mapper.user.UserCreateMapper;
import com.hezix.shaudifymain.mapper.user.UserReadMapper;
import com.hezix.shaudifymain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserCreateMapper userCreateMapper;
    private final UserReadMapper userReadMapper;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    @Transactional()
    public ReadUserDto save(CreateUserDto createUserDto) {
        if(!createUserDto.getPassword().equals(createUserDto.getPasswordConfirm())){
            throw new PasswordAndPasswordConfirmationNotEquals("Password and password confirmation not equals");
        }
        createUserDto.setPassword(bcryptPasswordEncoder.encode(createUserDto.getPassword()));
        User user = userCreateMapper.toEntity(createUserDto);
        user.setCreatedAt(Instant.now());
        User created_user = userRepository.save(user);
        return userReadMapper.toDto(created_user);
    }
    @Cacheable(value = "UserService::findUserById", key = "#id")
    @Transactional(readOnly = true)
    public ReadUserDto findUserById(Long id) {
        return userReadMapper.toDto(userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found")));
    }
    @Transactional(readOnly = true)
    @Cacheable(value = "UserService::findUserByUsername", key = "#username")
    public ReadUserDto findUserByUsername(String username) {
        return userReadMapper.toDto(userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with username " + username + " not found")));
    }
    @Cacheable(value = "UserService::findUserEntityByUsername", key = "#username")
    @Transactional(readOnly = true)
    public User findUserEntityByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User Entity with username " + username + " not found"));
    }
    @Cacheable(value = "UserService::findUserEntityByUsernameWithCreatedSongs", key = "#username")
    @Transactional(readOnly = true)
    public User findUserEntityByUsernameWithCreatedSongs(String username) {
        return userRepository.findByUsernameWithCreatedSongs(username)
                .orElseThrow(() -> new EntityNotFoundException("User Entity with username " + username + " not found"));
    }
    @Cacheable(value = "UserService::findUserDetailsByUsername", key = "#username")
    @Transactional(readOnly = true)
    public UserDetails findUserDetailsByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singletonList(user.getRole())
                ))
                .orElseThrow(() -> new EntityNotFoundException("UserDetails with username " + username + " not found"));
    }
    @Transactional(readOnly = true)
    public List<ReadUserDto> findAllUsers() {
        return userReadMapper.toDtoList(userRepository.findAll());
    }
    @Caching(evict = {
            @CacheEvict(value = "UserService::findUserById", key = "#result.id"),
            @CacheEvict(value = "UserService::findUserByUsername", key = "#result.username"),
            @CacheEvict(value = "UserService::findUserEntityByUsername", key = "#result.username"),
            @CacheEvict(value = "UserService::findUserDetailsByUsername", key = "#result.username"),
            @CacheEvict(value = "UserService::findUserEntityByUsernameWithCreatedSongs", key = "#result.username")
    })
    @Transactional()
    public ReadUserDto deleteUserById(Long id) {
        var user = findUserById(id);
        userRepository.delete(userReadMapper.toEntity(user));
        return user;
    }



}
