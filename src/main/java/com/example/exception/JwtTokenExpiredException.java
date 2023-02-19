package com.example.exception;

/**
 * @author 1ommy
 * @version 18.02.2023
 */
public class JwtTokenExpiredException extends Exception{
    public JwtTokenExpiredException(String message) {
        super(message);
    }
}
