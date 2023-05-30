package com.example.shortener.link.dto;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException() {
        super("wrong password");
    }
}
