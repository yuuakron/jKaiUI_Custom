/*
 * Metrics.java
 *
 * Created on 8. Juni 2005, 15:27
 *
 */

package pt.jkaiui.core.messages;
import pt.jkaiui.manager.I_InMessage;

/**
 *
 * @author jbi
 */
public class Metrics extends Message implements I_InMessage {
	
	private String OrbServer;
	private String IP;
	private String Port;
	private String Reachable;
	private String NetCard;
	private String Locked;
	private String Technology;
	private String BytesEngine;
	private String BytesChat;
	private String BytesOrb;
	private String Version;
	private String Platform;
	private String Signed;
	
	/** Creates a new instance of Metrics */
	public Metrics() {
	}
		
	public Message parse(String s) {
		if (s.startsWith("KAI_CLIENT_METRICS;")) {
			String[] splits = s.split(";");
            Metrics msg = new Metrics();
            msg.setOrbServer(splits[1]);
            msg.setReachable(splits[2]);
            msg.setIP(splits[3]);
            msg.setPort(splits[4]);
            msg.setVersion(splits[5]);
            msg.setPlatform(splits[6]);
            msg.setSigned(splits[7]);
            msg.setNetCard(splits[8]);
            msg.setTechnology(splits[16]);
            return msg;
        }
        
        return null;
	}
	
	public void setOrbServer(String s) {
		this.OrbServer = s;
	}
	
	public String getOrbServer() {
		return this.OrbServer;
	}

	public void setIP(String s) {
		this.IP = s;
	}
	
	public String getIP() {
		return this.IP;
	}

	public void setPort(String s) {
		this.Port = s;
	}
	
	public String getPort() {
		return this.Port;
	}

	public void setReachable(String s) {
		this.Reachable = s;
	}
	
	public String getReachable() {
		return this.Reachable;
	}

	public void setVersion(String s) {
		this.Version = s;
	}
	
	public String getVersion() {
		return this.Version;
	}

	public void setPlatform(String s) {
		this.Platform = s;
	}
	
	public String getPlatform() {
		return this.Platform;
	}

	public void setSigned(String s) {
		this.Signed = s;
	}
	
	public String getSigned() {
		return this.Signed;
	}

	public void setNetCard(String s) {
		this.NetCard = s;
	}
	
	public String getNetCard() {
		return this.NetCard;
	}

	public void setTechnology(String s) {
		this.Technology = s;
	}
	
	public String getTechnology() {
		return this.Technology;
	}

}
