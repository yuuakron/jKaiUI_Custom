/*
 * LoginMessage.java
 *
 * Created on November 22, 2004, 6:51 PM
 */

package pt.jkaiui.core.messages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pt.jkaiui.core.KaiString;
import pt.jkaiui.manager.I_InMessage;
import pt.jkaiui.manager.I_OutMessage;

/**
 *
 * @author  pedro
 */
public class CapsOut extends Message implements I_OutMessage {
    
    public CapsOut(){
    }
    /**
     * Holds value of property value.
     */
    private KaiString value = new KaiString("03");
    /**
     * Getter for property value.
     * @return Value of property value.
     */
    public KaiString getValue()    {

        return this.value;
    }
    
    /**
     * Setter for property value.
     * @param value New value of property value.
     */
    public void setValue(pt.jkaiui.core.KaiString value)    {

        this.value = value;
    }
    
    public String send() {
        
        String out = "KAI_CLIENT_CAPS;" + getValue() + ";";
        return out;
    }
    
    
    
}
