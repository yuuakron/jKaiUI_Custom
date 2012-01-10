/*
 * GetUserProfile.java
 *
 * Created on 10. Juni 2005, 03:15
 *
 */

package pt.jkaiui.core.messages;

import pt.jkaiui.manager.I_OutMessage;

/**
 *
 * @author jbi
 */
public class GetUserProfile extends Message implements I_OutMessage {

	private String user;
	
	/** Creates a new instance of GetUserProfile */
	public GetUserProfile(String user) {
		this.user = user;
	}
	
	public String send() {
		return "KAI_CLIENT_GET_PROFILE;" + this.user + ";";
	}
	
	
}
