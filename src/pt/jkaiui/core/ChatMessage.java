/*
 * ChatMessage.java
 *
 * Created on November 27, 2004, 8:54 PM
 */

package pt.jkaiui.core;

/**
 *
 * @author  pedro
 */
public class ChatMessage {
    
    public static int PUBLIC_MESSAGE = 1;
    public static int PRIVATE_MESSAGE = 2;
    

    /**
     * Holds value of property user.
     */
    private User user;

    /**
     * Holds value of property message.
     */
    private String message;

    /**
     * Holds value of property type.
     */
    private int type;
    
    /** Creates a new instance of ChatMessage */
    public ChatMessage() {
    }

    /**
     * Getter for property user.
     * @return Value of property user.
     */
    public User getUser() {

        return this.user;
    }

    /**
     * Setter for property user.
     * @param user New value of property user.
     */
    public void setUser(User user) {

        this.user = user;
    }

    /**
     * Getter for property message.
     * @return Value of property message.
     */
    public String getMessage() {

        return this.message;
    }

    /**
     * Setter for property message.
     * @param message New value of property message.
     */
    public void setMessage(String message) {

        this.message = message;
    }

    /**
     * Getter for property type.
     * @return Value of property type.
     */
    public int getType() {

        return this.type;
    }

    /**
     * Setter for property type.
     * @param type New value of property type.
     */
    public void setType(int type) {

        this.type = type;
    }
    
}
