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
public class JoinsVector extends Message implements I_InMessage {

    /**
     * Holds value of property user.
     */
    private KaiString user;
    
    /** Creates a new instance of ClientHere */
    public JoinsVector() {
    }
    
    public Message parse(String s) {
        
        Pattern p = Pattern.compile("KAI_CLIENT_JOINS_VECTOR;(.*);");
        Matcher m = p.matcher(s);
        if (m.matches()){
            JoinsVector msg = new JoinsVector();
            msg.setUser(new KaiString(m.group(1)));
            
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
    
}
