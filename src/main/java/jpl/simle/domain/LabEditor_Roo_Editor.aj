package jpl.simle.domain;

privileged aspect LabEditor_Roo_Editor {
    
    declare parents: LabEditor implements java.beans.PropertyEditorSupport;    
    
    org.springframework.beans.SimpleTypeConverter LabEditor.typeConverter = new org.springframework.beans.SimpleTypeConverter();    
    
    public java.lang.String LabEditor.getAsText() {    
        Object obj = getValue();        
        if (obj == null) {        
            return null;            
        }        
        return (String) typeConverter.convertIfNecessary(((jpl.simle.domain.Lab) obj).getId() , String.class);        
    }    
    
    public void LabEditor.setAsText(java.lang.String text) {    
        if (text == null || 0 == text.length()) {        
            setValue(null);            
            return;            
        }        
        
        java.lang.Long identifier = (java.lang.Long) typeConverter.convertIfNecessary(text, java.lang.Long.class);        
        if (identifier == null) {        
            setValue(null);            
            return;            
        }        
        
        setValue(jpl.simle.domain.Lab.findLab(identifier));        
    }    
    
}
