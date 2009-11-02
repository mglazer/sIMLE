package jpl.simle.domain;

privileged aspect ApplicationEditor_Roo_Editor {
    
    declare parents: ApplicationEditor implements java.beans.PropertyEditorSupport;    
    
    org.springframework.beans.SimpleTypeConverter ApplicationEditor.typeConverter = new org.springframework.beans.SimpleTypeConverter();    
    
    public java.lang.String ApplicationEditor.getAsText() {    
        Object obj = getValue();        
        if (obj == null) {        
            return null;            
        }        
        return (String) typeConverter.convertIfNecessary(((jpl.simle.domain.Application) obj).getId() , String.class);        
    }    
    
    public void ApplicationEditor.setAsText(java.lang.String text) {    
        if (text == null || 0 == text.length()) {        
            setValue(null);            
            return;            
        }        
        
        java.lang.Long identifier = (java.lang.Long) typeConverter.convertIfNecessary(text, java.lang.Long.class);        
        if (identifier == null) {        
            setValue(null);            
            return;            
        }        
        
        setValue(jpl.simle.domain.Application.findApplication(identifier));        
    }    
    
}
