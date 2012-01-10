/*
 * I_InMessage.java
 *
 * Created on November 22, 2004, 7:12 PM
 */

package pt.jkaiui.manager;

import pt.jkaiui.core.messages.Message;

/**
 *
 * @author  pedro
 */
public interface I_InMessage {
    
    public Message parse(String s);
    
}
