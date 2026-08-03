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
public class JobApplicationResponse {

    private Long id;
    private Long jobpostingId;
    private Long candidateId;
    private String candidateName;
    private List<String> hardskills;
    private Map<String, Integer> softskills;
    private LocalDate createAt;

}
