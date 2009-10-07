package jpl.simle.domain;

import java.beans.PropertyEditorSupport;
import java.lang.Long;
import java.lang.String;
import jpl.simle.domain.Application;
import org.springframework.beans.SimpleTypeConverter;

privileged aspect ApplicationEditor_Roo_Editor {
    
    declare parents: ApplicationEditor implements PropertyEditorSupport;    
    
    private SimpleTypeConverter ApplicationEditor.typeConverter = new SimpleTypeConverter();    
    
    public String ApplicationEditor.getAsText() {    
        Object obj = getValue();        
        if (obj == null) {        
            return null;            
        }        
        return (String) typeConverter.convertIfNecessary(((Application) obj).getId(), String.class);        
    }    
    
    public void ApplicationEditor.setAsText(String text) {    
        if (text == null || 0 == text.length()) {        
            setValue(null);            
            return;            
        }        
        
        Long identifier = (Long) typeConverter.convertIfNecessary(text, Long.class);        
        if (identifier == null) {        
            setValue(null);            
            return;            
        }        
        
        setValue(Application.findApplication(identifier));        
    }    
    
}
