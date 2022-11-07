package ru.pletneva.coursework.exceptions;

public class InvalidJwtAutenticationException extends RuntimeException{
    public InvalidJwtAutenticationException(String message) {
        super(message);
    }
}
