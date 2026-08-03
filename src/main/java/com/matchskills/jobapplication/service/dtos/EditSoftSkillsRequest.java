package com.matchskills.jobapplication.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class EditSoftSkillsRequest {

    private Long id;
    private Map<String, Integer> softskills;

}
