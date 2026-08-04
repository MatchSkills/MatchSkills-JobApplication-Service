package com.matchskills.jobapplication.service.services;

import com.matchskills.jobapplication.service.exceptions.customs.jobapplication.JobApplicationNotFoundException;
import com.matchskills.jobapplication.service.repositorys.JobapplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CurriculumService {

    private final SupabaseStorageService storageService;
    private final JobapplicationRepository repository;

    public  CurriculumService(SupabaseStorageService storageService, JobapplicationRepository repository) {
        this.storageService = storageService;
        this.repository = repository;
    }

    public void upload(MultipartFile file, Long jobapplicationId) {

        var targetJobaplication = repository.findById(jobapplicationId)
                .orElseThrow(JobApplicationNotFoundException::new);

        var storagePath = storageService.upload(file, jobapplicationId);

        targetJobaplication.setCurriculumPath(storagePath);

        repository.save(targetJobaplication);
    }

    public String getResumeUrl(Long id) {

        var targetApplication = repository.findById(id)
                .orElseThrow(JobApplicationNotFoundException::new);

        return storageService.generateSignedUrl(targetApplication.getCurriculumPath());
    }

    public void delete(Long id) {

        var targetApplication = repository.findById(id)
                .orElseThrow(JobApplicationNotFoundException::new);

        storageService.delete(targetApplication.getCurriculumPath());

        repository.delete(targetApplication);
    }
}
