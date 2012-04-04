
package pt.jkaiui.ui.tools;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import pt.jkaiui.JKaiUI;
import pt.jkaiui.core.KaiConfig;

/** 
 * <p>Offers a static method for connecting to a remote NTP server, getting the
 * current time, and returning that as a java.util.Date object.</p>
 *
 * <p>This class is primarily used in the MainUI's toolbar to synchronize the time
 * to a correct, globally accurate settings</p>
 *
 * @author Jay
 */

public class NTPClient {
    
    /** This constructor is declared private to prevent instantation */
    private NTPClient() {}
    
    /**
     * A static method to open a Socket to the default NTP server, retrieve the
     * time, and return that time in the form of a java.util.Date object.
     *
     * @return The current time based on a remote NTP server's time.
     */
    public static long getNTPTime() {
        return getNTPTime(KaiConfig.getInstance().getConfigString(KaiConfig.ConfigTag.NTPSERVER));
    }
    
    /**
     * A static method to open a Socket to a specified NTP server, retrieve the
     * time, and return that time in the form of a java.util.Date object.
     *
     * @param serverAddress the address to an NTP server
     * @return The current time based on a remote NTP server's time.
     */
    public static long getNTPTime(String serverAddress) {
        
        Socket connection;
        
        try {
            // Try opening a Socket to the specified address on the NTP port
            connection = new Socket(serverAddress, 37);
            
            // If instantiating the Socket didn't throw an Exception, then we open
            // a BufferedInputStream to the server in preparation to read the data.
            BufferedInputStream bis = new BufferedInputStream(connection.getInputStream(), connection.getReceiveBufferSize());
            
            // To make it more accurate, I have it check the current time before it
            // starts reading. This value will be used later to adjust the time
            // based on how long it actually took to get the time.
            long beforeTime = System.currentTimeMillis();
            
            // Read a long datatype (split into four bytes)
            int b1 = bis.read(); int b2 = bis.read(); int b3 = bis.read(); int b4 = bis.read();
            
            // Check the time after the four bytes are read.
            long afterTime = System.currentTimeMillis();
            
            // Attempt to close the socket. If it doesn't close, oh well.  :)
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch(IOException ioe) {
                System.out.println("NTPClient:"+ioe);
            }
            
            // Check if any of the bytes were the EOF integer (-1)
            if((b1 | b2 | b3 | b4) < 0) {
                throw new Exception("The time received was an invalid negative \"long\" number.");
            }
            
            // Lastly, we multiply the time retrieved from the NTP server by 1000
            // to convert it to milliseconds, add the delay it took to actually
            // get the time from the server, then return the new Date object.
            return (((((long) b1) << 24) + (b2 << 16) + (b3 << 8) + b4) * 1000) + (afterTime - beforeTime);
            
        } catch(Exception e) {
            // TODO: THIS SHOULD BE LOGGED, NOT PRINTED!!!!
            System.out.println("Failed to retrieve NTP time. Falling back to system time! Reason: " + e);
            return 0L;
        }
    }
}
