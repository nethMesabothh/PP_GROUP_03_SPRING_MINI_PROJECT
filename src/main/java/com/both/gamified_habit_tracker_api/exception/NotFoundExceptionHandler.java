package com.both.gamified_habit_tracker_api.exception;

public class NotFoundExceptionHandler extends RuntimeException {
    public NotFoundExceptionHandler(String message) {
        super(message);
    }
}