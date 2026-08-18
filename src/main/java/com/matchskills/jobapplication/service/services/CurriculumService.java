package com.matchskills.jobapplication.service.services;

import com.matchskills.jobapplication.service.dtos.ExtractHardskillsRequest;
import com.matchskills.jobapplication.service.dtos.ExtractHardskillsResponse;
import com.matchskills.jobapplication.service.exceptions.customs.jobapplication.JobApplicationNotFoundException;
import com.matchskills.jobapplication.service.jwt.InternalTokenProvider;
import com.matchskills.jobapplication.service.repositorys.JobapplicationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CurriculumService {

    private final SupabaseStorageService storageService;
    private final JobapplicationRepository repository;
    private final RestClient restClient = RestClient.create();
    private final String iaServiceUrl;
    private final InternalTokenProvider internalTokenProvider;

    public  CurriculumService(SupabaseStorageService storageService,
                              JobapplicationRepository repository,
                              @Value("${ia.url}") String iaServiceUrl,
                              InternalTokenProvider internalTokenProvider) {
        this.storageService = storageService;
        this.repository = repository;
        this.iaServiceUrl = iaServiceUrl;
        this.internalTokenProvider = internalTokenProvider;
    }

    @Async
    public void upload(MultipartFile file, Long jobapplicationId) {

        var targetJobaplication = repository.findById(jobapplicationId)
                .orElseThrow(JobApplicationNotFoundException::new);

        var storagePath = storageService.upload(file, jobapplicationId);

        targetJobaplication.setCurriculumPath(storagePath);

        var url = storageService.generateSignedUrl(storagePath);

        String internalToken = internalTokenProvider.generate("candidatura-service");

        var hardskills =  restClient.post()
                .uri(iaServiceUrl + "/extract-hardskills")
                .header("X-Internal-Token", internalToken)
                .body(new ExtractHardskillsRequest(url))
                .retrieve()
                .body(ExtractHardskillsResponse.class);

        targetJobaplication.setHardskills(hardskills.getHardskills());

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
