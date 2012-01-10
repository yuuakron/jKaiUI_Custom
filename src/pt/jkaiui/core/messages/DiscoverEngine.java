/*
 * DiscoverMessage.java
 *
 * Created on November 22, 2004, 6:58 PM
 */

package pt.jkaiui.core.messages;

import pt.jkaiui.manager.I_OutMessage;

/**
 *
 * @author  pedro
 */
public class DiscoverEngine extends Message implements I_OutMessage{
    
    /** Creates a new instance of DiscoverMessage */
    public DiscoverEngine() {
    }
    
     public String send() {
        
        return "KAI_CLIENT_DISCOVER;";
    }
}
