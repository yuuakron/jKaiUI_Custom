/*
 * ClientHere.java
 *
 * Created on November 22, 2004, 7:17 PM
 */

package pt.jkaiui.core.messages;

import pt.jkaiui.manager.I_InMessage;

/**
 *
 * @author  pedro
 */
public class ConnectedMessenger extends Message implements I_InMessage {
    
    /** Creates a new instance of ClientHere */
    public ConnectedMessenger() {
    }

    public Message parse(String s) {
        
        if (s.equals("KAI_CLIENT_CONNECTED_MESSENGER;"))
            return new ConnectedMessenger();
        
        return null;
    }
    
}
