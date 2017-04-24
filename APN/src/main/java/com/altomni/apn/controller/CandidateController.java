package com.altomni.apn.controller;

import com.altomni.apn.service.CandidateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Alfred Yuan on 4/20/17.
 */
@Slf4j
@RestController
public class CandidateController {
    @Autowired
    private CandidateService candidateService;
}
