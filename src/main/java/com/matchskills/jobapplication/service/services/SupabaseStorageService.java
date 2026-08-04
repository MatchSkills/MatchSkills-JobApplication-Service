package com.matchskills.jobapplication.service.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class SupabaseStorageService{

    @Value("${supabase.url}")
    private String url;

    @Value("${supabase.service-role-key}")
    private String key;

    @Value("${supabase.bucket}")
    private String bucket;

    private final RestClient client = RestClient.create();

    public String upload(MultipartFile file, Long jobapplicationId) {

        try {

            String extension = "";

            if (file.getOriginalFilename() != null &&
                    file.getOriginalFilename().contains(".")) {

                extension = file.getOriginalFilename()
                        .substring(file.getOriginalFilename().lastIndexOf("."));
            }

            String fileUrl =
                    "jobapplication/" +
                    jobapplicationId +
                    "/" +
                    UUID.randomUUID() +
                    extension;

            client.post()
                    .uri(url + "/storage/v1/object/" + bucket + "/" + fileUrl)
                    .header("apikey", key)
                    .header("Authorization", "Bearer " + key)
                    .contentType(MediaType.parseMediaType(file.getContentType()))
                    .body(file.getBytes())
                    .retrieve()
                    .toBodilessEntity();

            return fileUrl;

        } catch (IOException e) {
            throw new RuntimeException("Erro ao enviar arquivo.", e);
        }
    }

    public String generateSignedUrl(String path) {

        Map response = client.post()
                .uri(url + "/storage/v1/object/sign/" + bucket + "/" + path)
                .header("apikey", key)
                .header("Authorization", "Bearer " + key)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("expiresIn", 600))
                .retrieve()
                .body(Map.class);

        String signedPath = (String) response.get("signedURL");

        return url + "/storage/v1" + signedPath;
    }

    public void delete(String path) {

        client.delete()
                .uri(url + "/storage/v1/object/" + bucket + "/" + path)
                .header("apikey", key)
                .header("Authorization", "Bearer " + key)
                .retrieve()
                .toBodilessEntity();
    }
}
