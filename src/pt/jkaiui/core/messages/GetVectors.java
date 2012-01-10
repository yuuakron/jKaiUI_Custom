/*
 * LoginMessage.java
 *
 * Created on November 22, 2004, 6:51 PM
 */

package pt.jkaiui.core.messages;

import pt.jkaiui.core.KaiString;
import pt.jkaiui.manager.I_OutMessage;

/**
 *
 * @author  pedro
 */
public class GetVectors extends Message implements I_OutMessage {
    
    public GetVectors(){
    }
    /**
     * Holds value of property vector.
     */
    private KaiString vector;
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
        
        String out = "KAI_CLIENT_GET_VECTORS;" + getVector() + ";";
        return out;
    }
 
}
