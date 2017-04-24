package com.altomni.apn.controller;

/**
 * Created by JIALIN on 4/22/2017.
 */
import com.altomni.apn.model.Experience;
import com.altomni.apn.service.ExperienceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
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
public class ExperienceController {
    @Autowired
    private ExperienceService experienceService;

    @PreAuthorize("#oauth2.hasScope('Experience') and #oauth2.hasScope('read')")
    @RequestMapping(value = "/candidate/{candidateId}/experience", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<List<Experience>> getExperienceList(@PathVariable ("candidateId") int candidateId) {
        List<Experience> experienceList = this.getExperienceService().findByCandidateId(candidateId);
        if(experienceList == null) {
            log.debug("ExperienceList with candidateId" + candidateId + "not found");
            return new ResponseEntity<List<Experience>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Experience>>(experienceList, HttpStatus.OK);
    }

    @PreAuthorize("#oauth2.hasScope('Experience') and #oauth2.hasScope('write') and hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/experience/", method = RequestMethod.POST)
    public ResponseEntity<Void> createExperience(@RequestBody Experience experience, UriComponentsBuilder ucBuilder) {
        log.debug("Creating Experience" + experience.getCandidateId() + experience.getCompanyId());

        this.getExperienceService().saveExperience(experience);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/experience/{companyId}").buildAndExpand(experience.getCompanyId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //update college
    @PreAuthorize("#oauth2.hasScope('CandidateCollege') and #oauth2.hasScope('write') and hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/candidateCollege/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Experience> updateExperience(@PathVariable("companyId") int companyId, @RequestBody Experience experience) {
        log.debug("Updating CandidateCollege" + companyId);
        Experience currentExperience = this.getExperienceService().findByCompanyId(companyId);

        if (currentExperience == null) {
            log.debug("CandidateCollege with companyId " + companyId + " not found");
            return new ResponseEntity<Experience>(HttpStatus.NOT_FOUND);
        }

        currentExperience.setCompanyId(experience.getCompanyId());
        currentExperience.setStartDate(experience.getStartDate());
        currentExperience.setEndDate(experience.getEndDate());
        currentExperience.setTitle(experience.getTitle());
        currentExperience.setSalary(experience.getSalary());
        currentExperience.setSalary(experience.getBonus());
        currentExperience.setSalary(experience.getStock());
        currentExperience.setCity(experience.getCity());
        currentExperience.setCompany2(experience.getCompany2());

        this.getExperienceService().saveExperience(currentExperience);
        return new ResponseEntity<Experience>(currentExperience, HttpStatus.OK);
    }

    public ExperienceService getExperienceService() {
        return experienceService;
    }

    public void setExperienceService(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }
}
