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
public class RemoveSubVector extends Message implements I_InMessage {

    /**
     * Holds value of property vector.
     */
    private KaiString vector;
    
    /** Creates a new instance of ClientHere */
    public RemoveSubVector() {
    }
    
    public Message parse(String s) {
        
        Pattern p = Pattern.compile("KAI_CLIENT_REMOVE_SUB_VECTOR;(.*);");
        Matcher m = p.matcher(s);
        if (m.matches()){
            RemoveSubVector msg = new RemoveSubVector();
            msg.setVector(new KaiString(m.group(1)));
            
            return msg;
        }
        
        return null;
    }

    /**
     * Getter for property vector.
     * @return Value of property vector.
     */
    public KaiString getVector()  {

        return this.vector;
    }

    /**
     * Setter for property vector.
     * @param vector New value of property vector.
     */
    public void setVector(pt.jkaiui.core.KaiString vector)  {

        this.vector = vector;
    }
    
}
