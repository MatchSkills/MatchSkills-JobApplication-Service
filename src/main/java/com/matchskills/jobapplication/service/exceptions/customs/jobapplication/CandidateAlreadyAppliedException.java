package com.matchskills.jobapplication.service.exceptions.customs.jobapplication;

public class CandidateAlreadyAppliedException extends RuntimeException {
    public CandidateAlreadyAppliedException() {
        super("The candidate has already applied.");
    }
}
