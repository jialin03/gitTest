package com.altomni.apn.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Alfred Yuan on 4/19/17.
 */
@Data
@Entity
@Table(name = "Candidates")
public class Candidate {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "referredBy")
    private int referredBy;
    @Column(name = "selfReferred")
    private boolean selfReferred;
    @Column(name = "email")
    private String email;
    @Column(name = "email2")
    private String email2;
    @Column(name = "email3")
    private String email3;
    @Column(name = "extraContact")
    private int extraContact;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "phone")
    private String phone;
    @Column(name = "wechatId")
    private String wechatId;
    @Column(name = "wechatQr")
    private String wechatQr;
    @Column(name = "linkedinMemberId")
    private String linkedinMemberId;
    @Column(name = "diceId")
    private String diceId;
    @Column(name = "profileUrl")
    private String profileUrl;
    @Column(name = "profileUrl2")
    private String profileUrl2;
    @Column(name = "profileUrl3")
    private String profileUrl3;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "gender")
    private int gender;
    @Column(name = "photo")
    private String photo;
    @Column(name = "birthyear")
    private int birthyear;
    @Column(name = "birthmonth")
    private int birthmonth;
    @Column(name = "birthday")
    private int birthday;
    @Column(name = "nationality")
    private int nationality;
    @Column(name = "degree")
    private int degree;
    @Column(name = "city")
    private int city;
    @Column(name = "zipcode")
    private String zipcode;
    @Column(name = "workAuth")
    private int workAuth;
    @Column(name = "availableDate")
    private Date availableDate;
    @Column(name = "available")
    private int available;
    @Column(name = "validated")
    private int validated;
    @Column(name = "expectedSalary")
    private int expectedSalary;
    @Column(name = "workSince")
    private int workSince;
    @Column(name = "originalResume")
    private String originalResume;
    @Column(name = "rawFile")
    private String rawFile;
    @Column(name = "blackListed")
    private int blackListed;
    @Column(name = "createdAt")
    private Date createdAt;
}
