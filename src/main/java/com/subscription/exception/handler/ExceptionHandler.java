package com.subscription.exception.handler;


import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.razorpay.RazorpayException;
import com.subscription.logger.LoggerConfiguration;
import java.text.ParseException;



@ControllerAdvice
public class ExceptionHandler {
	private final logger logger = LoggerConfiguration.
	
	@org.springframework.web.bind.annotation.ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	private ResponseEntity<Object> HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex){
		 ex.printStackTrace();
		return new ResponseEntity<Object>("Please change http method type", HttpStatus.NOT_FOUND);
	}
	
	
	@org.springframework.web.bind.annotation.ExceptionHandler(IndexOutOfBoundsException.class)
	private ResponseEntity<String>handleIndexOfBoundException(IndexOutOfBoundsException e){
		
		return new ResponseEntity<String>("Payment server down. please try again sometime :)", HttpStatus.BAD_REQUEST);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(RazorpayException.class)
	private ResponseEntity<String>handleRazorpayException(RazorpayException r){
		r.printStackTrace();
		return new ResponseEntity<String>("Payment server down. please try again sometime :)", HttpStatus.BAD_REQUEST);
	}
	
	
	@org.springframework.web.bind.annotation.ExceptionHandler(NullPointerException.class)
	private ResponseEntity<String>handleNullPointerException(NullPointerException n){
		return new ResponseEntity<String>("Data Accured null", HttpStatus.NOT_FOUND);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(ParseException.class)
    public ResponseEntity<String> handleParseException(ParseException e) {
        return new ResponseEntity<>("Payment server down. please try again sometime :)", HttpStatus.BAD_REQUEST);
    }
	
	
}
