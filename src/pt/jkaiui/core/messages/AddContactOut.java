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
public class AddContactOut extends Message implements I_OutMessage {
    
    /**
     * Holds value of property user.
     */
    private KaiString user;
    
    /** Creates a new instance of ClientHere */
    public AddContactOut() {
    }
    
    
    /**
     * Getter for property user.
     * @return Value of property user.
     */
    public KaiString getUser() {
        
        return this.user;
    }
    
    /**
     * Setter for property user.
     * @param user New value of property user.
     */
    public void setUser(KaiString user) {
        
        this.user = user;
    }
    
    
    @Override
    public String send() {
        
        String out = "KAI_CLIENT_ADD_CONTACT;" + getUser() + ";";
        return out;
    }
    
}
