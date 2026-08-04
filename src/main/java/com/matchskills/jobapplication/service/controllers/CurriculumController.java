package com.matchskills.jobapplication.service.controllers;

import com.matchskills.jobapplication.service.services.CurriculumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/curriculum")
@RequiredArgsConstructor
public class CurriculumController {

    private final CurriculumService service;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        value = "/job-application/{jobApplicationId}" )
    public ResponseEntity<Void> upload(@PathVariable Long jobApplicationId,
                                       @RequestParam MultipartFile file) {
        service.upload(file, jobApplicationId);

        return  ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> download(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getResumeUrl(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
