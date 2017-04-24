package com.altomni.apn.controller;

import com.altomni.apn.model.User;
import com.altomni.apn.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Slf4j
@RestController
public class AppRestController {
    @Autowired
    private UserService userService;

    @PreAuthorize("#oauth2.hasScope('user') and #oauth2.hasScope('read')")
    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = this.getUserService().findAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @PreAuthorize("#oauth2.hasScope('user') and #oauth2.hasScope('read')")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<User> getUser(@PathVariable("id") String id) {
        log.debug("Fetching User with id " + id);
        User user = this.getUserService().findById(id);
        if (user == null) {
            log.debug("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PreAuthorize("#oauth2.hasScope('user') and #oauth2.hasScope('write') and hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        log.debug("Creating User " + user.getName());

        this.getUserService().saveUser(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PreAuthorize("#oauth2.hasScope('user') and #oauth2.hasScope('write') and hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("id") String id, @RequestBody User user) {
        log.debug("Updating User " + id);

        User currentUser = this.getUserService().findById(id);

        if (currentUser == null) {
            log.debug("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        currentUser.setName(user.getName());
        currentUser.setAge(user.getAge());
        currentUser.setSalary(user.getSalary());

        this.getUserService().saveUser(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }

    @PreAuthorize("#oauth2.hasScope('user') and #oauth2.hasScope('write') and hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<User> mergeUser(@PathVariable("id") String id, @RequestBody User user) {
        log.debug("Merging User " + id);

        if (!user.getId().equals(id)) {
            log.debug("User id not matched");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        User currentUser = this.getUserService().findById(id);

        if (currentUser == null) {
            log.debug("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        if (null != user.getName() && !user.getName().equals("")) {
            currentUser.setName(user.getName());
        }
        if (null != user.getAge() && !user.getAge().equals("")) {
            currentUser.setAge(user.getAge());
        }
        if (null != user.getSalary() && !user.getSalary().equals("")) {
            currentUser.setSalary(user.getSalary());
        }

        this.getUserService().saveUser(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }

    @PreAuthorize("#oauth2.hasScope('user') and #oauth2.hasScope('trust') and hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") String id) {
        log.debug("Fetching & Deleting User with id " + id);

        User user = this.getUserService().findById(id);
        if (user == null) {
            log.debug("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        this.getUserService().deleteUserById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("#oauth2.hasScope('user') and #oauth2.hasScope('trust') and hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/user/", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteAllUsers() {
        log.debug("Deleting All Users");

        this.getUserService().deleteAllUsers();
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}