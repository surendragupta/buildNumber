package com.sinet.gage.provision.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sinet.gage.provision.model.Message;
import com.sinet.gage.provision.model.MessageType;

/**
 * Exception controller advice to map exceptions to 
 * messages.
 * 
 * @author Team Gage
 *
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

	@Autowired
	private MessageSource msgSource;

	/**
	 * Maps MethodArgumentNotValidException to Message
	 * also sets Http Status to 400 Bad Request
	 * 
	 * @param ex
	 *        MethodArgumentNotValidException 
	 * @return
	 *        Message to the client
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Message processValidationError(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		FieldError error = result.getFieldError();		
		Locale currentLocale = LocaleContextHolder.getLocale();
		if (error == null) {
			String msg = msgSource.getMessage("error.date.compare", null, currentLocale);
			return processFieldError(msg);
		}
		String msg = msgSource.getMessage(error.getDefaultMessage(), null, currentLocale);
		return processFieldError(msg);
	}

	/**
	 * Maps HttpMessageNotReadableException to Message
	 * also sets Http Status to 400 Bad Request
	 * 
	 * @param ex
	 *        HttpMessageNotReadableException
	 * @return
	 *        Message to the client
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Message exception(HttpMessageNotReadableException ex) {
		return processFieldError(ex.getMessage());
	}

	/**
	 * Maps all uncatched Exception to Message
	 * also sets Http Status to 500 Internal Server Error
	 * 
	 * @param ex
	 *         Exception 
	 * @return
	 *        Message to the client
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public Message processAllexception(Exception ex) {
		return processFieldError(ex.getMessage());
	}

	private Message processFieldError(String error) {
		return new Message(MessageType.ERROR, error);
	}

	/**
	 * Setter for MessageSource
	 * 
	 * @param messageSource
	 *                     Injected MessageSource
	 */
	public void setMessageSource(MessageSource messageSource) {
		this.msgSource = messageSource;
	}
}
