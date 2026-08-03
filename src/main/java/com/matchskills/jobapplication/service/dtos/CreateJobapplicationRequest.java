package com.matchskills.jobapplication.service.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class CreateJobapplicationRequest {

    @NotNull
    private Long jobpostingId;

    @NotNull
    private Long candidateId;

    @NotBlank
    private String candidateName;

    @NotNull
    private List<String> hardskills;

    @NotNull
    private Map<String, Integer> softskills;


}
