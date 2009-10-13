package jpl.simle.domain.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.util.regex.Pattern;

import javax.validation.Constraint;
import javax.validation.ConstraintPayload;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Constraint(validatedBy = IPAddressValidator.class)
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IPAddress {
	String message() default "is not an IP Address";
	Class<?>[] groups() default {};
	Class<? extends ConstraintPayload>[] payload() default {};
	
	class IPAddressValidator implements ConstraintValidator<IPAddress, String> 
	{
	    // just google for IP address validation in Java and you'll come up with something akin to this pattern
	    private final static Pattern IP_PATTERN = Pattern.compile("\\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b");
		public void initialize(IPAddress constraintAnnotation)
		{
			
		}
		
		public boolean isValid(String value, ConstraintValidatorContext context)
		{
			return value != null && IP_PATTERN.matcher(value).matches();
		}
	}
}
