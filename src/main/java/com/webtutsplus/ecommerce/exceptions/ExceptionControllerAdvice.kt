package com.webtutsplus.ecommerce.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice {
    @ExceptionHandler(value = [UpdateFailException::class])
    fun handleUpdateFailException(exception: UpdateFailException): ResponseEntity<String?> {
        return ResponseEntity(exception.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [AuthenticationFailException::class])
    fun handleUpdateFailException(exception: AuthenticationFailException): ResponseEntity<String?> {
        return ResponseEntity(exception.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [CustomException::class])
    fun handleUpdateFailException(exception: CustomException): ResponseEntity<String?> {
        return ResponseEntity(exception.message, HttpStatus.BAD_REQUEST)
    }
}