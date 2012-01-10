/*
 * Arena.java
 *
 * Created on November 21, 2004, 10:56 PM
 */

package pt.jkaiui.core;

/**
 *
 * @author  pedro
 */
public class Arena extends KaiObject {

    /**
     * Holds value of property vector.
     */
    private String vector;
    
    private String description;

    /**
     * Holds value of property users.
     */
    private int users;

    /**
     * Holds value of property pass.
     */
    private boolean pass;

    /**
     * Holds value of property maxPlayers.
     */
    private int maxPlayers;

    /**
     * Holds value of property subs.
     */
    private int subs;

    /**
     * Holds value of property user.
     */
    private boolean user;
    
    /** Creates a new instance of Arena */
    public Arena() {
    }
    
    public Arena(String name){
        super(name);
    }

    /**
     * Getter for property vector.
     * @return Value of property vector.
     */
    public String getVector() {

        return this.vector;
    }

    /**
     * Setter for property vector.
     * @param vector New value of property vector.
     */
    public void setVector(String vector) {

        this.vector = vector;
        
        String name = vector.replaceFirst(".*/(.*)$","$1");
        super.setName(name);
    }

    /**
     * Getter for property users.
     * @return Value of property users.
     */
    public int getUsers() {

        return this.users;
    }

    /**
     * Setter for property users.
     * @param users New value of property users.
     */
    public void setUsers(int users) {

        this.users = users;
    }

    /**
     * Getter for property pass.
     * @return Value of property pass.
     */
    public boolean isPass() {

        return this.pass;
    }

    /**
     * Setter for property pass.
     * @param pass New value of property pass.
     */
    public void setPass(boolean pass) {

        this.pass = pass;
    }

    /**
     * Getter for property maxPlayers.
     * @return Value of property maxPlayers.
     */
    public int getMaxPlayers() {

        return this.maxPlayers;
    }

    /**
     * Setter for property maxPlayers.
     * @param maxPlayers New value of property maxPlayers.
     */
    public void setMaxPlayers(int maxPlayers) {

        this.maxPlayers = maxPlayers;
    }

    /**
     * Getter for property subs.
     * @return Value of property subs.
     */
    public int getSubs() {

        return this.subs;
    }

    /**
     * Setter for property subs.
     * @param subs New value of property subs.
     */
    public void setSubs(int subs) {

        this.subs = subs;
    }

    /**
     * Getter for property description.
     * @return Value of property description.
     */
    public String getDescription() {
        
        return this.description;
    }

    /**
     * Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(String description) {
        
        this.description = description;
    }

    /**
     * Getter for property user.
     * @return Value of property user.
     */
    public boolean isUser() {

        return this.user;
    }

    /**
     * Setter for property user.
     * @param user New value of property user.
     */
    public void setUser(boolean user) {

        this.user = user;
    }
    
    public String toString() {
	return vector;
    }
/*    
    public boolean equals(Object o) {
        return (o instanceof Arena && ((Arena)o).getVector().equals(vector));
    }
     * */
}
