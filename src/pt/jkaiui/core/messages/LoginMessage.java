/*
 * LoginMessage.java
 *
 * Created on November 22, 2004, 6:51 PM
 */

package pt.jkaiui.core.messages;

import pt.jkaiui.core.KaiString;
import pt.jkaiui.manager.I_OutMessage;

/**
 *
 * @author  pedro
 */
public class LoginMessage extends Message implements I_OutMessage {
    
    /** Creates a new instance of LoginMessage */
    public LoginMessage(){
    }
    /**
     * Holds value of property login.
     */
    private KaiString login;

    /**
     * Holds value of property password.
     */
    private KaiString password;
    /**
     * Getter for property login.
     * @return Value of property login.
     */
    public KaiString getLogin() {

        return this.login;
    }

    /**
     * Setter for property login.
     * @param login New value of property login.
     */
    public void setLogin(KaiString login) {

        this.login = login;
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

    public String send() {
        
        String out = "KAI_CLIENT_LOGIN;" + getLogin() +";" + getPassword()+";";
        return out;
    }
 
    
    
}
