/*
 * EngineFinder.java
 *
 * Created on July 2, 2005, 2:45 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package pt.jkaiui.manager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Collection;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import pt.jkaiui.tools.log.ConfigLog;

/**
 *
 * @author jicksta
 */
public class EngineFinder {
    
    private static Logger _logger;
    private final static int UDP_BUFFER_SIZE = 1024; // 1 kB buffer
    private final static String[] PLACES_TO_SCAN = {
        // Sending a UDP packet to 255.255.255.255 should address everyone on the
        // user's same subnet.
        "255.255.255.255",
        // A request should automatically be sent to the localhost in the event
        // the above is unreachable (for instance if there is no domain)
        "127.0.0.1"};
        
    private Vector engines;
    
    public EngineFinder() {
        engines = new Vector();
        _logger = ConfigLog.getLogger(getClass().getName());
    }
    
    public void spawnEngineReceivers(int timeout) {
        engines.clear();
        
        // Convert to milliseconds
        timeout *= 1000;
        
        ThreadGroup engineListeners = new ThreadGroup("Engine Echo Listeners");
        for(int i = 0; i < PLACES_TO_SCAN.length; i++) {
            new Thread(engineListeners, new EngineReceiver(PLACES_TO_SCAN[i], engines, timeout), "EngineReceiver for " + PLACES_TO_SCAN[i]).start();
        }
        
        try {
            // We want to tie up the thread until everything's done.
            while(engineListeners.activeCount() > 0) {
                Thread.sleep(250);
            }
        } catch(InterruptedException ie) {
            System.out.println("EngineFinder:"+ie);
        }
    }
    
    public Collection getEngines() {
        return engines;
    }
    
    class EngineReceiver implements Runnable {
        
        private Collection results;
        private String address;
        private int timeout;
        
        EngineReceiver(String address, Collection results, int timeout) {
            this.address = address;
            this.results = results;
            this.timeout = timeout;
        }
        
        @Override
        public void run() {
            DatagramSocket locateConnection;
            DatagramPacket packet;

            try {
                locateConnection = new DatagramSocket();
                locateConnection.setSoTimeout(timeout);
                
                packet = new DatagramPacket("KAI_CLIENT_DISCOVER;".getBytes(), 0, 20, InetAddress.getByName(address), 34522);
                locateConnection.send(packet);
            } catch(Exception ioe) {
                System.out.println("EngineFinder run:"+ioe);
                return;
            }

            packet.setData(new byte[UDP_BUFFER_SIZE]);

            while(true) {
                try {
                    locateConnection.receive(packet);
                    if(new String(packet.getData(), 0, packet.getLength()).startsWith("KAI_CLIENT_ENGINE_HERE;")) {
                        String address = packet.getAddress().getHostAddress();
                        if(!engines.contains(address)) {
                            engines.add(address);
                            _logger.log(Level.INFO, "Engine found at {0}", address);
                        }
                    }
                } catch (SocketTimeoutException ste) {
                    System.out.println("EngineFinder:"+ste);
                    break;
                } catch (IOException ioe) {
                    System.out.println("EnginFinder:"+ioe);
                }
            }
        }
    }
}