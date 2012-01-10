/*
 * DisconnectUser.java
 *
 * Created on 14. Juni 2005, 01:40
 *
 */

package pt.jkaiui.core.messages;

import pt.jkaiui.manager.I_OutMessage;

/**
 *
 * @author jbi
 */
public class DisconnectUser extends Message implements I_OutMessage{
	
	private String user;
	
	/** Creates a new instance of DisconnectUser */
	public DisconnectUser(String user) {
		this.user = user;
	}

	public String send() {
		return "KAI_CLIENT_ARENA_BREAK_STREAM;" + this.user + ";";		
	}
	
}
