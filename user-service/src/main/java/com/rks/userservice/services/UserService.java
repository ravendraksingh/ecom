package com.rks.userservice.services;

import com.rks.userservice.domains.User;
import com.rks.userservice.dto.response.UserResponse;

public interface UserService {
    UserResponse getUserByEmail(String email);
    UserResponse createNewUser(User user);
}
