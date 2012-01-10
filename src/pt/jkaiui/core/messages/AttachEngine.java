/*
 * AttachEngine.java
 *
 * Created on November 22, 2004, 7:55 PM
 */

package pt.jkaiui.core.messages;

import pt.jkaiui.manager.I_InMessage;
import pt.jkaiui.manager.I_OutMessage;

/**
 *
 * @author  pedro
 */
public class AttachEngine extends Message implements I_OutMessage, I_InMessage{
    
    /** Creates a new instance of AttachEngine */
    public AttachEngine() {
    }
    
    
    public String send() {
        
        return "KAI_CLIENT_ATTACH;";
    }
    
    
    public Message parse(String s) {
        
        if (s.equals("KAI_CLIENT_ATTACH;"))
            return new AttachEngine();
        
        return null;
    }
}
