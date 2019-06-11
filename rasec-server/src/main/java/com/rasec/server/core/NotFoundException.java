package com.rasec.server.core;

public class NotFoundException extends RuntimeException{
    NotFoundException(){
        super("404 NOT FOUND");
    }
    NotFoundException(String msg){
        super("404 NOT FOUND\n" + msg);
    }
}
