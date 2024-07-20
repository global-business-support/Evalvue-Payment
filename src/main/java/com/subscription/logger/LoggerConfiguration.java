package com.subscription.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.subscription.exception.handler.GlobalExceptionHandler;
import com.subscription.service.SubscriptionImpl;
import com.subscription.service.SubscriptionService;
import com.subscription.EvalvuePaymentServiceApiApplication;
import com.subscription.controller.SubscriptionController;

public class LoggerConfiguration {
	private static final Logger Mainlogger = LoggerFactory.getLogger(EvalvuePaymentServiceApiApplication.class);
	private static final Logger subscriptionImplLogger = LoggerFactory.getLogger(SubscriptionImpl.class);
	private static final Logger subscriptionServiceLogger = LoggerFactory.getLogger(SubscriptionService.class);
	private static final Logger subscriptionControllerLogger = LoggerFactory.getLogger(SubscriptionController.class);
	private static final Logger exceptionHandler = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	
	
	public static Logger getMainlogger() {
		return Mainlogger;
	}
	public static Logger getSubscriptionImplLogger() {
		return subscriptionImplLogger;
	}
	public static Logger getSubscriptionservicelogger() {
		return subscriptionServiceLogger;
	}
	public static Logger getSubscriptioncontrollerlogger() {
		return subscriptionControllerLogger;
	}
	public static Logger getSubscriptionimpllogger() {
		return subscriptionImplLogger;
	}
	public static Logger getGlobalExceptionhandler() {
		return exceptionHandler;
	}
}
