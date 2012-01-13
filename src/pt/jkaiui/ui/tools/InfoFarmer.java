/*
 * InfoFarmer.java
 *
 * Created on July 3, 2005, 8:22 PM
 *
 */

package pt.jkaiui.ui.tools;
import java.io.*;
import java.net.*;
import java.util.Locale;

/**
 * <p>The InfoFarmer is a simple class that stores harmless statistics about
 * the user's system.</p>
 *
 * <p>To communicate with the intermediary web server that actually performs
 * all database operations, the program simply opens a connection to a URL and
 * reads only a single byte from it. That byte represents the one-digit error
 * code of the operation.</p>
 *
 * <ol start="0">
 *     <li>Success</li>
 *     <li>An error occured will accessing the database</li>
 *     <li>The database runtime update failed</li>
 *     <li>The session creation failed</li>
 *     <li>The script could not connect to the database</li>
 *     <li>The data supplied via the url was incorrect</li>
 *     <li>A general query failed</li>
 * </ol>
 *
 * @author jicksta
 * 
 * not used 2012/1/14 yuu@akron
 * 
 */

public class InfoFarmer {
    
    private static final String PHPSCRIPT_URL = "http://jkaiui.sourceforge.net/stats/ping.php?";
    
    private static final String SESSIONID = "1"; // THIS MUST BE SECURE AND CONSTANT!!   :(
    private static final String OSNAME = ((System.getProperty("mrj.version") == null)?System.getProperty("os.name"):"Apple%20OSX");
    private static final String OSVERSION = System.getProperty("os.version");
    private static final String JVMVERSION = System.getProperty("java.version");
    private static final String JVMVENDOR = System.getProperty("java.vendor");
    private static final String OSLOCALE = Locale.getDefault().getDisplayCountry();
    
    // Delay sending the updates every 3 minutes. To change this, simply change the first number
    private static final int UPDATE_DELAY = 3 * 60 * 1000;
    private static Thread updater;
    
    /**
     * <p>The method called when a connection to an engine is first started. This will
     * send the information to the web server for cataloging. This should only be called
     * once per program execution. After this has been called, startUpdater should be
     * started immediately afterwards.</p>
     * @return a boolean representing whether the command was succesful.
     */
    private static boolean establishInitialization() {
        System.out.println("establishing initialization");
        try {
            String correctURL = (PHPSCRIPT_URL + "OS=" + OSNAME + ":" + OSVERSION + "&jvm=" + JVMVENDOR + "%20" + JVMVERSION + "&locale=" + OSLOCALE + ":" + OSLOCALE + "&sid=" + SESSIONID).replaceAll(" ", "%20");
            System.out.println(correctURL);
            URL statManager = new URL(correctURL);
            InputStream is = statManager.openStream();
            
            // Once the result is read, the PHP script is already completely finished and
            // is telling us the result.
            int result = is.read() - 48;
            System.out.println("STAT ERROR CODE: " + result);
            is.close();
            
            // If we got here, everything's fine and dandy.
            return true;
            
        } catch(MalformedURLException mfurle) {
            System.out.println("infoFarmer:"+mfurle);
            return false;
        } catch(NumberFormatException nfe) {
            System.out.println("infoFarmer:"+nfe);
            return false;
        } catch(IOException ioe) {
            System.out.println("infoFarmer:"+ioe);
            return false;
        } catch(Exception e){
            System.out.println("infoFarmer:"+e);
            return false;
        }
    }
    
    /**
     * <p>While this method is primarily used internally, it also available here in this
     * public version so one may call it manually if need be.</p>
     * @return a boolean representing whether the command was successful
     */
    private static boolean sendUpdate() {
        try {
            URL statManager = new URL(PHPSCRIPT_URL + "sid=" + SESSIONID + "&run=true");
            InputStream is = statManager.openStream();
            
            // One byte is read from the web server, the error code.
            int result = is.read() - 48;
            System.out.println("Update result: " + result);
            is.close();
            
            // If we got here, everything went well.
            return true;
            
        } catch(MalformedURLException mfurle) {
            System.out.println("infoFarmer:"+mfurle);
            return false;
        } catch (IOException ioe) {
            System.out.println("infoFarmer:"+ioe);
            return false;
        }catch(Exception e){
            System.out.println("infoFarmer:"+e);
            return false;
        }
    }
    
    /**
     * <p>Starts a thread that begins sending the minimalistic "catch-up" packets. This
     * should **ONLY** be called after establishInitialization() has been called!</p>
     */
    public static void start() {

        updater = new Thread(new Runnable() {
            public void run() {
                try {
                    boolean successful = false;
                    while(!successful) {
                        successful = establishInitialization();
                        if(successful) Thread.sleep(UPDATE_DELAY);
                        else Thread.sleep(UPDATE_DELAY / 6);
                    }
                    
                    
                    while(true) {
                        if(sendUpdate()) Thread.sleep(UPDATE_DELAY);
                        // If the update wasn't successful, let's not wait the
                        // full time to send another response. Let's try again soon.
                        else Thread.sleep(UPDATE_DELAY / 3);
                    }
                } catch (InterruptedException ie) {
                    System.out.println("infoFarmer:"+ie);
                } catch (Exception e){
                    System.out.println("infoFarmer:"+e);
                }
            }
        });
        updater.start();
    }
    
    // Everything below are just protective accessors for the local static final variables.
    // These may prove to be very useful for other objects to utilize.
    /**
     * <p>Good for methods that, for some reason, need information about the system.
     * <em>NOTE: It is <strong>bad</strong> practice to write system dependent code!</em></p>
     * @return a String of the OS's actual name
     */
    public static String getOSName() {
        return OSNAME.replaceAll("%20", " ");
    }
    /**
     * <p>Good for methods that, for some reason, need information about the system.
     * <em>NOTE: It is <strong>bad</strong> practice to write system dependent code!</em></p>
     * @return a String of the OS's version
     */
    public static String getOSVersion() {
        return OSVERSION;
    }
    /**
     * <p>Good for methods that, for some reason, need information about the system.
     * <em>NOTE: It is <strong>bad</strong> practice to write system dependent code!</em></p>
     * @return a <em>String</em> of the JVM version running JKaiUI.
     */
    public static String getJVMVersion() {
        return JVMVERSION;
    }
}
