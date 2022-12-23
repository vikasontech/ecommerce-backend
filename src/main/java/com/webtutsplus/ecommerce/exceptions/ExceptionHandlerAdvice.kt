package com.webtutsplus.ecommerce.exceptions

import com.webtutsplus.ecommerce.common.ApiResponse
import org.springframework.core.NestedExceptionUtils
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@ControllerAdvice
class ExceptionHandlerAdvice {
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun conflict(ex: DataIntegrityViolationException): ResponseEntity<ApiResponse> {
        val message = getMostSpecificMessage(ex)
        return ResponseEntity(ApiResponse(false, message), HttpStatus.CONFLICT)
    }

    //	@ExceptionHandler(AccessDeniedException.class)
    //	public ResponseEntity<ApiResponse> accessDenied(AccessDeniedException ex){
    //		String message = ex.getMessage();
    //		
    //		return new ResponseEntity<ApiResponse>(new ApiResponse(false, message), HttpStatus.FORBIDDEN);
    //	}
    @ExceptionHandler(ValidationException::class)
    fun validationException(ex: ValidationException): ResponseEntity<ApiResponse> {
        val message = ex.message
        return ResponseEntity(ApiResponse(false, message), HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun methodArgumentTypeMismatchException(ex: MethodArgumentTypeMismatchException): ResponseEntity<ApiResponse> {
        ex.printStackTrace()
        val message = ex.message
        return ResponseEntity(ApiResponse(false, message), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(Exception::class)
    fun unhandledExceptions(ex: Exception): ResponseEntity<ApiResponse> {
        val message = NestedExceptionUtils.getMostSpecificCause(ex).message
        ex.printStackTrace()
        return ResponseEntity(ApiResponse(false, message), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    private fun getMostSpecificMessage(ex: DataIntegrityViolationException): String? {
        var message = NestedExceptionUtils.getMostSpecificCause(ex).message
        if (message!!.contains("Detail:")) {
            message = message.substring(message.indexOf("Detail:") + "Detail:".length)
        }
        return message
    }
}