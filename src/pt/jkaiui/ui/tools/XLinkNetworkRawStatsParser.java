/*
 * XLinkRawStatParser.java
 *
 * Created on June 19, 2005, 11:40 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package pt.jkaiui.ui.tools;
import java.util.*;
import java.net.*;
import java.io.*;


/** 
 * <p>Document me!</p>
 *
 * @author jicksta
 */
public class XLinkNetworkRawStatsParser {
    
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("pt/jkaiui/ui/Bundle");
    
    /** Private constructors disallow construction. */
    private XLinkNetworkRawStatsParser() {}
    
    
    public static HashMap getRawStatsInfo() {
	HashMap ret = new HashMap();
	try {
	    URL domain = new URL("http://www.teamxlink.co.uk/rawstats.php?show=1");
	    BufferedReader reader = new BufferedReader(new InputStreamReader(domain.openStream()));
	    String stats = reader.readLine();
	    stats = reader.readLine();
            
            if(stats.equals("")){
                return ret;
            }
            
	    StringTokenizer st = new StringTokenizer(stats, ";");
	    
	    for(int i = 1; i <= 9; i++) {
		String currentName = sequenceToName(i);
		String currentValue = st.nextToken();
		if(i == 2) {
		    int removeIndex = currentValue.indexOf("Online - ");
		    if(removeIndex >= 0) currentValue = currentValue.substring(removeIndex + 9);
		}
		else if(i == 7) currentValue += "%";
		ret.put(currentName, currentValue);
	    }
	
	// THESE SHOULD BE LOGGED. FIX THIS, JAY! RAWR!
	} catch (UnknownHostException uhe) {
	    System.out.println("Could not connect to the stats provider");
	    return null;
	} catch (MalformedURLException murle) {
	    System.out.println("CAUGHT EXCEPTION: " + murle);
	    murle.printStackTrace();
	} catch (IOException ioe) {
	    System.out.println("CAUGHT EXCEPTION: " + ioe);
	    ioe.printStackTrace();
	}
	
	return ret;
    }
    
    public static String sequenceToName(int place) {
	switch(place) {
	    case 1:  return resourceBundle.getString("LBL_ToolbarStatsUsersOnline");
	    case 2:  return resourceBundle.getString("LBL_ToolbarStatsServerUptime");
	    case 3:  return resourceBundle.getString("LBL_ToolbarStatsSupportedGames");
	    case 4:  return resourceBundle.getString("LBL_ToolbarStatsTotalUsers");
	    case 5:  return resourceBundle.getString("LBL_ToolbarStatsGeolinkCapable");
	    case 6:  return resourceBundle.getString("LBL_ToolbarStatsOrbitalsOnline");
	    case 7:  return resourceBundle.getString("LBL_ToolbarStatsOrbitalSync");
	    case 8:  return resourceBundle.getString("LBL_ToolbarStatsGameTraffic");
	    case 9:  return resourceBundle.getString("LBL_ToolbarStatsOrbitalTraffic");
	    default: return "";
	}
    }
}
