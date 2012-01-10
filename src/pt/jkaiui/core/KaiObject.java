/*
 * KaiObject.java
 *
 * Created on November 21, 2004, 10:54 PM
 */

package pt.jkaiui.core;

/**
 *
 * @author  pedro
 */
public class KaiObject implements Comparable {
    
    /**
     * Holds value of property name.
     */
    private String name;
    
    /** Creates a new instance of KaiObject */
    public KaiObject() {
    }
    
    
    public KaiObject(String name){
        
        this.name = name;
    }
    
    /**
     * Getter for property name.
     * @return Value of property name.
     */
    public String getName() {
        
        return this.name;
    }
    
    /**
     * Setter for property name.
     * @param name New value of property name.
     */
    public void setName(String name) {
        
        this.name = name;
    }
    
    
    
    public int compareTo(Object obj) {
        
        String prefix1 = generatePrefix(this);
        String prefix2 = generatePrefix(obj);
        
        KaiObject u = (KaiObject) obj;
        String a = prefix1 + this.getName();
        String b = prefix2 + u.getName();
        return a.compareToIgnoreCase(b);
        
    }
    
    private String generatePrefix(Object obj){
        
        String prefix ="";
        
        if (obj instanceof User){
            User u = (User) obj;
            if(u.isOnline())
                return "USER_AAA_";
            else
                return "USER_BBB_";
        }
        else if( obj instanceof Arena){
            return "AAAAA_";
        }
        else
            return "";
        
        
    }
    
    
    public boolean equals(Object obj) {
        
        
        KaiObject u = (KaiObject) obj;
        return this.getName().equals(u.getName());
        
    }
    
    
    
    
}
