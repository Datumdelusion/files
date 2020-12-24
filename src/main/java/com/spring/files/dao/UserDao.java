package com.spring.files.dao;

import com.spring.files.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    User login(User user);
}
