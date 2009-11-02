package jpl.simle.domain;

privileged aspect GroupMember_Roo_ToString {
    
    public java.lang.String GroupMember.toString() {    
        StringBuilder sb = new StringBuilder();        
        sb.append("id: ").append(getId()).append(", ");        
        sb.append("version: ").append(getVersion()).append(", ");        
        sb.append("group: ").append(getGroup()).append(", ");        
        sb.append("user: ").append(getUser());        
        return sb.toString();        
    }    
    
}
