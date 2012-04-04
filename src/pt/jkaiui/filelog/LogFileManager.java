/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.jkaiui.filelog;

import java.util.EnumMap;
import java.util.Map;
import pt.jkaiui.JKaiUI;
import pt.jkaiui.core.KaiConfig;
import static pt.jkaiui.core.KaiConfig.ConfigTag.*;
import pt.jkaiui.core.messages.*;
import static pt.jkaiui.filelog.LogFileManager.LogKinds.*;

/**
 *
 * @author yuu@akron
 */
public class LogFileManager {

    public enum LogKinds {

        All,
        Chat,
        User,
        Room,
        Friend,
        MAC
    }
    private static Map<LogKinds, Log> logfiles;
    private static final KaiConfig config = KaiConfig.getInstance();
    
    private static final LogFileManager INSTANCE = new LogFileManager();

    public static LogFileManager getInstance() {
        return INSTANCE;
    }
    
    private LogFileManager() {
        init();
    }

    private void init() {
        logfiles = new EnumMap<LogKinds, Log>(LogKinds.class);
        if (config.getConfigBoolean(ChatLog)) {
            logfiles.put(Chat, new ChatLog(config.getConfigFile(ChatLogFile), config.getConfigString(ChatLogPattern), config.getConfigString(TAG)));
        }
        if (JKaiUI.isDevel()) {
            if (config.getConfigBoolean(AllLog)) {
                logfiles.put(All, new AllLog(config.getConfigFile(AllLogFile), config.getConfigString(AllLogPattern)));
            }
            if (config.getConfigBoolean(UserLog)) {
                logfiles.put(User, new UserLog(config.getConfigFile(UserLogFile), config.getConfigString(UserLogPattern)));
            }
            if (config.getConfigBoolean(RoomLog)) {
                logfiles.put(Room, new RoomLog(config.getConfigFile(RoomLogFile), config.getConfigString(RoomLogPattern)));
            }

            if (config.getConfigBoolean(FriendLog)) {
                logfiles.put(Friend, new FriendLog(config.getConfigFile(FriendLogFile), config.getConfigString(FriendLogPattern)));
            }
            if (config.getConfigBoolean(MACLog)) {
                logfiles.put(MAC, new MACLog(config.getConfigFile(MACLogFile), config.getConfigString(MACLogPattern)));
            }
        }
    }

    
    //println(Object, Object)が呼ばれる？
    /*
    public void println(LogKinds kinds, String text) {
        if (logfiles.containsKey(kinds)) {
            logfiles.get(kinds).println(text);
        }
    }
    * 
    */

    public Log getLog(LogKinds kinds) {
        if (logfiles.containsKey(kinds)) {
            return logfiles.get(kinds);
        }
        return null;
    }

    public void println(Object o) {
        if ((o instanceof PM) || (o instanceof PMOut) || (o instanceof ArenaPM) || (o instanceof ArenaPMOut)) {
            if (logfiles.containsKey(Chat)) {
                this.getLog(Chat).println(o);
            }
        }
        if (o instanceof JoinsVector) {
            if (logfiles.containsKey(User)) {
                this.getLog(User).println(o);
            }
        }
        if ((o instanceof SubVector) || (o instanceof UserSubVector)) {
            if (logfiles.containsKey(User)) {
                this.getLog(Room).println(o);
            }
        }
        if ((o instanceof ContactOnline) || (o instanceof ContactOffline)) {
            if (logfiles.containsKey(Friend)) {
                this.getLog(Friend).println(o);
            }
        }
        if ((o instanceof RemoteArenaDevice)) {
            if (logfiles.containsKey(MAC)) {
                this.getLog(MAC).println(o);
            }
        }
    }

    public void println(Object o, Object option) {
        if ((o instanceof Chat) || (o instanceof ChatOut) || (o instanceof Chat2)) {
            if (logfiles.containsKey(Chat)) {
                this.getLog(Chat).println(o, option);
            }
        }
    }

    public void updateAll() {
        Log tmp;
        
        if (config.getConfigBoolean(ChatLog)) {
            if (!logfiles.containsKey(Chat)) {
                logfiles.put(Chat, new ChatLog(config.getConfigFile(ChatLogFile), config.getConfigString(ChatLogPattern), config.getConfigString(TAG)));
            }
            tmp = this.getLog(Chat);
            if (!(tmp.format(config.getConfigFile(ChatLogFile)).equals(tmp.logfile.getPath()))) {
                tmp.update();
            }
        }

        if (JKaiUI.isDevel()) {
            if (config.getConfigBoolean(AllLog)) {
                if (!logfiles.containsKey(All)) {
                    logfiles.put(All, new AllLog(config.getConfigFile(AllLogFile), config.getConfigString(AllLogPattern)));
                }
                tmp = this.getLog(All);
                if (!(tmp.format(config.getConfigFile(AllLogFile)).equals(tmp.logfile.getPath()))) {
                    tmp.update();
                }
            }
            if (config.getConfigBoolean(UserLog)) {
                if (!logfiles.containsKey(User)) {
                    logfiles.put(User, new UserLog(config.getConfigFile(UserLogFile), config.getConfigString(UserLogPattern)));
                }
                tmp = this.getLog(User);
                if (!(tmp.format(config.getConfigFile(UserLogFile)).equals(tmp.logfile.getPath()))) {
                    tmp.update();
                }
            }
            if (config.getConfigBoolean(RoomLog)) {
                if (!logfiles.containsKey(Room)) {
                    logfiles.put(Room, new RoomLog(config.getConfigFile(RoomLogFile), config.getConfigString(RoomLogPattern)));
                }
                tmp = this.getLog(Room);
                if (!(tmp.format(config.getConfigFile(RoomLogFile)).equals(tmp.logfile.getPath()))) {
                    tmp.update();
                }
            }

            if (config.getConfigBoolean(FriendLog)) {
                if (!logfiles.containsKey(Friend)) {
                    logfiles.put(Friend, new FriendLog(config.getConfigFile(FriendLogFile), config.getConfigString(FriendLogPattern)));
                }
                tmp = this.getLog(Friend);
                if (!(tmp.format(config.getConfigFile(FriendLogFile)).equals(tmp.logfile.getPath()))) {
                    tmp.update();
                }
            }
            if (config.getConfigBoolean(MACLog)) {
                if (!logfiles.containsKey(MAC)) {
                    logfiles.put(MAC, new MACLog(config.getConfigFile(MACLogFile), config.getConfigString(MACLogPattern)));
                }
                tmp = this.getLog(MAC);
                if (!(tmp.format(config.getConfigFile(MACLogFile)).equals(tmp.logfile.getPath()))) {
                    tmp.update();
                }
            }
        }
    }
}
