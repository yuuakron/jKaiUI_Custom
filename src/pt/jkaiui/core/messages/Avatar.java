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
public class Avatar extends Message implements I_InMessage {

    /**
     * Holds value of property user.
     */
    private KaiString user;

    /**
     * Holds value of property url.
     */
    private KaiString url;
    
    /** Creates a new instance of ClientHere */
    public Avatar() {
    }
    
    public Message parse(String s) {
        
        Pattern p = Pattern.compile("KAI_CLIENT_AVATAR;(.*);(.*);");
        Matcher m = p.matcher(s);
        if (m.matches()){
            Avatar msg = new Avatar();
            msg.setUser(new KaiString(m.group(1)));
            msg.setUrl(new KaiString(m.group(2)));
            
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
     * Getter for property url.
     * @return Value of property url.
     */
    public KaiString getUrl() {

        return this.url;
    }

    /**
     * Setter for property url.
     * @param url New value of property url.
     */
    public void setUrl(KaiString url) {

        this.url = url;
    }


    
}
