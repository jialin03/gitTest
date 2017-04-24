package com.altomni.apn.service;

import java.util.List;

import com.altomni.apn.model.User;

public interface UserService {

	public User findById(String id);

	public User findByName(String name);

	public void saveUser(User user);

	public void deleteUserById(String id);

	public List<User> findAllUsers();

	public void deleteAllUsers();

	public boolean isUserExist(User user);

}
