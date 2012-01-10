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
public class CodePage extends Message implements I_InMessage {
    
    private KaiString codepage;
    
    public CodePage() {
    }
    
    public Message parse(String s) {
        
        Pattern p = Pattern.compile("KAI_CLIENT_CODEPAGE;(.*);");
        Matcher m = p.matcher(s);
        if (m.matches()){
            CodePage msg = new CodePage();
            msg.setCodePage(new KaiString(m.group(1)));
            
            return msg;
        }
        
        return null;
    }

    public KaiString getCodePage() {

        return this.codepage;
    }

    public void setCodePage(KaiString codepage) {

        this.codepage = codepage;
    }
}
