/*
 * ClientHere.java
 *
 * Created on November 22, 2004, 7:17 PM
 */

package pt.jkaiui.core.messages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pt.jkaiui.core.KaiString;
import pt.jkaiui.manager.I_InMessage;

/**
 *
 * @author  pedro
 */
public class ClientNotLoggedIn extends Message implements I_InMessage {

    /**
     * Holds value of property user.
     */
    private KaiString user;

    /**
     * Holds value of property password.
     */
    private KaiString password;

    /**
     * Holds value of property auto.
     */
    private boolean auto;
    
    /** Creates a new instance of ClientHere */
    public ClientNotLoggedIn() {
    }
    
    public Message parse(String s) {
        
        Pattern p = Pattern.compile("KAI_CLIENT_NOT_LOGGED_IN;(.*);(.*);(.);");
        Matcher m = p.matcher(s);
        if (m.matches()){
            ClientNotLoggedIn msg = new ClientNotLoggedIn();
            msg.setUser(new KaiString(m.group(1)));
            msg.setPassword(new KaiString(m.group(2)));
            msg.setAuto(m.group(3).equals("1")?true:false);
            
            return msg;
        }
        
        return null;
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
     * Getter for property auto.
     * @return Value of property auto.
     */
    public boolean isAuto() {

        return this.auto;
    }

    /**
     * Setter for property auto.
     * @param auto New value of property auto.
     */
    public void setAuto(boolean auto) {

        this.auto = auto;
    }
    
}
