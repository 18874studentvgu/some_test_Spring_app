package com.example.demo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ AppException.class })  // Có thể bắt nhiều loại exception
    public ResponseEntity<String> handleAppException(AppException e) {
        return ResponseEntity.status(e.getCode()).body(e.getMessage());
    }
    
    // Có thêm các @ExceptionHandler khác...
    
    // Nên bắt cả Exception.class
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnwantedException(Exception e) {
        // Log lỗi ra và ẩn đi message thực sự (xem phần 3.2)
        // https://viblo.asia/p/xu-ly-exception-phat-sinh-trong-ung-dung-spring-boot-6J3ZgWkLZmB
        e.printStackTrace();  // Thực tế người ta dùng logger
        return ResponseEntity.status(500).body("Unknow error");
    }
}