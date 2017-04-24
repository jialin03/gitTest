package com.altomni.apn.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.altomni.apn.model.User;

public interface UserRepo extends PagingAndSortingRepository<User, String> {
	User findByName(String name);
}
