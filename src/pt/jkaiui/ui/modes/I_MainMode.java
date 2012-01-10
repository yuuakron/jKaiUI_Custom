/*
 * I_MainMode.java
 *
 * Created on November 21, 2004, 12:42 AM
 */

package pt.jkaiui.ui.modes;

import pt.jkaiui.core.messages.Message;

/**
 *
 * @author  pedro
 */
public interface I_MainMode {
    
    public void processMessage(Message message) throws ModeException;
    
}
