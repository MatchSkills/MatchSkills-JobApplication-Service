package com.matchskills.jobapplication.service.services;

import com.matchskills.jobapplication.service.domains.ApplicationDomain;
import com.matchskills.jobapplication.service.domains.JobapplicationDomain;
import com.matchskills.jobapplication.service.dtos.*;
import com.matchskills.jobapplication.service.entitys.JobapplicationEntity;
import com.matchskills.jobapplication.service.exceptions.customs.jobapplication.CandidateAlreadyAppliedException;
import com.matchskills.jobapplication.service.exceptions.customs.jobapplication.JobApplicationNotFoundException;
import com.matchskills.jobapplication.service.repositorys.JobapplicationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class JobapplicationService {

    final private JobapplicationRepository repository;
    final private String jobpostingUrl;
    final private RestClient restClient;
    final private MatchSkillsService matchSkillsService;


    public JobapplicationService(JobapplicationRepository repository,
                                 @Value("${jobposting.url}") String jobpostingUrl,
                                 RestClient restClient,
                                 MatchSkillsService matchSkillsService) {
        this.repository = repository;
        this.jobpostingUrl = jobpostingUrl;
        this.restClient = restClient;
        this.matchSkillsService = matchSkillsService;
    }

    public JobApplicationResponse createJobApplication(CreateJobapplicationRequest createJobapplicationRequest) {

        var exists = repository.existsByJobpostingIdAndCandidateId(createJobapplicationRequest.getJobpostingId(), createJobapplicationRequest.getCandidateId());

        if (exists){
            throw new CandidateAlreadyAppliedException();
        }

        var newApplication = JobapplicationEntity.builder()
                .candidateId(createJobapplicationRequest.getCandidateId())
                .jobpostingId(createJobapplicationRequest.getJobpostingId())
                .hardskills(createJobapplicationRequest.getHardskills())
                .build();

        var savedApplication = repository.save(newApplication);

        return savedApplication.toJobapplicationDomain().toJobApplicationResponse();

    }

    public JobApplicationResponse updateSoftSkills(EditSoftSkillsRequest editSoftSkillsRequest) {

        var targetApplication = repository.findById(editSoftSkillsRequest.getId())
                .orElseThrow(JobApplicationNotFoundException::new);

        targetApplication.setSoftskills(editSoftSkillsRequest.getSoftskills());

        var savedApplication = repository.save(targetApplication);

        return savedApplication.toJobapplicationDomain().toJobApplicationResponse();


    }

    public List<MatchSkillsResponse> getResultsByJobpostingId(Long jobpostingId) {

        var jobposting = restClient.get()
                .uri(jobpostingUrl+"/"+jobpostingId)
                .retrieve()
                .body(JobPostingResponse.class);

        var targetSoftskills = jobposting.getTargetSoftskills();
        var targetHardskills = jobposting.getTargetHardskills();

        var list = repository.findAllByJobpostingId(jobpostingId)
                .stream()
                .map(JobapplicationEntity::toJobapplicationDomain)
                .map(JobapplicationDomain::toApplicationDomain)
                .toList();;

        return matchSkillsService.match(targetSoftskills, targetHardskills, list);

    }

}
