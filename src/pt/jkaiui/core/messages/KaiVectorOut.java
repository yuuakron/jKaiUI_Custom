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
public class KaiVectorOut extends Message implements I_OutMessage {
    
    public KaiVectorOut(){
    }
    /**
     * Holds value of property vector.
     */
    private KaiString vector;
    
    /**
     * Holds value of property password.
     */
    private KaiString password = new KaiString("");

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
    
    public String send() {
        
        String out = "KAI_CLIENT_VECTOR;" + getVector() + ";"+ getPassword() +";";
        return out;
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
