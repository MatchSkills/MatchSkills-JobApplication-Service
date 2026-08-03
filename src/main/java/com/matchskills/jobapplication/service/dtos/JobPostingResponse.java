package com.matchskills.jobapplication.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class JobPostingResponse {

    private Long id;
    private Long companyId;
    private String title;
    private String description;
    private String local;
    private LocalDate createAt;
    private Map<String, Integer> targetSoftskills;
    private List<String> targetHardskills;


}
