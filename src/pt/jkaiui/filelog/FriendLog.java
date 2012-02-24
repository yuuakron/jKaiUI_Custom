/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.jkaiui.filelog;

import java.io.File;
import pt.jkaiui.JKaiUI;
import static pt.jkaiui.core.KaiConfig.ConfigTag.FriendLogFile;
import static pt.jkaiui.core.KaiConfig.ConfigTag.FriendLogPattern;
import pt.jkaiui.core.messages.ContactOffline;
import pt.jkaiui.core.messages.ContactOnline;
/**
 *
 * @author yuu@akron
 */
public class FriendLog extends Log{

    public FriendLog() {
        this.init();
    }

    @Override
    protected void init() {
//        System.out.println("friendlog");
        logfile = new File(format(JKaiUI.getConfig().getConfigFile(FriendLogFile)));
        super.init();
    }

    @Override
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
