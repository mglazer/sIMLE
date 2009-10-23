package jpl.simle.web;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import jpl.simle.domain.SIMLEUser.UserType;
import jpl.simle.web.editors.SIMLEUserTypeEditor;

public class SIMLEBindingInitializer implements WebBindingInitializer
{

    public void initBinder(WebDataBinder binder, WebRequest request)
    {
        binder.registerCustomEditor(UserType.class, new SIMLEUserTypeEditor());
    }

}
