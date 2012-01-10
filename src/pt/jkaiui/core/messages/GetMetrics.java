/*
 * GetMetrics.java
 *
 * Created on 8. Juni 2005, 16:20
 *
 */

package pt.jkaiui.core.messages;

import pt.jkaiui.manager.I_OutMessage;

/**
 *
 * @author jbi
 */
public class GetMetrics extends Message implements I_OutMessage{
	
	/** Creates a new instance of GetMetrics */
	public GetMetrics() {
	}

	public String send() {
		return "KAI_CLIENT_GET_METRICS;";
	}

}
