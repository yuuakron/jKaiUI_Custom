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
public class ChatMode extends Message implements I_InMessage {

    /**
     * Holds value of property room.
     */
    private KaiString room;
    
    /** Creates a new instance of ClientHere */
    public ChatMode() {
    }
    
    public Message parse(String s) {
        
        Pattern p = Pattern.compile("(?s)KAI_CLIENT_CHATMODE;(.*);");
        Matcher m = p.matcher(s);
        if (m.matches()){
            ChatMode msg = new ChatMode();
            msg.setRoom(new KaiString(m.group(1)));
            
            return msg;
        }
        
        return null;
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



    
}
