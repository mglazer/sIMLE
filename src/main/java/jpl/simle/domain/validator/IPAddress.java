package jpl.simle.domain.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Constraint(validatedBy = IPAddressValidator.class)
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IPAddress {
	String message() default "is not an IP Address";
	
	class IPAddressValidator implements ConstraintValidator<IPAddress, String> 
	{
		public void initialize(IPAddress constraintAnnotation)
		{
			
		}
		
		public boolean isValid(String value, ConstraintValidatorContext context)
		{
			return value != null && value.matches("\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\b");
		}
	}
}
