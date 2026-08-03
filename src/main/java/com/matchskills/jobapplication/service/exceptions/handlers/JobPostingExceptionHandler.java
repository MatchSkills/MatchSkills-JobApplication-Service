package com.matchskills.jobapplication.service.exceptions.handlers;

import com.matchskills.jobapplication.service.exceptions.CustomErrorResponse;
import com.matchskills.jobapplication.service.exceptions.customs.jobposting.JobPostingNotFoundException;
import com.matchskills.jobapplication.service.exceptions.customs.jobposting.NotJobPostingOwnerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class JobPostingExceptionHandler {

    @ExceptionHandler(JobPostingNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> jobPostingNotFoundException(JobPostingNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomErrorResponse(exception.getMessage(), 404));
    }

    @ExceptionHandler(NotJobPostingOwnerException.class)
    public ResponseEntity<CustomErrorResponse> notJobPostingOwnerException(NotJobPostingOwnerException exception){
        return  ResponseEntity.status(HttpStatus.FORBIDDEN).body(new CustomErrorResponse(exception.getMessage(), 403));
    }

}
