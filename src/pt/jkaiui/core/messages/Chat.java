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
public class Chat extends Message implements I_InMessage {

    /**
     * Holds value of property user.
     */
    private KaiString user;

    /**
     * Holds value of property room.
     */
    private KaiString room;

    /**
     * Holds value of property message.
     */
    private KaiString message;
    
    /** Creates a new instance of ClientHere */
    public Chat() {
    }
    
    public Message parse(String s) {
        
        Pattern p = Pattern.compile("(?s)KAI_CLIENT_CHAT;(.*);(.*);(.*);");
        Matcher m = p.matcher(s);
        if (m.matches()){
            Chat msg = new Chat();
            msg.setRoom(new KaiString(m.group(1)));
            msg.setUser(new KaiString(m.group(2)));
            msg.setMessage(new KaiString(m.group(3)));
            
            return msg;
        }
        p = Pattern.compile("(?s)KAI_CLIENT_CHAT;(.*);(.*);(.*)");
        m = p.matcher(s);
        if (m.matches()){
            Chat msg = new Chat();
            msg.setRoom(new KaiString(m.group(1)));
            msg.setUser(new KaiString(m.group(2)));
            msg.setMessage(new KaiString(m.group(3)));
            
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
     * Getter for property room.
     * @return Value of property room.
     */
    public KaiString getRoom() {

        return this.room;
    }

    /**
     * Setter for property room.
     * @param room New value of property room.
     */
    public void setRoom(KaiString room) {

        this.room = room;
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
