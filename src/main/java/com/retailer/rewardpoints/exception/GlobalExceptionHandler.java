package com.retailer.rewardpoints.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.retailer.rewardpoints.dto.ResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(RewardPointsException.class)
	public ResponseEntity<ResponseDto> handleRewardPointsException(RewardPointsException ex) {
	    ResponseDto response = new ResponseDto();
	    response.setStatus("failed");
	    response.setError(ex.getMessage());
	    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    
	}
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseDto> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        ResponseDto response = new ResponseDto();
        response.setStatus("failed");
        response.setError("Invalid parameter: " + ex.getName());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> handleGeneralException(Exception ex) {
        ResponseDto response = new ResponseDto();
        response.setStatus("failed");
        response.setError("An unexpected error occurred: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
