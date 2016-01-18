package com.sinet.gage.provision.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.sinet.gage.provision.validator.DateValidator;

/**
 * Annotation to validate date fields
 * usage 
 * @ValidDate(message="error.pilot.endate.invalid")
 * 
 * @author Team Gage
 *
 */
@Documented
@Constraint(validatedBy = DateValidator.class)
@Target(value = { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDate {
	
	String message() default "{error.date.notvalid}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
