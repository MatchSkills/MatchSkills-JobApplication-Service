package com.matchskills.jobapplication.service.repositorys;

import com.matchskills.jobapplication.service.entitys.JobapplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobapplicationRepository extends JpaRepository<JobapplicationEntity, Long> {

    Boolean existsByJobpostingIdAndCandidateId(Long jobpostingId, Long candidateId);

    List<JobapplicationEntity> findAllByJobpostingId(Long jobpostingId);

}
