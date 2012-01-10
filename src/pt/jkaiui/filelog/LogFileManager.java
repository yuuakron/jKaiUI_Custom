/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.jkaiui.filelog;

import java.util.*;

import pt.jkaiui.JKaiUI;
import pt.jkaiui.core.messages.*;
import static pt.jkaiui.core.KaiConfig.ConfigTag.*;

/**
 *
 * @author yuu@akron
 */
public class LogFileManager {

    private static HashMap logfiles;

    public LogFileManager() {
        init();
    }

    private void init() {
        logfiles = new HashMap();
        if (JKaiUI.getConfig().getConfigBoolean(ChatLog)) {
            logfiles.put("Chat", new ChatLog());
        }
        if (JKaiUI.develflag) {
            if (JKaiUI.getConfig().getConfigBoolean(AllLog)) {
                logfiles.put("All", new AllLog());
            }
            if (JKaiUI.getConfig().getConfigBoolean(UserLog)) {
                logfiles.put("User", new UserLog());
            }
            if (JKaiUI.getConfig().getConfigBoolean(RoomLog)) {
                logfiles.put("Room", new RoomLog());
            }

            if (JKaiUI.getConfig().getConfigBoolean(FriendLog)) {
                logfiles.put("Friend", new FriendLog());
            }
            if (JKaiUI.getConfig().getConfigBoolean(MACLog)) {
                logfiles.put("MAC", new MACLog());
            }
        }
    }

    public void println(String kinds, String text) {
        if (logfiles.containsKey(kinds)) {
            ((Log) logfiles.get(kinds)).println(text);
        }
    }

    public Log getLog(String kinds) {
        if (logfiles.containsKey(kinds)) {
            return (Log) logfiles.get(kinds);
        }
        return null;
    }

    public void println(Object o) {
        if ((o instanceof PM) || (o instanceof PMOut) || (o instanceof ArenaPM) || (o instanceof ArenaPMOut)) {
            if (logfiles.containsKey("Chat")) {
                this.getLog("Chat").println(o);
            }
        }
        if (o instanceof JoinsVector) {
            if (logfiles.containsKey("User")) {
                this.getLog("User").println(o);
            }
        }
        if ((o instanceof SubVector) || (o instanceof UserSubVector)) {
            if (logfiles.containsKey("User")) {
                this.getLog("Room").println(o);
            }
        }
        if ((o instanceof ContactOnline) || (o instanceof ContactOffline)) {
            if (logfiles.containsKey("Friend")) {
                this.getLog("Friend").println(o);
            }
        }
        if ((o instanceof RemoteArenaDevice)) {
            if (logfiles.containsKey("MAC")) {
                this.getLog("MAC").println(o);
            }
        }
    }

    public void println(Object o, Object option) {
        if ((o instanceof Chat) || (o instanceof ChatOut) || (o instanceof Chat2)) {
            if (logfiles.containsKey("Chat")) {
                this.getLog("Chat").println(o, option);
            }
        }
    }

    public void updateAll() {
        Log tmp;
        
        if (JKaiUI.getConfig().getConfigBoolean(ChatLog)) {
            if (!logfiles.containsKey("Chat")) {
                logfiles.put("Chat", new ChatLog());
            }
            tmp = (ChatLog) this.getLog("Chat");
            if (!(tmp.format(JKaiUI.getConfig().getConfigString(ChatLogFile)).equals(tmp.logfile.getPath()))) {
                tmp.update();
            }
        }

        if (JKaiUI.develflag) {
            if (JKaiUI.getConfig().getConfigBoolean(AllLog)) {
                if (!logfiles.containsKey("All")) {
                    logfiles.put("All", new AllLog());
                }
                tmp = (AllLog) this.getLog("All");
                if (!(tmp.format(JKaiUI.getConfig().getConfigString(AllLogFile)).equals(tmp.logfile.getPath()))) {
                    tmp.update();
                }
            }
            if (JKaiUI.getConfig().getConfigBoolean(UserLog)) {
                if (!logfiles.containsKey("User")) {
                    logfiles.put("User", new UserLog());
                }
                tmp = (UserLog) this.getLog("User");
                if (!(tmp.format(JKaiUI.getConfig().getConfigString(UserLogFile)).equals(tmp.logfile.getPath()))) {
                    tmp.update();
                }
            }
            if (JKaiUI.getConfig().getConfigBoolean(RoomLog)) {
                if (!logfiles.containsKey("Room")) {
                    logfiles.put("Room", new RoomLog());
                }
                tmp = (RoomLog) this.getLog("Room");
                if (!(tmp.format(JKaiUI.getConfig().getConfigString(RoomLogFile)).equals(tmp.logfile.getPath()))) {
                    tmp.update();
                }
            }

            if (JKaiUI.getConfig().getConfigBoolean(FriendLog)) {
                if (!logfiles.containsKey("Friend")) {
                    logfiles.put("Friend", new FriendLog());
                }
                tmp = (FriendLog) this.getLog("Friend");
                if (!(tmp.format(JKaiUI.getConfig().getConfigString(FriendLogFile)).equals(tmp.logfile.getPath()))) {
                    tmp.update();
                }
            }
            if (JKaiUI.getConfig().getConfigBoolean(MACLog)) {
                if (!logfiles.containsKey("MAC")) {
                    logfiles.put("MAC", new MACLog());
                }
                tmp = (MACLog) this.getLog("MAC");
                if (!(tmp.format(JKaiUI.getConfig().getConfigString(MACLogFile)).equals(tmp.logfile.getPath()))) {
                    tmp.update();
                }
            }
        }
    }
}
