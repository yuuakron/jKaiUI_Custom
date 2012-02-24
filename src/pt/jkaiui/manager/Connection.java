/*
 * Connection.java
 *
 * Created on November 22, 2004, 6:01 PM
 */

package pt.jkaiui.manager;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import pt.jkaiui.JKaiUI;
import pt.jkaiui.tools.log.ConfigLog;

/**
 *
 * @author  pedro
 */
public class Connection extends Thread {
    
    
    private static Logger _logger;
    
    private InetAddress ia;
    private DatagramSocket csocket;
    private String indir;
    private int port;
    
    public Connection(String indir,int port){
        
        _logger = ConfigLog.getLogger(this.getClass().getName());
        
        this.indir = indir;
        this.port = port;
        try{
            csocket = new DatagramSocket();
            ia = InetAddress.getByName(indir);
            start();
        }
        catch (Exception e){
            System.out.println("Connection error.:"+e);
        }
    }
    
/*    
    public void send(String toSend) {

        if (!toSend.isEmpty()) {
            byte[] toSendtmp = StringValidator.StringtoByte(toSend);

            try {
                DatagramPacket dp = new DatagramPacket(toSendtmp, toSendtmp.length, ia, port);
                //_logger.finest("Sending string: " + toSend+ "; Number of bytes: "+toSend.getBytes().length);
                csocket.send(dp);
            } catch (Exception e) {
                System.out.println("Connection Send:"+e);
                _logger.severe(" Error sending string: " + e.getMessage());
            }
        }
    }
*/    
    public void send(byte[] toSend) {
    
        if (toSend.length > 0) {
            try {
//                byte[] toSendtmp = toSend.getBytes(Encoding);
                DatagramPacket dp = new DatagramPacket(toSend, toSend.length, ia, port);
                //_logger.finest("Sending string: " + toSend+ "; Number of bytes: "+toSend.getBytes().length);
                csocket.send(dp);
            } catch (Exception e) {
                System.out.println("Connection Send:" + e);
                _logger.log(Level.SEVERE, " Error sending string: {0}", e.getMessage());
            }
        }
    }
    
    public void close(){
        System.out.println("Closing Connection");
        try{
            csocket.close();
        }catch (Exception e){
            System.out.println("Connection close:"+e);
        }
    }
    @Override
    public void finalize(){
        close();
    }
    
    @Override
    public void run(){
        
        _logger.config("Connecting to kaid");
        
        while(true){
            
            String str = "";
            try {
                byte [] receiveData = new byte [1500];
                DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
                csocket.receive(receivePacket);
//                str = new String(receivePacket.getData());

                                
//                if (str!=""){
                if(receiveData.length > 0){
                    // Pass string to Manager
                    JKaiUI.getManager().process(receivePacket.getData());
                
                }
            }
            catch (Exception e) {
                System.out.println("Connection run:"+e);
                _logger.log(Level.SEVERE, "Error in receiver thread: {0}: {1}", new Object[]{e.getMessage(), str.trim()});
            }
        }
        
        
    }
    
}
