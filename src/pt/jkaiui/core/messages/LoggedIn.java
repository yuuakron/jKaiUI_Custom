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
public class LoggedIn extends Message implements I_InMessage {
    
    /** Creates a new instance of ClientHere */
    public LoggedIn() {
    }

    public Message parse(String s) {
        
        if (s.equals("KAI_CLIENT_LOGGED_IN;"))
            return new LoggedIn();
        
        return null;
    }
    
}
