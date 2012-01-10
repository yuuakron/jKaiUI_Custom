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
public class SubVectorUpdate extends Message implements I_InMessage {

    /**
     * Holds value of property vector.
     */
    private KaiString vector;

    /**
     * Holds value of property count.
     */
    private int count;

    /**
     * Holds value of property subs.
     */
    private int subs;
    
    /** Creates a new instance of ClientHere */
    public SubVectorUpdate() {
    }
    
    public Message parse(String s) {
        
        Pattern p = Pattern.compile("KAI_CLIENT_SUB_VECTOR_UPDATE;(.*);(.*);(.*);");
        Matcher m = p.matcher(s);
        if (m.matches()){
            SubVectorUpdate msg = new SubVectorUpdate();
            msg.setVector(new KaiString(m.group(1)));
            msg.setCount(Integer.parseInt(m.group(2)));
            msg.setSubs(Integer.parseInt(m.group(3)));
            
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

    /**
     * Getter for property count.
     * @return Value of property count.
     */
    public int getCount()  {

        return this.count;
    }

    /**
     * Setter for property count.
     * @param count New value of property count.
     */
    public void setCount(int count)  {

        this.count = count;
    }

    /**
     * Getter for property subs.
     * @return Value of property subs.
     */
    public int getSubs() {

        return this.subs;
    }

    /**
     * Setter for property subs.
     * @param subs New value of property subs.
     */
    public void setSubs(int subs) {

        this.subs = subs;
    }

    
}
