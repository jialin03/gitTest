package com.altomni.apn.service.impl;
import com.altomni.apn.repository.ExperienceRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.altomni.apn.model.Experience;
import com.altomni.apn.service.ExperienceService;

import java.util.List;

/**
 * Created by JIALIN on 4/22/2017.
 */
@Slf4j
@Service("experienceService")
public class ExperienceServiceImpl implements ExperienceService{
    @Autowired
    private ExperienceRepo experienceRepo;

    public List<Experience> findByCandidateId(int candidateId){
        log.debug("findByCandidateId is executed");
        return experienceRepo.findByCandidateId(candidateId);
    }
    public Experience findByCompanyId(int companyId){
        log.debug("findByCompanyID is executed");
        return experienceRepo.findOne(companyId);
    }
    public void saveAllExperience (List<Experience> experienceList){
        log.debug("saveAllExperience is executed");
        experienceRepo.save(experienceList);
    }
    public void saveExperience (Experience experience){
        log.debug("saveExperience is executed");
        experienceRepo.save(experience);
    }
}
