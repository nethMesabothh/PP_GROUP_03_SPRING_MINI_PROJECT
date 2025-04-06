package com.both.gamified_habit_tracker_api.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse<T> {
    private boolean success;
    private String message;
    private HttpStatus status;
    private T payload;
    private LocalDateTime time;
}
