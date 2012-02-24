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
public class ArenaPing extends Message implements I_InMessage {

    /**
     * Holds value of property user.
     */
    private KaiString user;

    /**
     * Holds value of property ping.
     */
    private int ping;

    /**
     * Holds value of property status.
     */
    private int status;
    
    /** Creates a new instance of ClientHere */
    public ArenaPing() {
    }
    
    @Override
    public Message parse(String s) {
        
        Pattern p = Pattern.compile("KAI_CLIENT_ARENA_PING;(.*);(.*);(.*);(.*);(.*);");
        Matcher m = p.matcher(s);
        if (m.matches()){
            ArenaPing msg = new ArenaPing();
            msg.setUser(new KaiString(m.group(1)));
            msg.setPing(Integer.parseInt(m.group(2)));
            msg.setStatus(Integer.parseInt(m.group(3)));
            //TODO: SUPPORT OTHER FIELDS
            
            //msg.setAuto(m.group(3).equals("1")?true:false);
            
            return msg;
        }
        
        return null;
    }

    /**
     * Getter for property user.
     * @return Value of property user.
     */
    public KaiString getUser() {

        return this.user;
    }

    /**
     * Setter for property user.
     * @param user New value of property user.
     */
    public void setUser(KaiString user) {

        this.user = user;
    }


    /**
     * Setter for property ping.
     * @param ping New value of property ping.
     */
    public void setPing(int ping)  {

        this.ping = ping;
    }

    /**
     * Getter for property ping.
     * @return Value of property ping.
     */
    public int getPing() {
        return this.ping;
    }

    /**
     * Getter for property status.
     * @return Value of property status.
     */
    public int getStatus() {

        return this.status;
    }

    /**
     * Setter for property status.
     * @param status New value of property status.
     */
    public void setStatus(int status) {

        this.status = status;
    }
   
    
}
