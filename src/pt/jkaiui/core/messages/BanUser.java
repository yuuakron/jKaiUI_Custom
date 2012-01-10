/*
 * BanUser.java
 *
 * Created on 14. Juni 2005, 01:36
 *
 */

package pt.jkaiui.core.messages;

import pt.jkaiui.manager.I_OutMessage;

/**
 *
 * @author jbi
 */
public class BanUser extends Message implements I_OutMessage{
	
	private String user;
	
	/** Creates a new instance of BanUser */
	public BanUser(String user) {
		this.user = user;
	}
	
	public String send() {
		return "KAI_CLIENT_ARENA_BAN;" + this.user + ";";		
	}
	
}
