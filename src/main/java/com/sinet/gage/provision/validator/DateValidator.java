package com.sinet.gage.provision.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.sinet.gage.provision.annotation.ValidDate;

/**
 * Validator validate text fields annotated with ValidDate
 * checks if the date is valid or not as per DD-MM-YYYY 
 * format
 *  
 * @author Team Gage
 *
 */
public class DateValidator implements ConstraintValidator<ValidDate, String> {

	@Override
	public void initialize(ValidDate constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if ( StringUtils.hasLength(value) ) {
			return value.matches("^(((0[13578]|1[02])-(0[1-9]|[12]\\d|3[01])-((19|[2-9]\\d)\\d{2}))|((0[13456789]|1[012])-(0[1-9]|[12]\\d|30)-((19|[2-9]\\d)\\d{2}))|(02-(0[1-9]|1\\d|2[0-8])-((19|[2-9]\\d)\\d{2}))|(02-29-((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$");
		}
		return true;
	}

}
