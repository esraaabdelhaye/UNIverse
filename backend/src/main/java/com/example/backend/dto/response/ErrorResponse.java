package com.example.backend.dto.response;

import java.time.LocalDateTime;

public class ErrorResponse {
    private Integer statusCode;
    private String message;
    private String errorCode;
    private String details;
    private LocalDateTime timestamp;
    private String path;

    public ErrorResponse() {}

    public ErrorResponse(Integer statusCode, String message, String errorCode,
                         String details, LocalDateTime timestamp, String path) {
        this.statusCode = statusCode;
        this.message = message;
        this.errorCode = errorCode;
        this.details = details;
        this.timestamp = timestamp;
        this.path = path;
    }

    public Integer getStatusCode() { return statusCode; }
    public void setStatusCode(Integer statusCode) { this.statusCode = statusCode; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getErrorCode() { return errorCode; }
    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
}
