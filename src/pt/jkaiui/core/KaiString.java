/*
 * KaiString.java
 *
 * Created on November 23, 2004, 4:39 PM
 */

package pt.jkaiui.core;

/**
 *
 * @author  pedro
 */
public class KaiString {
    
    private String s;
    
    /** Creates a new instance of KaiString */
    public KaiString(String s) {
        
        this.s = s.replaceAll(";","\2");
    }
    
    public String decode(){
        
        return s.replaceAll("\2",";");
        
    }
    
    @Override
    public String toString(){
        
        return s;
    }
    
}
