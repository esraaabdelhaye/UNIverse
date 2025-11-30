package com.example.backend.dto.response;

import java.time.LocalDateTime;

public class ApiResponse<T> {
    private Integer statusCode;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    private String traceId;

    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(Integer statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(Integer statusCode, String message, T data, LocalDateTime timestamp, String traceId) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
        this.traceId = traceId;
    }

    public ApiResponse(Integer statusCode, String message, T data, LocalDateTime timestamp) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public static class Builder<T> {
        private Integer statusCode;
        private String message;
        private T data;
        private LocalDateTime timestamp = LocalDateTime.now();
        private String traceId;

        public Builder<T> statusCode(Integer statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public Builder<T> timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder<T> traceId(String traceId) {
            this.traceId = traceId;
            return this;
        }

        public ApiResponse<T> build() {
            return new ApiResponse<>(statusCode, message, data, timestamp, traceId);
        }
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "Success", data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(200, message, data);
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(201, "Resource created successfully", data);
    }

    public static <T> ApiResponse<T> created(String message, T data) {
        return new ApiResponse<>(201, message, data);
    }

    public static <T> ApiResponse<T> accepted(T data) {
        return new ApiResponse<>(202, "Request accepted", data);
    }

    public static <T> ApiResponse<T> noContent() {
        return new ApiResponse<>(204, "No content");
    }

    public static <T> ApiResponse<T> badRequest(String message) {
        return new ApiResponse<>(400, message);
    }

    public static <T> ApiResponse<T> unauthorized(String message) {
        return new ApiResponse<>(401, message);
    }

    public static <T> ApiResponse<T> forbidden(String message) {
        return new ApiResponse<>(403, message);
    }

    public static <T> ApiResponse<T> notFound(String message) {
        return new ApiResponse<>(404, message);
    }

    public static <T> ApiResponse<T> conflict(String message) {
        return new ApiResponse<>(409, message);
    }

    public static <T> ApiResponse<T> internalServerError(String message) {
        return new ApiResponse<>(500, message);
    }

    public static <T> ApiResponse<T> serviceUnavailable(String message) {
        return new ApiResponse<>(503, message);
    }

    public Boolean isSuccess() {
        return statusCode != null && statusCode >= 200 && statusCode < 300;
    }

    public Boolean isError() {
        return statusCode != null && statusCode >= 400;
    }

    public Boolean isClientError() {
        return statusCode != null && statusCode >= 400 && statusCode < 500;
    }

    public Boolean isServerError() {
        return statusCode != null && statusCode >= 500;
    }

    public Boolean hasData() {
        return data != null;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", timestamp=" + timestamp +
                ", traceId='" + traceId + '\'' +
                '}';
    }
}
