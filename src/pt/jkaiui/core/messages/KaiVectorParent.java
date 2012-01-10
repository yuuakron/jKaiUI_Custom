/*
 * LoginMessage.java
 *
 * Created on November 22, 2004, 6:51 PM
 */

package pt.jkaiui.core.messages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pt.jkaiui.core.KaiString;
import pt.jkaiui.manager.I_OutMessage;

/**
 *
 * @author  pedro
 */
public class KaiVectorParent extends Message implements I_OutMessage {
    
    public KaiVectorParent(){
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
        
        // Remove last element
        vector = new KaiString(vector.toString().replaceFirst("(.*)/.*","$1"));
        
        this.vector = vector;
    }
    
    public String send() {
        
        String out = "KAI_CLIENT_VECTOR;" + getVector() + ";;";
        return out;
    }
    
    
    
    public static void main(String[] args){
        
        KaiString vector = new KaiString("Arena/Xbox");
        
        vector = new KaiString(vector.toString().replaceFirst("(.*)/.*","$1"));
        
    }
}
