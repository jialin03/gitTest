package com.altomni.apn.service.impl;

import com.altomni.apn.model.CandidateCollege;
import com.altomni.apn.repository.CandidateCollegeRepo;
import com.altomni.apn.service.CandidateCollegeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by JIALIN on 4/20/2017.
 */

@Slf4j
@Service("candidateCollegeService")
public class CandidateCollegeServiceImpl implements CandidateCollegeService{
    @Autowired
    private CandidateCollegeRepo candidateCollegeRepo;

    public CandidateCollege findByCandidateCollegeId(int collegeId) {
        log.debug("findByCandidateCollegeId is executed");
        return candidateCollegeRepo.findOne(collegeId);
    }

    public List<CandidateCollege> findByCandidateId(int candidateId) {
        log.debug("findByCandidateId is executed");
        return candidateCollegeRepo.findByCandidateId(candidateId);
    }

    public void saveAllCandidateCollege(List<CandidateCollege> candidateCollegeList) {
        log.debug("saveAllCandidateCollege is executed");
        candidateCollegeRepo.save(candidateCollegeList);
    }

    public void saveCandidateCollege(CandidateCollege candidateCollege) {
        log.debug("saveCandidateCollege is executed");
        candidateCollegeRepo.save(candidateCollege);
    }
}
