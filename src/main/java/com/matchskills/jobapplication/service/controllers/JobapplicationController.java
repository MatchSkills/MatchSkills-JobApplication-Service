package com.matchskills.jobapplication.service.controllers;

import com.matchskills.jobapplication.service.dtos.CreateJobapplicationRequest;
import com.matchskills.jobapplication.service.dtos.EditSoftSkillsRequest;
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
    public ResponseEntity<Void> createJobaplication(CreateJobapplicationRequest createJobapplicationRequest){
        jobapplicationService.createJobApplication(createJobapplicationRequest);


        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/edit-softskills")
    public ResponseEntity<Void> editSoftSkills(EditSoftSkillsRequest editSoftSkillsRequest){

        jobapplicationService.updateSoftSkills(editSoftSkillsRequest);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PreAuthorize("hasRole('Company')")
    @GetMapping("/jobposting/{id}")
    public ResponseEntity<List<MatchSkillsResponse>> getMatchSkills(@PathVariable Long id){
        return  ResponseEntity.status(HttpStatus.OK).body(jobapplicationService.getResultsByJobpostingId(id));
    }

}
