package com.matchskills.jobapplication.service.exceptions.customs.jobapplication;

public class NotJobApplicationOwnerException extends RuntimeException {
    public NotJobApplicationOwnerException() {
        super("This job application does not belong to this candidate.");
    }
}
