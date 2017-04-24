package com.altomni.apn.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table (name="candidate_experiences")
public class Experience {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "candidateId")
    private int candidateId;
    @Column(name = "companyId")
    private int companyId;
    @Column(name="startDate")
    private Date startDate;
    @Column(name="endDate")
    private Date endDate;
    @Column(name="title")
    private String title;
    @Column(name="salary")
    private int salary;
    @Column(name="bonus")
    private int bonus;
    @Column(name="stock")
    private int stock;
    @Column(name="city")
    private int city;
    @Column(name="company2")
    private String company2;
}