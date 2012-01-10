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
public class ArenaStatusOut extends Message implements I_OutMessage {

    /**
     * Holds value of property status.
     */
    private int status;

    /**
     * Holds value of property players.
     */
    private int players;
    
    /** Creates a new instance of ClientHere */
    public ArenaStatusOut() {
    }
    
    
    public String send() {
        
        String out = "KAI_CLIENT_ARENA_STATUS;" + getStatus() + ";" + getPlayers() + ";";
        return out;
    }

    /**
     * Getter for property status.
     * @return Value of property status.
     */
    public int getStatus() {

        return this.status;
    }

    /**
     * Setter for property status.
     * @param status New value of property status.
     */
    public void setStatus(int status) {

        this.status = status;
    }

    /**
     * Getter for property players.
     * @return Value of property players.
     */
    public int getPlayers() {

        return this.players;
    }

    /**
     * Setter for property players.
     * @param players New value of property players.
     */
    public void setPlayers(int players) {

        this.players = players;
    }
    
}
