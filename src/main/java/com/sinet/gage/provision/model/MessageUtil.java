package com.sinet.gage.provision.model;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Team Gage
 *
 */
@Component
public class MessageUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageUtil.class);
	@Autowired
	MessageSource messageSource;
	
	/**
	 * 
	 * @param code
	 * @param args
	 * @return
	 */
	public Message createSuccessMessage(String code, Object[] args) {
		return new Message(MessageType.SUCCESS, getMessage(code, args));
	}
	
	/**
	 * 
	 * @param code
	 * @param args
	 * @return
	 */
	public Message createErrorMessage(String code, Object[] args) {
		return new Message(MessageType.ERROR, getMessage(code, args));
	}

	/**
	 * 
	 * @param code
	 * @param success
	 * @param args
	 * @return
	 */
	public Message createMessage(String code, boolean success,Object ... args) {
		return success?createSuccessMessage(code, args):createErrorMessage(code, args);
	}
	
	/**
	 * 
	 * @param code
	 * @param listOfData
	 * @param nameReplaceMap
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> Message createMessageWithPayload(String code, List<T> listOfData, Map<String,String> nameReplaceMap, Class<T> clazz) {
		T[] dataArray = (T[])Array.newInstance(clazz, listOfData.size());
		DataArray<T> data= new DataArray<T>(listOfData.toArray(dataArray));
		
		Message message = new Message( MessageType.SUCCESS, getMessage(code), data);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.setPropertyNamingStrategy(new ReplaceNamingStrategy(nameReplaceMap));
		JavaType type = mapper.getTypeFactory().constructParametricType(Message.class, clazz);
		try {
			message = mapper.readValue(mapper.writeValueAsString(message), type);
		} catch (IOException e) {
			LOGGER.error("Error creating response message with payload "  + e.getMessage());
		}		
		return message;
	}
	
	/**
	 * 
	 * @param code
	 * @param firstField
	 * @param nameReplaceMap
	 * @param clazz
	 * @return
	 */
	public <T> Message createMessageWithPayload(String code, T firstField, Map<String,String> nameReplaceMap, Class<T> clazz) {
		
		Data<T> data= new Data<T>(firstField);		
		Message message = new Message( MessageType.SUCCESS, getMessage(code), data);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.setPropertyNamingStrategy(new ReplaceNamingStrategy(nameReplaceMap));
		JavaType type = mapper.getTypeFactory().constructParametricType(Message.class, clazz);
		try {
			message = mapper.readValue(mapper.writeValueAsString(message), type);
		} catch (IOException e) {
			LOGGER.error("Error creating response message with payload "  + e.getMessage());
		}		
		return message;
	}
	
	/**
	 * 
	 * @param code
	 * @param firstField
	 * @param secondField
	 * @param nameReplaceMap
	 * @param clazz
	 * @return
	 */
	public <T,U> Message createMessageWithPayload(String code, T firstField, U secondField, String fourthField, Map<String,String> nameReplaceMap, Class<T> clazz) {
		MultipleData<Object> multipleData = new MultipleData<Object>(firstField, secondField,null,fourthField);
		Message message = new Message( MessageType.SUCCESS, getMessage(code), multipleData);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.setPropertyNamingStrategy(new ReplaceNamingStrategy(nameReplaceMap));
		JavaType type = mapper.getTypeFactory().constructParametricType(Message.class, clazz);
		try {
			message = mapper.readValue(mapper.writeValueAsString(message), type);
		} catch (IOException e) {
			LOGGER.error("Error creating response message with payload "  + e.getMessage());
		}
		return message;
	}
	
	public <T,U> Message createMessageWithPayload(String code, T firstField, U secondField, Map<String,String> nameReplaceMap, Class<T> clazz) {
				MultipleData<Object> multipleData = new MultipleData<Object>(firstField, secondField,null,null);
				Message message = new Message( MessageType.SUCCESS, getMessage(code), multipleData);
				
				ObjectMapper mapper = new ObjectMapper();
				mapper.setPropertyNamingStrategy(new ReplaceNamingStrategy(nameReplaceMap));
				JavaType type = mapper.getTypeFactory().constructParametricType(Message.class, clazz);
				try {
					message = mapper.readValue(mapper.writeValueAsString(message), type);
				} catch (IOException e) {
					LOGGER.error("Error creating response message with payload "  + e.getMessage());
				}
				return message;
			}
	/**
	 * 
	 * @param code
	 * @param firstField
	 * @param secondField
	 * @param thirdField
	 * @param fourthField
	 * @param nameReplaceMap
	 * @param clazz
	 * @return
	 */
	public <T,U,V,W> Message createMessageWithPayload(String code, T firstField, U secondField, V thirdField, W fourthField, Map<String,String> nameReplaceMap, Class<T> clazz) {
		MultipleData<Object> multipleData = new MultipleData<Object>(firstField, secondField,thirdField,fourthField);
		Message message = new Message( MessageType.SUCCESS, getMessage(code), multipleData);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.setPropertyNamingStrategy(new ReplaceNamingStrategy(nameReplaceMap));
		JavaType type = mapper.getTypeFactory().constructParametricType(Message.class, clazz);
		try {
			message = mapper.readValue(mapper.writeValueAsString(message), type);
		} catch (IOException e) {
			LOGGER.error("Error creating response message with payload "  + e.getMessage());
		}
		return message;
	}
	/**
	 * 
	 * @param code
	 * @param args
	 * @return
	 */
	public String getMessage(String code, Object ... args) {
		return messageSource.getMessage(code, args, getLocale());
	}
	
	/**
	 * 
	 * @param code
	 * @return
	 */
	public String getMessage(String code) {
		return getMessage(code,(Object []) null);
	}
	
	/**
	 * 
	 * @return
	 */
	private Locale getLocale() {
		return LocaleContextHolder.getLocale();
	}
}
