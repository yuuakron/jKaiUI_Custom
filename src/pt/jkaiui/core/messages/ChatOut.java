/*
 * ClientHere.java
 *
 * Created on November 22, 2004, 7:17 PM
 */

package pt.jkaiui.core.messages;

import pt.jkaiui.core.KaiString;

import pt.jkaiui.manager.I_OutMessage;

/**
 *
 * @author  pedro
 */
public class ChatOut extends Message implements I_OutMessage {
    
    /**
     * Holds value of property message.
     */
    private KaiString message;
    
    /** Creates a new instance of ClientHere */
    public ChatOut() {
    }
    
    
    /**
     * Getter for property message.
     * @return Value of property message.
     */
    public KaiString getMessage()  {

        return this.message;
    }
    
    /**
     * Setter for property message.
     * @param message New value of property message.
     */
    public void setMessage(pt.jkaiui.core.KaiString message)  {

        this.message = message;
    }
    
    
    public String send() {
        
        String out = "KAI_CLIENT_CHAT;" + getMessage() + ";";
        return out;
    }
    
}
