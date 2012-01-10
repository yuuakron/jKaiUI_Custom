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
public class ContactPing extends Message implements I_InMessage {

    /**
     * Holds value of property user.
     */
    private KaiString user;

    /**
     * Holds value of property ping.
     */
    private int ping;

    /**
     * Holds value of property currentArena.
     */
    private KaiString currentArena;

    /**
     * Holds value of property caps.
     */
    private KaiString caps;
    
    /** Creates a new instance of ClientHere */
    public ContactPing() {
    }
    
    public Message parse(String s) {        
        Pattern p = Pattern.compile("KAI_CLIENT_CONTACT_PING;(.*);(.*);(.*);(.*);");
        Matcher m = p.matcher(s);
        if (m.matches()){
            ContactPing msg = new ContactPing();
            msg.setUser(new KaiString(m.group(1)));
            
            String arena = m.group(2).length()==0?"Messenger mode":m.group(2);
            msg.setCurrentArena(new KaiString(arena));
            msg.setPing(Integer.parseInt(m.group(3)));
            msg.setCaps(new KaiString(m.group(4)));
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
     * Getter for property currentArena.
     * @return Value of property currentArena.
     */
    public pt.jkaiui.core.KaiString getCurrentArena()  {

        return this.currentArena;
    }

    /**
     * Setter for property currentArena.
     * @param currentArena New value of property currentArena.
     */
    public void setCurrentArena(pt.jkaiui.core.KaiString currentArena)  {

        this.currentArena = currentArena;
    }

    /**
     * Getter for property caps.
     * @return Value of property caps.
     */
    public pt.jkaiui.core.KaiString getCaps() {

        return this.caps;
    }

    /**
     * Setter for property caps.
     * @param caps New value of property caps.
     */
    public void setCaps(pt.jkaiui.core.KaiString caps) {

        this.caps = caps;
    }

    
}
