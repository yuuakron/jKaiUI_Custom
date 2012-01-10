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
public class SessionKey extends Message implements I_InMessage {
    
    private KaiString sessionkey;
    
    public SessionKey() {
    }
    
    public Message parse(String s) {
        
        Pattern p = Pattern.compile("KAI_CLIENT_SESSION_KEY;(.*);");
        Matcher m = p.matcher(s);
        if (m.matches()){
            SessionKey msg = new SessionKey();
            msg.setSessionKey(new KaiString(m.group(1)));
            
            return msg;
        }
        
        return null;
    }

    public KaiString getSessionKey() {

        return this.sessionkey;
    }

    public void setSessionKey(KaiString sessionkey) {

        this.sessionkey = sessionkey;
    }
}
