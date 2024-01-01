package com.socialsync.gateway.filter.exceptions;

public class UnauthorizedException extends Exception{
    public UnauthorizedException(){
        super();
    }

    public UnauthorizedException(String s){
        super(s);
    }
}
