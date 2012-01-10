/*
 * LocalDevice.java
 *
 * Created on 9. Juni 2005, 20:19
 *
 */

package pt.jkaiui.core.messages;

import pt.jkaiui.manager.I_InMessage;

/**
 *
 * @author jbi
 */
public class LocalDevice extends Message implements I_InMessage{
	
	private String Mac;
	
	/** Creates a new instance of LocalDevice */
	public LocalDevice() {
	}
	
	public Message parse(String s) {
		if (s.startsWith("KAI_CLIENT_LOCAL_DEVICE;")) {
			String[] splits = s.split(";");
            LocalDevice msg = new LocalDevice();
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
