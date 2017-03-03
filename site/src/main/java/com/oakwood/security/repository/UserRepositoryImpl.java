package com.oakwood.security.repository;

import com.oakwood.security.model.Authority;
import com.oakwood.security.model.AuthorityName;
import com.oakwood.security.model.User;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;

/**
 * [description]
 *
 * @author syeremy
 * @version 1.0
 * @date 3/2/17
 **/

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Override
    public User findByUsername(String username) {

        User value = null;

        User userAdmin  = new User();
        userAdmin.setUsername(username);
        userAdmin.setPassword("admin");
        userAdmin.setId(1l);
        userAdmin.setEmail("admin@admin.com");
        userAdmin.setEnabled(true);

        User user  = new User();
        userAdmin.setUsername(username);
        userAdmin.setPassword("password");
        userAdmin.setId(2l);
        userAdmin.setEmail("user@user.com");
        userAdmin.setEnabled(true);


        Authority authorityAdmin = new Authority();
        authorityAdmin.setId(1l);
        authorityAdmin.setName(AuthorityName.ROLE_ADMIN);


        Authority authorityUser = new Authority();
        authorityUser.setId(2l);
        authorityUser.setName(AuthorityName.ROLE_USER);


        // -- Authority -> User mapping
        authorityAdmin.setUsers(Collections.singletonList(userAdmin));
        authorityUser.setUsers(Arrays.asList(userAdmin, user));

        // -- User -> Authority mapping
        user.setAuthorities(Collections.singletonList(authorityUser));
        userAdmin.setAuthorities(Arrays.asList(authorityAdmin, authorityUser));




        switch (username){
            case "admin" :
                value = userAdmin;
                break;

            case "user" :
                value =  user;
                break;
        }

        return  value;
    }
}
