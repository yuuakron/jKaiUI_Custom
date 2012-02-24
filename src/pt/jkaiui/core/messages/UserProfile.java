/*
 * UserProfile.java
 *
 * Created on 10. Juni 2005, 03:12
 *
 */
package pt.jkaiui.core.messages;

import pt.jkaiui.manager.I_InMessage;

/**
 *
 * @author jbi
 */
public class UserProfile extends Message implements I_InMessage {

    private String User;
    private String Age;
    private String Bandwidth;
    private String Location;
    private String Consoles;
    private String Games;

    /**
     * Creates a new instance of UserProfile
     */
    public UserProfile() {
    }

    @Override
    public Message parse(String s) {
        if (s.startsWith("KAI_CLIENT_USER_PROFILE;")) {
            String[] splits = s.split(";");
            UserProfile msg = new UserProfile();
            msg.User = splits[1];
            msg.Age = splits[2];
            msg.Bandwidth = splits[3];
            msg.Location = splits[4];
            msg.Consoles = splits[5] + "/" + splits[6] + "/" + splits[7];
            msg.Games = splits[8];
            return msg;
        }

        return null;
    }

    public String GetUser() {
        return this.User;
    }

    public String GetAge() {
        return this.Age;
    }

    public String GetBandwidth() {
        return this.Bandwidth;
    }

    public String GetLocation() {
        return this.Location;
    }

    public String GetConsoles() {
        return this.Consoles;
    }

    public String GetGames() {
        return this.Games;
    }
}
