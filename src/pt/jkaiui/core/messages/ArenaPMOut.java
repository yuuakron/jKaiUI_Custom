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
public class ArenaPMOut extends Message implements I_OutMessage {
    
    /**
     * Holds value of property user.
     */
    private KaiString user;
    
    /**
     * Holds value of property message.
     */
    private KaiString message;
    
    /** Creates a new instance of ClientHere */
    public ArenaPMOut() {
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
    
    /**
     * Getter for property message.
     * @return Value of property message.
     */
    public KaiString getMessage() {
        
        return this.message;
    }
    
    /**
     * Setter for property message.
     * @param message New value of property message.
     */
    public void setMessage(KaiString message) {
        
        this.message = message;
    }
    
    
    public String send() {
        
        String out = "KAI_CLIENT_ARENA_PM;" + getUser() + ";"+ getMessage() +";";
        return out;
    }
    
}
