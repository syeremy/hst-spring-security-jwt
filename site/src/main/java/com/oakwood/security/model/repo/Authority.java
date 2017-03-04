package com.oakwood.security.model.repo;


import java.util.List;

/**
 * [description]
 *
 * @author syeremy
 * @version 1.0
 * @date 3/2/17
 **/
public class Authority {

    private Long id;

    private AuthorityName name;
    private List<User> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuthorityName getName() {
        return name;
    }

    public void setName(AuthorityName name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
