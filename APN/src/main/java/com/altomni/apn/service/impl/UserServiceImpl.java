package com.altomni.apn.service.impl;

import com.altomni.apn.model.User;
import com.altomni.apn.repository.UserRepo;
import com.altomni.apn.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepository;

    public List<User> findAllUsers() {
        log.debug("findAllUsers is executed");
        return (List<User>) this.getUserRepository().findAll();
    }

    public User findById(String id) {
        log.debug("findById is executed");
        return this.getUserRepository().findOne(id);
    }

    public User findByName(String name) {
        log.debug("findByName is executed");
        return this.getUserRepository().findByName(name);
    }

    public void saveUser(User user) {
        log.debug("saveUser is executed");
        this.getUserRepository().save(user);
    }

    public void deleteUserById(String id) {
        log.debug("deleteUserById is executed");
        this.getUserRepository().delete(id);
    }

    public boolean isUserExist(User user) {
        log.debug("isUserExist is executed");
        return findByName(user.getName()) != null;
    }

    public void deleteAllUsers() {
        log.debug("deleteAllUsers is executed");
        this.getUserRepository().deleteAll();
    }

    public UserRepo getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

}
