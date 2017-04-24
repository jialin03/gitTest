package com.altomni.apn.repository;

import com.altomni.apn.model.Candidate;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Alfred Yuan on 4/20/17.
 */
public interface CandidateRepo extends PagingAndSortingRepository<Candidate, String> {
}
