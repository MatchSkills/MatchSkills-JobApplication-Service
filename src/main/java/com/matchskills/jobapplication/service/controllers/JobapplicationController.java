package com.matchskills.jobapplication.service.controllers;

import com.matchskills.jobapplication.service.dtos.CreateJobapplicationRequest;
import com.matchskills.jobapplication.service.dtos.EditSoftSkillsRequest;
import com.matchskills.jobapplication.service.dtos.JobApplicationResponse;
import com.matchskills.jobapplication.service.dtos.MatchSkillsResponse;
import com.matchskills.jobapplication.service.services.JobapplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job-application")
public class JobapplicationController {

    final private JobapplicationService jobapplicationService;

    public JobapplicationController(JobapplicationService jobapplicationService) {
        this.jobapplicationService = jobapplicationService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('Candidate')")
    public ResponseEntity<JobApplicationResponse> createJobaplication(CreateJobapplicationRequest createJobapplicationRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(jobapplicationService.createJobApplication(createJobapplicationRequest));
    }

    @PutMapping("/edit-softskills")
    public ResponseEntity<JobApplicationResponse> editSoftSkills(EditSoftSkillsRequest editSoftSkillsRequest){
        return ResponseEntity.status(HttpStatus.OK).body(jobapplicationService.updateSoftSkills(editSoftSkillsRequest));
    }

    @PreAuthorize("hasRole('Company')")
    @GetMapping("/jobposting/{id}")
    public ResponseEntity<List<MatchSkillsResponse>> getMatchSkills(@PathVariable Long id){
        return  ResponseEntity.status(HttpStatus.OK).body(jobapplicationService.getResultsByJobpostingId(id));
    }

}
