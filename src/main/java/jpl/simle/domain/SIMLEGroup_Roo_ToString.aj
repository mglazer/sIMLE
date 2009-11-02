package jpl.simle.domain;

privileged aspect SIMLEGroup_Roo_ToString {
    
    public java.lang.String SIMLEGroup.toString() {    
        StringBuilder sb = new StringBuilder();        
        sb.append("id: ").append(getId()).append(", ");        
        sb.append("version: ").append(getVersion()).append(", ");        
        sb.append("groupName: ").append(getGroupName());        
        return sb.toString();        
    }    
    
}
