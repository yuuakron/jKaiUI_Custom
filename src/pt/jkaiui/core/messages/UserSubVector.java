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
public class UserSubVector extends Message implements I_InMessage {

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

    /**
     * Holds value of property maxPlayers.
     */
    private int maxPlayers;

    /**
     * Holds value of property pass.
     */
    private boolean pass;

    /**
     * Holds value of property description.
     */
    private KaiString description;
    
    /** Creates a new instance of ClientHere */
    public UserSubVector() {
    }
    
    public Message parse(String s) {
        
        Pattern p = Pattern.compile("KAI_CLIENT_USER_SUB_VECTOR;(.*);(.*);(.*);(.*);(.*);(.*);");
        Matcher m = p.matcher(s);
        if (m.matches()){
            UserSubVector msg = new UserSubVector();
            msg.setVector(new KaiString(m.group(1)));
            msg.setCount(Integer.parseInt(m.group(2)));
            msg.setSubs(Integer.parseInt(m.group(3)));
            msg.setPass( m.group(4).equals("0")?false:true );
            msg.setMaxPlayers(Integer.parseInt(m.group(5)));
            msg.setDescription(new KaiString(m.group(6)));
            
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

    /**
     * Getter for property maxPlayers.
     * @return Value of property maxPlayers.
     */
    public int getMaxPlayers() {

        return this.maxPlayers;
    }

    /**
     * Setter for property maxPlayers.
     * @param maxPlayers New value of property maxPlayers.
     */
    public void setMaxPlayers(int maxPlayers) {

        this.maxPlayers = maxPlayers;
    }

    /**
     * Getter for property pass.
     * @return Value of property pass.
     */
    public boolean isPass() {

        return this.pass;
    }

    /**
     * Setter for property pass.
     * @param pass New value of property pass.
     */
    public void setPass(boolean pass) {

        this.pass = pass;
    }

    /**
     * Getter for property description.
     * @return Value of property description.
     */
    public KaiString getDescription() {

        return this.description;
    }

    /**
     * Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(KaiString description) {

        this.description = description;
    }

    
}
