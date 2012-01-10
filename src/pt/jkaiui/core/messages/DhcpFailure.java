/*
 * DhcpFailure.java
 *
 * Created on 9. Juni 2005, 21:52
 *
 */

package pt.jkaiui.core.messages;

import pt.jkaiui.manager.I_InMessage;

/**
 *
 * @author jbi
 */
public class DhcpFailure extends Message implements I_InMessage{

	private String Mac;
	
	/** Creates a new instance of DhcpFailure */
	public DhcpFailure() {
	}

	public Message parse(String s) {
		if (s.startsWith("KAI_CLIENT_DHCP_FAILURE;")) {
			String[] splits = s.split(";");
            DhcpFailure msg = new DhcpFailure();
            msg.setMac(splits[1]);
			return msg;
		}
		
		return null;

	}
		
	private void setMac(String s) {
		this.Mac = s;
	}
	
	public String getMac() {
		return this.Mac;
	}
	
}
