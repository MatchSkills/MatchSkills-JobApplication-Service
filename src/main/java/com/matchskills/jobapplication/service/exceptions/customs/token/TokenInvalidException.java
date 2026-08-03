package com.matchskills.jobapplication.service.exceptions.customs.token;

public class TokenInvalidException extends RuntimeException {
    public TokenInvalidException() {
        super("Token is invalid");
    }
}
