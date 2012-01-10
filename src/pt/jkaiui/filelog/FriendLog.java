/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.jkaiui.filelog;

import pt.jkaiui.JKaiUI;
        
import java.io.File;

import pt.jkaiui.core.messages.*;
import static pt.jkaiui.core.KaiConfig.ConfigTag.*;
/**
 *
 * @author yuu@akron
 */
public class FriendLog extends Log{

    public FriendLog() {
        this.init();
    }

    protected void init() {
//        System.out.println("friendlog");
        logfile = new File(format(JKaiUI.getConfig().getConfigString(FriendLogFile)));
        super.init();
    }

    public void println(Object friend) {
        if (!datecheck()) {
            update();
        }

        String pattern = JKaiUI.getConfig().getConfigString(FriendLogPattern);
        pattern = pattern.replace("%T", now());
        if (friend instanceof ContactOnline) {
            ContactOnline friendtmp = (ContactOnline) friend;
            pattern = pattern.replace("%K", "ContactOnline");
            pattern = pattern.replace("%N", friendtmp.getUser().decode());
        }

        if (friend instanceof ContactOffline) {
            ContactOffline friendtmp = (ContactOffline) friend;
            pattern = pattern.replace("%K", "ContactOffline");
            pattern = pattern.replace("%N", friendtmp.getUser().decode());
        }

        logfilepw.println(pattern);
    }
}
