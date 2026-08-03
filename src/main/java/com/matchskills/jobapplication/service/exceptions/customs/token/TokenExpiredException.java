package com.matchskills.jobapplication.service.exceptions.customs.token;

public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException() {
        super("Token is expired");
    }

}
