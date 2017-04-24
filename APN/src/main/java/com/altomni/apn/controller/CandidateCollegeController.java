package com.altomni.apn.controller;

import com.altomni.apn.service.CandidateCollegeService;
import com.altomni.apn.model.CandidateCollege;
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


/**
 * Created by JIALIN on 4/21/2017.
 */
@Slf4j
@RestController
public class CandidateCollegeController {
    @Autowired
    private CandidateCollegeService candidateCollegeService;

    // get college information from the college ID
    @PreAuthorize("#oauth2.hasScope('CandidateCollege') and #oauth2.hasScope('read')")
    @RequestMapping(value = "/candidateCollege/{collegeId}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<CandidateCollege> getCandidateCollege(@PathVariable("collegeId") int collegeId) {
        CandidateCollege candidateCollege = this.getCandidateCollegeService().findByCandidateCollegeId(collegeId);
        if(candidateCollege == null) {
            log.debug("CandidateCollege with collegeId" + collegeId + "not found");
            return new ResponseEntity<CandidateCollege>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<CandidateCollege>(candidateCollege, HttpStatus.OK);
    }

    // get all college information from the candidateId
    @PreAuthorize("#oauth2.hasScope('CandidateCollege') and #oauth2.hasScope('read')")
    @RequestMapping(value = "/candidate/{candidateId}/candidateCollege", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<List<CandidateCollege>> getCandidateCollegeList(@PathVariable ("candidateId") int candidateId) {
        List<CandidateCollege> candidateCollegeList = this.getCandidateCollegeService().findByCandidateId(candidateId);
        if(candidateCollegeList == null) {
            log.debug("CandidateCollegeList with candidateId" + candidateId + "not found");
            return new ResponseEntity<List<CandidateCollege>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<CandidateCollege>>(candidateCollegeList, HttpStatus.OK);
    }

    // create college
    @PreAuthorize("#oauth2.hasScope('CandidateCollege') and #oauth2.hasScope('write') and hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/candidateCollege/", method = RequestMethod.POST)
    public ResponseEntity<Void> createCandidateCollege(@RequestBody CandidateCollege candidateCollege, UriComponentsBuilder ucBuilder) {
        log.debug("Creating CandidateCollege" + candidateCollege.getCandidateId()+ candidateCollege.getCollegeId());

        this.getCandidateCollegeService().saveCandidateCollege(candidateCollege);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/candidateCollege/{collegeId}").buildAndExpand(candidateCollege.getCollegeId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //update college
    @PreAuthorize("#oauth2.hasScope('CandidateCollege') and #oauth2.hasScope('write') and hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/candidateCollege/{collegeId}", method = RequestMethod.PUT)
    public ResponseEntity<CandidateCollege> updateCandidateCollege(@PathVariable("collegeId") int collegeId, @RequestBody CandidateCollege candidateCollege) {
        log.debug("Updating CandidateCollege" + collegeId);
        CandidateCollege currentCandidateCollege = this.getCandidateCollegeService().findByCandidateCollegeId(collegeId);

        if (currentCandidateCollege == null) {
            log.debug("CandidateCollege with collegeId " + collegeId + " not found");
            return new ResponseEntity<CandidateCollege>(HttpStatus.NOT_FOUND);
        }

        currentCandidateCollege.setCollegeId(candidateCollege.getCollegeId());
        currentCandidateCollege.setStartDate(candidateCollege.getStartDate());
        currentCandidateCollege.setEndDate(candidateCollege.getEndDate());
        currentCandidateCollege.setDegree(candidateCollege.getDegree());
        currentCandidateCollege.setMajorId(candidateCollege.getMajorId());

        this.getCandidateCollegeService().saveCandidateCollege(currentCandidateCollege);
        return new ResponseEntity<CandidateCollege>(currentCandidateCollege, HttpStatus.OK);
    }


    public CandidateCollegeService getCandidateCollegeService() {
        return candidateCollegeService;
    }

    public void setCandidateCollegeService(CandidateCollegeService candidateCollegeService) {
        this.candidateCollegeService = candidateCollegeService;
    }
}
