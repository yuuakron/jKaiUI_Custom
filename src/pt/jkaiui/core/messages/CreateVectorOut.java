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
public class CreateVectorOut extends Message implements I_OutMessage {

    /**
     * Holds value of property password.
     */
    private KaiString password;

    /**
     * Holds value of property maxPlayers.
     */
    private int maxPlayers;

    /**
     * Holds value of property description.
     */
    private KaiString description;
    
    /** Creates a new instance of ClientHere */
    public CreateVectorOut() {
    }
    
    
    public String send() {
        
        String out = "KAI_CLIENT_CREATE_VECTOR;" + getMaxPlayers() + ";" + getDescription() + ";" + getPassword() + ";";
        return out;
    }

    /**
     * Getter for property password.
     * @return Value of property password.
     */
    public KaiString getPassword() {

        return this.password;
    }

    /**
     * Setter for property password.
     * @param password New value of property password.
     */
    public void setPassword(KaiString password) {

        this.password = password;
    }

    /**
     * Getter for property maxPlayers.
     * @return Value of property maxPlayers.
     */
    public int getMaxPlayers() {

        return this.maxPlayers;
    }

    /**
     * Setter for property maxPlayers.
     * @param maxPlayers New value of property maxPlayers.
     */
    public void setMaxPlayers(int maxPlayers) {

        this.maxPlayers = maxPlayers;
    }

    /**
     * Getter for property description.
     * @return Value of property description.
     */
    public KaiString getDescription() {

        return this.description;
    }

    /**
     * Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(KaiString description) {

        this.description = description;
    }
    
}
