package com.wp.custom.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = CheckPriceValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPrice {
    String message() default "price must lie between(10k to 1billion)";//this msg is overridden by explicitly 
   //msg(Invalid Number) written in update.jsp
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
