/*
 * User.java
 *
 * Created on November 21, 2004, 10:55 PM
 */

package pt.jkaiui.core;

import java.util.HashSet;
import javax.swing.ImageIcon;
import pt.jkaiui.JKaiUI;

/**
 *
 * @author  pedro
 */
public class User extends KaiObject implements Comparable {
    
    public static final int IDLE = 0;
    public static final int BUSY = 1;
    public static final int HOSTING = 2;
    public static final int DEDICATED_HOSTING = 3;
    
    /**
     * Holds value of property online.
     */
    private boolean online = false;
    
    /**
     * Holds value of property user.
     */
    private String user;
    
    /**
     * Holds value of property ping.
     */
    private int ping = 0;
    
    /**
     * Holds value of property currentArena.
     */
    private String currentArena;
    
    /**
     * Holds value of property status.
     */
    private int status;
    
    /**
     * Holds value of property icon.
     */
    private ImageIcon icon;
    
    /**
     * Holds value of property chat.
     */
    private boolean chat = false;
    
    /** Creates a new instance of User */
    public User() {
        super();
        
    }
    
    public User(String name){
        super(name);
    }
    
    /**
     * Getter for property online.
     * @return Value of property online.
     */
    public boolean isOnline() {
        
        return this.online;
    }
    
    /**
     * Setter for property online.
     * @param online New value of property online.
     */
    public void setOnline(boolean online) {
        
        this.online = online;
    }
    
    /**
     * Getter for property user.
     * @return Value of property user.
     */
    public String getUser() {
        
        return super.getName();
    }
    
    /**
     * Setter for property user.
     * @param user New value of property user.
     */
    public void setUser(String user) {
        
        super.setName(user);
    }
    
    /**
     * Getter for property ping.
     * @return Value of property ping.
     */
    public int getPing() {
        
        return this.ping;
    }
    
    /**
     * Setter for property ping.
     * @param ping New value of property ping.
     */
    public void setPing(int ping) {
        
        this.ping = ping;
    }
    
    public int compareTo(Object obj) {
        
        // If it is an arena, give it priority
        if (obj instanceof Arena)
            return 1;
        
        User u = (User) obj;
        
        String a = (this.isOnline()?"AAAA_":"BBBB_")+this.getUser();
        String b = (u.isOnline()?"AAAA_":"BBBB_")+u.getUser();
        return a.compareToIgnoreCase(b);
        
    }
    
    /**
     * Getter for property currentArena.
     * @return Value of property currentArena.
     */
    public String getCurrentArena()  {
        
        return this.currentArena;
    }
    
    /**
     * Setter for property currentArena.
     * @param currentArena New value of property currentArena.
     */
    public void setCurrentArena(String currentArena)  {
        
        this.currentArena = currentArena;
    }
    
    /**
     * Getter for property status.
     * @return Value of property status.
     */
    public int getStatus() {
        
        return this.status;
    }
    
    /**
     * Setter for property status.
     * @param status New value of property status.
     */
    public void setStatus(int status) {
        
        this.status = status;
    }
    
    /**
     * Getter for property icon.
     * @return Value of property icon.
     */
    public ImageIcon getIcon() {
        
        return this.icon;
    }
    
    /**
     * Setter for property icon.
     * @param icon New value of property icon.
     */
    public void setIcon(ImageIcon icon) {
        
        this.icon = icon;
    }
    
    /**
     * Getter for property chat.
     * @return Value of property chat.
     */
    public boolean isChat() {
        
        return this.chat;
    }
    
    /**
     * Setter for property chat.
     * @param chat New value of property chat.
     */
    public void setChat(boolean chat) {
        
        this.chat = chat;
    }
    
    
    public boolean isAdmin(){
        
        return JKaiUI.ADMINISTRATORS.contains(this.getUser());
        
    }
    
    
    public boolean isModerator(){
        
        return JKaiUI.MODERATORS.contains(this.getUser());
        
    }

    public boolean equals(Object obj) {
        if(!(obj instanceof User)){
            return false;
        }

        KaiObject u = (KaiObject) obj;

        return this.getName().equals(u.getName());
    }
}
