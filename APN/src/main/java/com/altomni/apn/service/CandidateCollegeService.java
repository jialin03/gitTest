package com.altomni.apn.service;

import com.altomni.apn.model.CandidateCollege;

import java.util.List;

/**
 * Created by JIALIN on 4/20/2017.
 */
public interface CandidateCollegeService {
    public List<CandidateCollege>  findByCandidateId(int candidateId);
    public CandidateCollege findByCandidateCollegeId(int collegeId);
    public void saveAllCandidateCollege (List<CandidateCollege> candidateCollegeList);
    public void saveCandidateCollege (CandidateCollege candidateCollege);
}
