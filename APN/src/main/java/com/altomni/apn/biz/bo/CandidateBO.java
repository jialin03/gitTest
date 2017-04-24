package com.altomni.apn.biz.bo;

import com.altomni.apn.model.Contacts;
import com.altomni.apn.model.CandidateCollege;
import com.altomni.apn.model.Experience;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by Alfred Yuan on 4/20/17.
 */
@Data
public class CandidateBO {
    private int id;

    private int referredBy;

    private boolean selfReferred;

    private String email;

    private String email2;

    private String email3;

    private int extraContact;

    private String mobile;

    private String phone;

    private String wechatId;

    private String wechatQr;

    private String linkedinMemberId;

    private String diceId;

    private String profileUrl;

    private String profileUrl2;

    private String profileUrl3;

    private String firstName;

    private String lastName;

    private String genderStr;

    private String photo;

    private int birthyear;

    private int birthmonth;

    private int birthday;

    private int nationality;

    private int degree;

    private int city;

    private String zipcode;

    private int workAuth;

    private Date availableDate;

    private int available;

    private int validated;

    private int expectedSalary;

    private int workSince;

    private String originalResume;

    private String rawFile;

    private int blackListed;

    private Date createdAt;

    private List<Experience> experienceList;

    private Contacts contacts;

    private List<CandidateCollege> candidateCollegeList;
}
