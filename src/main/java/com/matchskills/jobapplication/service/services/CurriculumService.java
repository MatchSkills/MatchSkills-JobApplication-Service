package com.matchskills.jobapplication.service.services;

import com.matchskills.jobapplication.service.domains.CurriculumDomain;
import com.matchskills.jobapplication.service.dtos.ExtractHardskillsRequest;
import com.matchskills.jobapplication.service.dtos.ExtractHardskillsResponse;
import com.matchskills.jobapplication.service.exceptions.customs.jobapplication.JobApplicationNotFoundException;
import com.matchskills.jobapplication.service.jwt.InternalTokenProvider;
import com.matchskills.jobapplication.service.repositorys.JobapplicationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
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
    public void upload(CurriculumDomain curriculumDomain, Long jobapplicationId) {

        log.atInfo().log("pegando a candidatura");
        var targetJobaplication = repository.findById(jobapplicationId)
                .orElseThrow(JobApplicationNotFoundException::new);

        log.atInfo().log("dando upload do arquivo");
        var storagePath = storageService.upload(curriculumDomain, jobapplicationId, targetJobaplication.getJobpostingId());
        log.atInfo().log("feito");

        log.atInfo().log("guardando nome do arquivo");
        targetJobaplication.setCurriculumPath(storagePath);
        log.atInfo().log("feito");

        log.atInfo().log("gerando url do arquivo");
        var url = storageService.generateSignedUrl(storagePath);
        log.atInfo().log("feito");

        log.atInfo().log("gerando token");
        String internalToken = internalTokenProvider.generate("candidatura-service");
        log.atInfo().log("feito");

        log.atInfo().log("enviando para ia");

        var hardskills =  restClient.post()
                .uri(iaServiceUrl+"/ai/extract-hardskills")
                .header("X-Internal-Token", internalToken)
                .body(new ExtractHardskillsRequest(url))
                .retrieve()
                .body(ExtractHardskillsResponse.class);
        log.atInfo().log("recebido da ia");

        targetJobaplication.setHardskills(hardskills.getHardskills());
        log.atInfo().log("guardando info");

        repository.save(targetJobaplication);
        log.atInfo().log("info salva");

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
