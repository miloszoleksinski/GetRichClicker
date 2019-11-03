package com.olas.GetRichClicker.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE}) 
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserPasswordValid.class)
@Documented
public @interface PasswordMatches
{
	String message() default "Passwords doesn't match";
    Class<?>[] groups() default {}; 
    Class<? extends Payload>[] payload() default {};
}
