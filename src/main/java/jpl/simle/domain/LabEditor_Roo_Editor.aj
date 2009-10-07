package jpl.simle.domain;

import java.beans.PropertyEditorSupport;
import java.lang.Long;
import java.lang.String;
import jpl.simle.domain.Lab;
import org.springframework.beans.SimpleTypeConverter;

privileged aspect LabEditor_Roo_Editor {
    
    declare parents: LabEditor implements PropertyEditorSupport;    
    
    private SimpleTypeConverter LabEditor.typeConverter = new SimpleTypeConverter();    
    
    public String LabEditor.getAsText() {    
        Object obj = getValue();        
        if (obj == null) {        
            return null;            
        }        
        return (String) typeConverter.convertIfNecessary(((Lab) obj).getId(), String.class);        
    }    
    
    public void LabEditor.setAsText(String text) {    
        if (text == null || 0 == text.length()) {        
            setValue(null);            
            return;            
        }        
        
        Long identifier = (Long) typeConverter.convertIfNecessary(text, Long.class);        
        if (identifier == null) {        
            setValue(null);            
            return;            
        }        
        
        setValue(Lab.findLab(identifier));        
    }    
    
}
