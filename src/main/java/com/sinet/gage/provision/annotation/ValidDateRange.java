package com.sinet.gage.provision.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.sinet.gage.provision.validator.DateRangeValidator;

/**
 * Annotation to validate comparing two date fields
 * creating a valid date range
 * usage 
 * @ValidDateRange(message = "error.date.compare")
 * 
 * @author Team Gage
 *
 */
@Documented
@Constraint(validatedBy = DateRangeValidator.class)
@Target(value = { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateRange {
	
	String message() default "{error.date.compare}";
	
	/*String start();
	String end();*/

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
