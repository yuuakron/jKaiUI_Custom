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
public class ConnectedArena extends Message implements I_InMessage {
    
    public ConnectedArena() {
    }
    
    public Message parse(String s) {
        
        Pattern p = Pattern.compile("KAI_CLIENT_CONNECTED_ARENA;");
        Matcher m = p.matcher(s);
        if (m.matches()){
            ConnectedArena msg = new ConnectedArena();
            
            return msg;
        }
        
        return null;
    }
}
