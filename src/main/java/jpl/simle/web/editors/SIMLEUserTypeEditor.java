package jpl.simle.web.editors;

import java.beans.PropertyEditorSupport;

import jpl.simle.domain.SIMLEUser;
import jpl.simle.domain.SIMLEUser.UserType;

import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class SIMLEUserTypeEditor extends PropertyEditorSupport
{

    @Override
    public void setAsText(String text) throws IllegalArgumentException
    {
        for ( UserType type : SIMLEUser.UserType.values() )
        {
            if ( type.name().equalsIgnoreCase(text) )
            {
                setValue(type);
            }
        }
    }
}
