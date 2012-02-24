/*
 * AttachEngine.java
 *
 * Created on November 22, 2004, 7:55 PM
 */

package pt.jkaiui.core.messages;

import pt.jkaiui.manager.I_OutMessage;

/**
 *
 * @author  pedro
 */
public class Takeover extends Message implements I_OutMessage{
    
    /** Creates a new instance of AttachEngine */
    public Takeover() {
    }
    
    
    @Override
    public String send() {
        
        return "KAI_CLIENT_TAKEOVER";
    }
    
}
