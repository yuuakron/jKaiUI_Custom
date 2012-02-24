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
public class ArenaPM extends Message implements I_InMessage {

    /**
     * Holds value of property user.
     */
    private KaiString user;

    /**
     * Holds value of property message.
     */
    private KaiString message;
    
    /** Creates a new instance of ClientHere */
    public ArenaPM() {
    }
    
    @Override
    public Message parse(String s) {
        
        Pattern p = Pattern.compile("KAI_CLIENT_ARENA_PM;(.*);(.*);");
        Matcher m = p.matcher(s);
        if (m.matches()){
            ArenaPM msg = new ArenaPM();
            msg.setUser(new KaiString(m.group(1)));
            msg.setMessage(new KaiString(m.group(2)));
            
            
            return msg;
        }
        p = Pattern.compile("KAI_CLIENT_ARENA_PM;(.*)");
        m = p.matcher(s);
        if (m.matches()){
            ArenaPM msg = new ArenaPM();           
            
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


    
}
