package com.matchskills.jobapplication.service.exceptions.customs.jobapplication;

public class JobApplicationNotFoundException extends RuntimeException {
    public JobApplicationNotFoundException() {
        super("Job Application Not Found");
    }
}
