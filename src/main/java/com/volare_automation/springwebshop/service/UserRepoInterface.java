package com.volare_automation.springwebshop.service;

import com.volare_automation.springwebshop.model.User;

import java.util.List;

public interface UserRepoInterface {

    public int count();

    public int save(User user);
    public int update(User user);
    public int deleteById(int id);

    List<User> findAll();
    List<User> findById(int id);

    public String getNameById();

}
