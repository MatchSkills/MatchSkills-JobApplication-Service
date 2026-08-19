package com.matchskills.jobapplication.service.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CurriculumDomain {

    private byte[] file;
    private String filename;
    private String contentType;

}
