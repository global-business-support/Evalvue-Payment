package com.subscription.exception.handler;


import org.hibernate.exception.SQLGrammarException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.razorpay.RazorpayException;
import com.subscription.logger.LoggerConfiguration;

import java.io.IOException;
import java.text.ParseException;



@ControllerAdvice
public class GlobalExceptionHandler {
	
	Logger logger = LoggerConfiguration.getGlobalExceptionhandler();
	JSONObject error = new JSONObject();
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	private ResponseEntity<Object> HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
	        logger.error(e.getMessage(),e.getCause());
			e.printStackTrace();
		return new ResponseEntity<Object>("Please change http method type", HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(IndexOutOfBoundsException.class)
	private ResponseEntity<String>handleIndexOfBoundException(IndexOutOfBoundsException e){
		logger.error(e.getMessage(),e.getCause());
		e.printStackTrace();
		error.put("payment_error", "Payment server down. please try again sometime :)");
		return new ResponseEntity<String>(error.toString(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(RazorpayException.class)
	private ResponseEntity<String>handleRazorpayException(RazorpayException e){
		logger.error(e.getMessage(),e.getCause());
		e.printStackTrace();
		error.put("payment_error", "Payment server down. please try again sometime :)");
		return new ResponseEntity<String>(error.toString(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NullPointerException.class)
	private ResponseEntity<String>handleNullPointerException(NullPointerException e){
		logger.error(e.getMessage(),e.getCause());
		e.printStackTrace();
		error.put("payment_error", "Payment server down. please try again sometime :)");
		return new ResponseEntity<String>(error.toString(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ParseException.class)
    public ResponseEntity<String> handleParseException(ParseException e) {
		logger.error(e.getMessage(),e.getCause());
		e.printStackTrace();
		error.put("payment_error", "Payment server down. please try again sometime :)");
        return new ResponseEntity<>(error.toString(), HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(DataIntegrityViolationException.class)
     public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException e){ 
		logger.error(e.getMessage(),e.getCause());
		e.printStackTrace();
     return new  ResponseEntity<String>(error.toString(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidDataAccessResourceUsageException.class)
	public ResponseEntity<String> handleInvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException e){
		logger.error(e.getMessage(),e.getCause());
		e.printStackTrace();
		error.put("payment_error", "Payment server down. please try again sometime :)");
		 return new  ResponseEntity<String>(error.toString(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SQLGrammarException.class)
	public ResponseEntity<String>handleSQLGrammarException(SQLGrammarException e){
		logger.error(e.getMessage(),e.getCause());
		e.printStackTrace();
		error.put("payment_error", "Payment server down. please try again sometime :)");
		return new  ResponseEntity<String>(error.toString(), HttpStatus.BAD_REQUEST);

	}
	@ExceptionHandler(SQLServerException.class)
	public ResponseEntity<String>handleSQLServerExceptionn(SQLServerException e){
		logger.error(e.getMessage(),e.getCause());
		e.printStackTrace();
		error.put("payment_error", "Payment server down. please try again sometime :)");
		 return new  ResponseEntity<String>(error.toString(), HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(JsonProcessingException.class)
	public ResponseEntity<String> handleJsonProcessingException(JsonProcessingException e){
		logger.error(e.getMessage(),e.getCause());
		e.printStackTrace();
		error.put("payment_error", "JsonProcessingException e");
		return new ResponseEntity<String>(error.toString(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e){
		logger.error(e.getMessage(),e.getCause());
		e.printStackTrace();
		error.put("payment_error", "IllegalArgumentException e");
		return new ResponseEntity<String>(error.toString(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IOException.class)
	public ResponseEntity<String> handleIOException(IOException e){
		logger.error(e.getMessage(),e.getCause());
		e.printStackTrace();
		error.put("payment_error",""+e.getMessage());
		return new ResponseEntity<String>(error.toString(), HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e){	
		logger.error(e.getMessage(),e.getCause());
		e.printStackTrace();
		error.put("payment_error", "Payment server down. please try again sometime :)");
		return new ResponseEntity<String>(error.toString(), HttpStatus.BAD_REQUEST);
	}
}
