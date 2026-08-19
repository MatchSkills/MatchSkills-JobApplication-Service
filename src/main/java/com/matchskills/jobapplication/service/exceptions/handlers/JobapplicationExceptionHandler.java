package com.matchskills.jobapplication.service.exceptions.handlers;

import com.matchskills.jobapplication.service.exceptions.CustomErrorResponse;
import com.matchskills.jobapplication.service.exceptions.customs.jobapplication.CandidateAlreadyAppliedException;
import com.matchskills.jobapplication.service.exceptions.customs.jobapplication.JobApplicationNotFoundException;
import com.matchskills.jobapplication.service.exceptions.customs.jobapplication.NotJobApplicationOwnerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class JobapplicationExceptionHandler {

    @ExceptionHandler(CandidateAlreadyAppliedException.class)
    public ResponseEntity<CustomErrorResponse> handlerCandidateAlreadyAppliedException(CandidateAlreadyAppliedException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomErrorResponse(exception.getMessage(), 409));
    }

    @ExceptionHandler(JobApplicationNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handlerJobApplicationNotFoundException(JobApplicationNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomErrorResponse(exception.getMessage(), 404));
    }

    @ExceptionHandler(NotJobApplicationOwnerException.class)
    public  ResponseEntity<CustomErrorResponse> handlerNotJobApplicationOwnerException(NotJobApplicationOwnerException exception){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new CustomErrorResponse(exception.getMessage(), 403));
    }

}
