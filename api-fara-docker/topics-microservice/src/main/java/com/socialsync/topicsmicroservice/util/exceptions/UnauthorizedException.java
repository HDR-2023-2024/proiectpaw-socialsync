package com.socialsync.topicsmicroservice.util.exceptions;

public class UnauthorizedException extends Exception{
    public UnauthorizedException(){
        super();
    }

    public UnauthorizedException(String s){
        super(s);
    }
}
