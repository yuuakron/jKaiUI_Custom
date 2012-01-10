/*
 * AttachEngine.java
 *
 * Created on November 22, 2004, 7:55 PM
 */

package pt.jkaiui.core.messages;

import pt.jkaiui.manager.I_InMessage;

/**
 *
 * @author  pedro
 */
public class DetachEngine extends Message implements I_InMessage {
    
    /** Creates a new instance of AttachEngine */
    public DetachEngine() {
    }
    
    public Message parse(String s) {
        
        if (s.equals("KAI_CLIENT_DETACH;"))
            return new DetachEngine();
        
        return null;
    }
}
