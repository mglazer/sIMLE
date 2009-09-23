package jpl.simle.web.resolvers;

import java.util.Locale;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

public class StaticViewResolver implements ViewResolver, Ordered 
{
	private int order_ = Integer.MAX_VALUE;
	private View view_;
	
	public View resolveViewName(String viewName, Locale locale)
			throws Exception 
	{
		return view_;
	}

	public int getOrder() 
	{
		return order_;
	}

	public void setView(View view)
	{
		view_ = view;
	}
	
	public void setOrder(int order)
	{
		order_ = order;
	}
}
