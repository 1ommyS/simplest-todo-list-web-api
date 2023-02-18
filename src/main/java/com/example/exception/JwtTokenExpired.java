package com.example.exception;

/**
 * @author 1ommy
 * @version 18.02.2023
 */
public class JwtTokenExpired extends Exception{
    public JwtTokenExpired(String message) {
        super(message);
    }
}
