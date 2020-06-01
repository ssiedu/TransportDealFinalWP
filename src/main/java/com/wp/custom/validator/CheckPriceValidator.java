package com.wp.custom.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckPriceValidator implements ConstraintValidator<CheckPrice, Integer> {

	@Override
	public void initialize(CheckPrice constraintAnnotation) {
		// TODO Auto-generated method stub	
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		return value>10000 && value<1000000000;//price should be between 10k and 1billion 
	}

}
