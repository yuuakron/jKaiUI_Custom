/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.jkaiui.filelog;

import pt.jkaiui.core.messages.ContactOffline;
import pt.jkaiui.core.messages.ContactOnline;
/**
 *
 * @author yuu@akron
 */
public class FriendLog extends Log{

    public FriendLog(String file, String pattern) {
        super.init(file, pattern);
    }

    @Override
    public void println(Object friend) {
        if (!datecheck()) {
            update();
        }

      String p = pattern.replace("%T", now());
        if (friend instanceof ContactOnline) {
            ContactOnline friendtmp = (ContactOnline) friend;
            p = p.replace("%K", friend.getClass().getName());
            p = p.replace("%N", friendtmp.getUser().decode());
        }

        if (friend instanceof ContactOffline) {
            ContactOffline friendtmp = (ContactOffline) friend;
            p = p.replace("%K", friend.getClass().getName());
            p = p.replace("%N", friendtmp.getUser().decode());
        }

        logfilepw.println(p);
    }
}
