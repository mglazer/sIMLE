package jpl.simle.domain.validator;

import java.util.Set;

import javax.validation.BeanDescriptor;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

public class LabValidator implements Validator {

	public BeanDescriptor getConstraintsForClass(Class<?> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> Set<ConstraintViolation<T>> validate(T object,
			Class<?>... groups) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> Set<ConstraintViolation<T>> validateProperty(T object,
			String propertyName, Class<?>... groups) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType,
			String propertyName, Object value, Class<?>... groups) {
		// TODO Auto-generated method stub
		return null;
	}

}
