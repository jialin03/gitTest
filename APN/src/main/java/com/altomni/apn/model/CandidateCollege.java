package com.altomni.apn.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Data
@Entity
@Table (name="candidate_colleges")
public class CandidateCollege {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name="candidateId")
    private int CandidateId;
    @Column(name = "collegeId")
    private int collegeId;
    @Column(name = "startDate")
    private Date startDate;
    @Column(name="endDate")
    private Date endDate;
    @Column(name="degree")
    private int degree;
    @Column(name="majorId")
    private int majorId;
}
