package jpl.simle.marshallers;

import java.util.List;
import java.util.Collections;


public class XStreamMarshaller extends
		org.springframework.oxm.xstream.XStreamMarshaller {

	private final static Class[] SUPPORTED_CLASSES = {
	};
	
	private List<Class> annotatedClasses_;
	
	public boolean supports(Class clazz) 
	{
		return getAnnotatedClasses().contains(clazz);
	}
	
	
	
	public List<Class> getAnnotatedClasses() {
		return annotatedClasses_;
	}



	public void setAnnotatedClasses(List<Class> annotatedClasses) {
		annotatedClasses_ = annotatedClasses;
	}



	protected void customizeXStream(com.thoughtworks.xstream.XStream xstream)
	{
		System.out.println("************************** PROCESSING ANNOTATIONS ******************************");
		xstream.processAnnotations(annotatedClasses_.toArray(new Class[0]));
	}

}
