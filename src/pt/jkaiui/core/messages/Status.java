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
public class Status extends Message implements I_InMessage {

    /**
     * Holds value of property message.
     */
    private KaiString message;
    
    public Status() {
    }
    
    public Message parse(String s) {
        
        Pattern p = Pattern.compile("KAI_CLIENT_STATUS;(.*);");
        Matcher m = p.matcher(s);
        if (m.matches()){
            Status msg = new Status();
            msg.setMessage(new KaiString(m.group(1)));
            
            return msg;
        }
        
        return null;
    }

    /**
     * Getter for property message.
     * @return Value of property message.
     */
    public KaiString getMessage()  {

        return this.message;
    }

    /**
     * Setter for property message.
     * @param message New value of property message.
     */
    public void setMessage(pt.jkaiui.core.KaiString message)  {

        this.message = message;
    }


    
}
