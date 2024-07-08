package com.subscription.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.subscription.EvalvuePaymentServiceApiApplication;
import com.subscription.controller.SubscriptionController;
import com.subscription.service.SubscriptionImpl;
import com.subscription.service.SubscriptionService;

public class LoggerConfiguration {
	private static final Logger Mainlogger = LoggerFactory.getLogger(EvalvuePaymentServiceApiApplication.class);
	private static final Logger subscriptionImplLogger = LoggerFactory.getLogger(SubscriptionImpl.class);
	private static final Logger subscriptionServiceLogger = LoggerFactory.getLogger(SubscriptionService.class);
	private static final Logger subscriptionControllerLogger = LoggerFactory.getLogger(SubscriptionController.class);
	private static final Logger subscriptionLogger = LoggerFactory.getLogger(SubscriptionController.class);

	
	
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


	

}
