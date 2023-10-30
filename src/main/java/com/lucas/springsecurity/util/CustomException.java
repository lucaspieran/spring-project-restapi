package com.lucas.springsecurity.util;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private String message;
    private HttpStatus status;
}
