package com.matchskills.jobapplication.service.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class ApplicationDomain {

    private String candidateName;
    private Map<String, Integer> softSkills;
    private List<String> hardSkills;

}
