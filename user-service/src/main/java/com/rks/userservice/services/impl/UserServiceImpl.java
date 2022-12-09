package com.rks.userservice.services.impl;

import com.rks.mcommon.exceptions.NotFoundException;
import com.rks.userservice.domains.User;
import com.rks.userservice.dto.request.UserRequest;
import com.rks.userservice.dto.response.UserResponse;
import com.rks.userservice.mappers.UserMapper;
import com.rks.userservice.repositories.UserRepository;
import com.rks.userservice.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.rks.mcommon.constants.CommonConstants.FAILED;

@Service
public class UserServiceImpl implements UserService {
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserResponse getUserByEmail(String email) {
        logger.info("Fetching user details for email={}", email);
        User fetchedUser = userRepository.findByEmail(email);
        if (fetchedUser == null) {
            logger.debug("ERROR: user not found for email={}", email);
            throw new NotFoundException(FAILED, 404, "User not found");
        }
        return createUserResponseForUser(fetchedUser);
    }

    @Override
    public UserResponse createNewUser(User user) {
        User savedUser = userRepository.save(user);
        return createUserResponseForUser(savedUser);
    }

    private UserResponse createUserResponseForUser(User user) {
        UserResponse userResponse = new UserResponse();
        userMapper.map(user, userResponse);
        return userResponse;
    }
}
