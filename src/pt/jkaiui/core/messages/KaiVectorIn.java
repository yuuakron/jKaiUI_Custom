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
public class KaiVectorIn extends Message implements I_InMessage {
    
    public KaiVectorIn(){
    }
    /**
     * Holds value of property vector.
     */
    private KaiString vector;

    /**
     * Holds value of property creatable.
     */
    private boolean creatable;
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
    
    
    public Message parse(String s) {
        
        Pattern p = Pattern.compile("KAI_CLIENT_VECTOR;(.*);(.*);");
        Matcher m = p.matcher(s);
        if (m.matches()){
            KaiVectorIn msg = new KaiVectorIn();
            msg.setVector(new KaiString(m.group(1)));
            msg.setCreatable(m.group(2).equals("0")?false:true);
            
            return msg;
        }
        
        return null;
    }

    /**
     * Getter for property creatable.
     * @return Value of property creatable.
     */
    public boolean isCreatable() {

        return this.creatable;
    }

    /**
     * Setter for property creatable.
     * @param creatable New value of property creatable.
     */
    public void setCreatable(boolean creatable) {

        this.creatable = creatable;
    }
    
}
