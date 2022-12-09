package com.rks.userservice.repositories;

import com.rks.userservice.domains.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByEmail(String email);
}
