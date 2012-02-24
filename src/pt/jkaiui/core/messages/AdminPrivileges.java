/*
 * ClientHere.java
 *
 * Created on November 22, 2004, 7:17 PM
 */

package pt.jkaiui.core.messages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pt.jkaiui.core.KaiString;
import pt.jkaiui.manager.I_InMessage;

/**
 *
 * @author  pedro
 */
public class AdminPrivileges extends Message implements I_InMessage {

    /**
     * Holds value of property users.
     */
    private KaiString users;
    
    /** Creates a new instance of ClientHere */
    public AdminPrivileges() {
    }
    
    @Override
    public Message parse(String s) {
        
        Pattern p = Pattern.compile("KAI_CLIENT_ADMIN_PRIVILEGES;(.*);");
        Matcher m = p.matcher(s);
        if (m.matches()){
            AdminPrivileges msg = new AdminPrivileges();
            msg.setUsers(new KaiString(m.group(1)));
            
            return msg;
        }
        
        return null;
    }

    /**
     * Getter for property users.
     * @return Value of property users.
     */
    public KaiString getUsers()  {

        return this.users;
    }

    /**
     * Setter for property users.
     * @param users New value of property users.
     */
    public void setUsers(pt.jkaiui.core.KaiString users)  {

        this.users = users;
    }


    
}
