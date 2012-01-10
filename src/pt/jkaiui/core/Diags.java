/*
 * Diags.java
 *
 * Created on 8. Juni 2005, 18:20
 *
 */

package pt.jkaiui.core;

import javax.swing.ImageIcon;

/**
 *
 * @author jbi
 */
public class Diags extends KaiObject {
	
    private String Value1;
    private String Value2;
    private String Value3;
    private ImageIcon Icon;

    public final static int ICON_DIAG_ORBSERVER = 1;
    public final static int ICON_DIAG_NETWORK = 2;
    public final static int ICON_DIAG_ENGINE = 3;
    public final static int ICON_DIAG_HARDWARE = 4;
    public final static int ICON_DIAG_PS2 = 5;
	
    private final ImageIcon DIAG_ENGINE = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/diag_engine.png"));
    private final ImageIcon DIAG_ORB = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/diag_orb.png"));
    private final ImageIcon DIAG_NETWORK = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/diag_network.png"));
    private final ImageIcon DIAG_HARDWARE = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/diag_hardware.png"));
    private final ImageIcon DIAG_PS2 = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/ps2.png"));
    private final ImageIcon DIAG_GCN = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/gcn.png"));
    private final ImageIcon DIAG_XBOX = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/xbox.png"));
    private final ImageIcon DIAG_MAC_UNKNOWN = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/unknown-console.png"));

    /** Creates a new instance of Diags */
    public Diags() {
    }

    public void setValue1(String V1) {
	    this.Value1 = V1;
    }

    public String getValue1() {
	    return Value1;
    }

    public void setValue2(String V2) {
	    this.Value2 = V2;
    }

    public String getValue2() {
	    return Value2;
    }

    public void setValue3(String V3) {
	    this.Value3 = V3;
    }

    public String getValue3() {
	    return Value3;
    }

    public void setIcon(int IconType) {

	switch (IconType) {

	    case ICON_DIAG_ORBSERVER: {
		this.Icon = DIAG_ORB;
		break;
	    }
	    case ICON_DIAG_NETWORK: {
		this.Icon = DIAG_NETWORK;
		break;
	    }
	    case ICON_DIAG_ENGINE: {
		this.Icon = DIAG_ENGINE;
		break;
	    }
	    case ICON_DIAG_HARDWARE: {
		this.Icon = DIAG_HARDWARE;
		break;
	    }
	}
    }

    public void setConsoleIcon(String mac) {

	if (mac.startsWith("0004")) {
		this.Icon = DIAG_PS2;
	}
	else if (mac.startsWith("0009")) {
		this.Icon = DIAG_GCN;
	}
	else if (mac.startsWith("0050")) {
		this.Icon = DIAG_XBOX;			
	}
	else {
		this.Icon = DIAG_MAC_UNKNOWN;			
	}
    }

    public ImageIcon getIcon(){
	    return this.Icon;
    }

}
