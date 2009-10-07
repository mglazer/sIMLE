package jpl.simle.domain;

import org.springframework.beans.factory.annotation.Configurable;

privileged aspect Host_Roo_Configurable {
    
    declare @type: Host: @Configurable;    
    
}
