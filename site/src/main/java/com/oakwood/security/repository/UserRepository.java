package com.oakwood.security.repository;


import com.oakwood.security.model.repo.User;

/**
 * Created by stephan on 20.03.16.
 */
public interface UserRepository  {
    User findByUsername(String username);
}
