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
public class ArenaStatus extends Message implements I_InMessage {
    
    private KaiString unknown1;
    private KaiString unknown2;
    
    public ArenaStatus() {
    }
    
    public Message parse(String s) {
        
        Pattern p = Pattern.compile("KAI_CLIENT_ARENA_STATUS;(.*);(.*);");
        Matcher m = p.matcher(s);
        if (m.matches()){
            ArenaStatus msg = new ArenaStatus();
            msg.setUnknown1(new KaiString(m.group(1)));
            msg.setUnknown2(new KaiString(m.group(2)));
            
            return msg;
        }
        
        return null;
    }

    public KaiString getUnknown1() {

        return this.unknown1;
    }

    public void setUnknown1(KaiString unknown) {

        this.unknown1 = unknown;
    }

    public KaiString getUnknown2() {

        return this.unknown2;
    }

    public void setUnknown2(KaiString unknown) {

        this.unknown2 = unknown;
    }
}