package com.matchskills.jobapplication.service.domains;

import com.matchskills.jobapplication.service.dtos.JobApplicationResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class JobapplicationDomain {

    private Long id;
    private Long jobpostingId;
    private Long candidateId;
    private String candidateName;
    private List<String> hardskills;
    private Map<String, Integer> softskills;
    private LocalDate createAt;

    public ApplicationDomain toApplicationDomain(){
        return new ApplicationDomain(this.candidateName, this.softskills, this.hardskills);
    }

    public JobApplicationResponse toJobApplicationResponse(){
        return new JobApplicationResponse(this.id,this.jobpostingId,this.candidateId,this.candidateName,this.hardskills,this.softskills,this.createAt);
    }

}
