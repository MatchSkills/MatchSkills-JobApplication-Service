package com.matchskills.interview.service.exceptions.customs.token;

public class TokenInvalidException extends RuntimeException {
    public TokenInvalidException() {
        super("Token is invalid");
    }
}
