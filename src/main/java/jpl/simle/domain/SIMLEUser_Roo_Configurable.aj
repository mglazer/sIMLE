package jpl.simle.domain;

import org.springframework.beans.factory.annotation.Configurable;

privileged aspect SIMLEUser_Roo_Configurable {
    
    declare @type: SIMLEUser: @Configurable;    
    
}
