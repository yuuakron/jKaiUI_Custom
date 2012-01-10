/*
 *
 * Created on November 22, 2004, 7:55 PM
 */

package pt.jkaiui.core.messages;

import pt.jkaiui.manager.I_OutMessage;

/**
 *
 * @author  pedro
 */
public class GetState extends Message implements I_OutMessage{
    
    public GetState() {
    }
    
    
    public String send() {
        
        return "KAI_CLIENT_GETSTATE;";
    }
}
