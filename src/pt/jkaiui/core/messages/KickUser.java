/*
 * KickUser.java
 *
 * Created on 14. Juni 2005, 01:39
 *
 */

package pt.jkaiui.core.messages;

import pt.jkaiui.manager.I_OutMessage;

/**
 *
 * @author jbi
 */
public class KickUser extends Message implements I_OutMessage{

	private String user;
	
	/** Creates a new instance of KickUser */
	public KickUser(String user) {
		this.user = user;
	}

	public String send() {
		return "KAI_CLIENT_ARENA_KICK;" + this.user + ";";		
	}	
	
}
