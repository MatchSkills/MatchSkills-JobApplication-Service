package com.matchskills.jobapplication.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MatchSkillsResponse {

    private String candidateName;
    private Integer matchSoftSkillsPercent;
    private Integer matchHardSkillsPercent;

}
