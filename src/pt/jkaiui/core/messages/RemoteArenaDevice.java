/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.jkaiui.core.messages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pt.jkaiui.core.KaiString;
import pt.jkaiui.manager.I_InMessage;

/**
 *
 * @author yuu@akron
 */
public class RemoteArenaDevice  extends Message implements I_InMessage  {
    
        /**
     * Holds value of property user.
     */
    private KaiString user;
    private KaiString mac;
    
    /** Creates a new instance of ClientHere */
    public RemoteArenaDevice() {
    }
    
    public Message parse(String s) {
        
        Pattern p = Pattern.compile("KAI_CLIENT_REMOTE_ARENA_DEVICE;(.*);(.*);");
        Matcher m = p.matcher(s);
        if (m.matches()){
            RemoteArenaDevice msg = new RemoteArenaDevice();
            msg.setUser(new KaiString(m.group(1)));
            msg.setMac(new KaiString(m.group(2)));
            
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

    public KaiString getMac() {
        return mac;
    }

    public void setMac(KaiString mac) {
        this.mac = mac;
    }
    
    
}
