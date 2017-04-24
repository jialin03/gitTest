package com.altomni.apn.service;

/**
 * Created by JIALIN on 4/22/2017.
 */
import com.altomni.apn.model.Experience;

import java.util.List;

public interface ExperienceService {
    public List<Experience> findByCandidateId(int candidateId);
    public Experience findByCompanyId(int companyId);
    public void saveAllExperience (List<Experience> experienceList);
    public void saveExperience (Experience experience);
}
