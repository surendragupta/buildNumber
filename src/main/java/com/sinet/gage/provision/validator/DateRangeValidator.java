package com.sinet.gage.provision.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.sinet.gage.provision.annotation.ValidDateRange;
import com.sinet.gage.provision.model.DistrictDomainRequest;

/**
 * Validator validate text fields annotated with ValidDate checks if the date is
 * valid or not as per DD-MM-YYYY format
 * 
 * @author Team Gage
 *
 */
public class DateRangeValidator implements ConstraintValidator<ValidDateRange, Object> {

	String currentDate = "";

	@Override
	public void initialize(ValidDateRange constraintAnnotation) {
		/*
		 * String start=constraintAnnotation.start(); String
		 * end=constraintAnnotation.end(); startDate =
		 */

	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {

		/*
		 * if(start.compareTo(end)>0){ return false; }
		 */

		DistrictDomainRequest cd = (DistrictDomainRequest) value;
		try {
			if (StringUtils.hasLength(cd.getSubscriptionEndDate())) {
				Date startDate = new SimpleDateFormat("MM-dd-yyyy").parse(cd.getSubscriptionStartDate());
				Date endDate = new SimpleDateFormat("MM-dd-yyyy").parse(cd.getSubscriptionEndDate());

				if (startDate.compareTo(endDate) > 0) {
					return false;
				}
			} else {
				return true;
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
