/*
 * KaiConfig.java
 *
 * Created on November 18, 2004, 4:23 PM
 */

package pt.jkaiui.core;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.prefs.Preferences;
import pt.jkaiui.JKaiUI;

/**
 *
 * @author yuu@akron
 * 
 */
public class KaiConfig {
    
    private final static KaiConfig INSTANCE = new KaiConfig();
    
    public static KaiConfig getInstance(){
        return INSTANCE;
    }
    
    private static final String PREFERENCES = "/pt/jkaiui";    
    private static Preferences preferences;
    private static String settingfolder;

    private HashMap configs;
    private HashMap initconfigs;
   
    public enum ConfigAttri{ NON, ORIGINAL, OTHER, LOG, CHATPM, ROOMUSER, SOUND }
    public enum ConfigTag {
        TAG,
        PASSWORD,
        BOOKMARKS,
        HOST,
        PORT,
        NTPSERVER,
        AUTOMATICALLYDETECTED,
        CACHEDAYS,
        ALLOWSTATISTICS,
        SHOWTIMESTAMPS,
        SHOWSERVERSTATS,
        STOREWINDOWSIZEPOSITION,
        PLAYMESSAGESOUND,
        AVATARCACHE,
        WINDOWHEIGTH,
        WINDOWWIDTH,
        WINDOWX,
        WINDOWY,
        
        AutoHostSetting,
        AutoArenaMoving,
        SettingFolder,
        
        AllLog,
        ChatLog,
        UserLog,
        RoomLog,
        FriendLog,
        MACLog,
        AllLogFile,
        ChatLogFile,
        UserLogFile,
        RoomLogFile,
        FriendLogFile,
        MACLogFile,
        AllLogPattern,
        ChatLogPattern,
        UserLogPattern,
        RoomLogPattern,
        FriendLogPattern,
        MACLogPattern,
        
        ShowLatestChat,
        HideServerMessage,
        ChatFontSize,
        SystemFontSize,
        InputFieldFontSize,
        ShowFriendLoginInfo,
        MaxChatHistory,
        ChatDisplayStyle,
        ChatWrap,
        ShowImageMouseoverLink,
        ColorBackground,
        CUICommand,
        AskCommand,
        
        URLDecode,
        RoomFontSize,
        
        ChatSound,
        PMOpenSound,
        FriendPMSound,
        FriendChatSound,
        FriendOnlineSound,
        ArenaPMSound,
        ModeratorChatSound,
        SendSound,
        ChatSoundFile,
        PMOpenSoundFile,
        FriendPMSoundFile,
        FriendChatSoundFile,
        FriendOnlineSoundFile,
        ArenaPMSoundFile,
        ModeratorChatSoundFile,
        SendSoundFile
    }
    
    private class Pair<T1, T2> {

        public T1 first;
        public T2 second;

        public Pair(T1 first, T2 second) {
            this.first = first;
            this.second = second;
        }

        public <U1, U2> Pair<U1, U2> create(U1 first, U2 second) {
            return new Pair<U1, U2>(first, second);
        }

        @Override
        public String toString() {
            return "[" + first + "," + second + "]";
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Pair) {
                Pair other = (Pair) o;
                return first.equals(other.first)
                        && second.equals(other.second);
            }
            return false;
        }
    }
    
    /** Creates a new instance of KaiConfig */
    private KaiConfig() {
        initConfig();
    }
    
    private void initConfig(){
        initconfigs = new HashMap();
        configs = new HashMap();

        initconfigs.put(ConfigTag.TAG, new Pair(ConfigAttri.NON,""));
        initconfigs.put(ConfigTag.PASSWORD, new Pair(ConfigAttri.NON, ""));
        initconfigs.put(ConfigTag.HOST, new Pair(ConfigAttri.ORIGINAL, "127.0.0.1"));
        initconfigs.put(ConfigTag.PORT, new Pair(ConfigAttri.ORIGINAL, 34522));
        initconfigs.put(ConfigTag.NTPSERVER, new Pair(ConfigAttri.ORIGINAL, "ntp1.ptb.de"));
        initconfigs.put(ConfigTag.AUTOMATICALLYDETECTED, new Pair(ConfigAttri.ORIGINAL, true));
        initconfigs.put(ConfigTag.CACHEDAYS, new Pair(ConfigAttri.ORIGINAL, 3));
        initconfigs.put(ConfigTag.SHOWTIMESTAMPS, new Pair(ConfigAttri.ORIGINAL, false));
        initconfigs.put(ConfigTag.SHOWSERVERSTATS, new Pair(ConfigAttri.ORIGINAL, false));
        initconfigs.put(ConfigTag.STOREWINDOWSIZEPOSITION, new Pair(ConfigAttri.ORIGINAL, false));
        initconfigs.put(ConfigTag.PLAYMESSAGESOUND, new Pair(ConfigAttri.ORIGINAL, true));
        initconfigs.put(ConfigTag.AVATARCACHE, new Pair(ConfigAttri.NON, System.getProperty("user.home") + File.separator + ".java" + File.separator + ".jkaiui_cache"));
        initconfigs.put(ConfigTag.BOOKMARKS, new Pair(ConfigAttri.NON, ""));
        initconfigs.put(ConfigTag.WINDOWHEIGTH, new Pair(ConfigAttri.ORIGINAL, 500));
        initconfigs.put(ConfigTag.WINDOWWIDTH, new Pair(ConfigAttri.ORIGINAL, 200));
        initconfigs.put(ConfigTag.WINDOWX, new Pair(ConfigAttri.ORIGINAL, 0));
        initconfigs.put(ConfigTag.WINDOWY, new Pair(ConfigAttri.ORIGINAL, 0));
        
        initconfigs.put(ConfigTag.AutoHostSetting, new Pair(ConfigAttri.OTHER, false));
        initconfigs.put(ConfigTag.AutoArenaMoving, new Pair(ConfigAttri.OTHER, false));
        initconfigs.put(ConfigTag.SettingFolder, new Pair(ConfigAttri.NON, "default:"));
        
        initconfigs.put(ConfigTag.ChatLog, new Pair(ConfigAttri.LOG, false));
        initconfigs.put(ConfigTag.ChatLogFile, new Pair(ConfigAttri.LOG, "log/Chatlog-%Y%M%D.txt"));
        initconfigs.put(ConfigTag.ChatLogPattern, new Pair(ConfigAttri.LOG, "%T;%K;%R;%S;%M"));
        
        if (JKaiUI.isDevel()) {
            initconfigs.put(ConfigTag.AllLog, new Pair(ConfigAttri.LOG, false));
            initconfigs.put(ConfigTag.UserLog, new Pair(ConfigAttri.LOG, false));
            initconfigs.put(ConfigTag.RoomLog, new Pair(ConfigAttri.LOG, false));
            initconfigs.put(ConfigTag.FriendLog, new Pair(ConfigAttri.LOG, false));
            initconfigs.put(ConfigTag.MACLog, new Pair(ConfigAttri.LOG, false));
            
            initconfigs.put(ConfigTag.AllLogFile, new Pair(ConfigAttri.LOG, "log/Alllog-%Y%M%D.txt"));
            initconfigs.put(ConfigTag.UserLogFile, new Pair(ConfigAttri.LOG, "log/Userlog-%Y%M%D.txt"));
            initconfigs.put(ConfigTag.RoomLogFile, new Pair(ConfigAttri.LOG, "log/Roomlog-%Y%M%D.txt"));
            initconfigs.put(ConfigTag.FriendLogFile, new Pair(ConfigAttri.LOG, "log/Friendlog-%Y%M%D.txt"));
            initconfigs.put(ConfigTag.MACLogFile, new Pair(ConfigAttri.LOG, "log/MAClog-%Y%M%D.txt"));
            
            initconfigs.put(ConfigTag.AllLogPattern, new Pair(ConfigAttri.LOG, "%T;%M"));
            initconfigs.put(ConfigTag.UserLogPattern, new Pair(ConfigAttri.LOG, "%N"));
            initconfigs.put(ConfigTag.RoomLogPattern, new Pair(ConfigAttri.LOG, "%V;%C;%S;%P;%M;%D"));
            initconfigs.put(ConfigTag.FriendLogPattern, new Pair(ConfigAttri.LOG, "%T;%K;%N"));
            initconfigs.put(ConfigTag.MACLogPattern, new Pair(ConfigAttri.LOG, "%N;%A"));
        }
                
        initconfigs.put(ConfigTag.ShowLatestChat, new Pair(ConfigAttri.CHATPM, true));
        initconfigs.put(ConfigTag.HideServerMessage, new Pair(ConfigAttri.CHATPM, false));
        initconfigs.put(ConfigTag.ChatFontSize, new Pair(ConfigAttri.CHATPM, 12));
        initconfigs.put(ConfigTag.SystemFontSize, new Pair(ConfigAttri.CHATPM, 10));
        initconfigs.put(ConfigTag.InputFieldFontSize, new Pair(ConfigAttri.CHATPM, 12));
        initconfigs.put(ConfigTag.ShowFriendLoginInfo, new Pair(ConfigAttri.CHATPM, false));
        initconfigs.put(ConfigTag.MaxChatHistory, new Pair(ConfigAttri.CHATPM, 100));
        initconfigs.put(ConfigTag.ChatDisplayStyle, new Pair(ConfigAttri.CHATPM, 0));
        initconfigs.put(ConfigTag.ChatWrap, new Pair(ConfigAttri.CHATPM, false));
        initconfigs.put(ConfigTag.ShowImageMouseoverLink, new Pair(ConfigAttri.CHATPM, false));
        initconfigs.put(ConfigTag.ColorBackground, new Pair(ConfigAttri.CHATPM, true));
        initconfigs.put(ConfigTag.CUICommand, new Pair(ConfigAttri.CHATPM, true));
        initconfigs.put(ConfigTag.AskCommand, new Pair(ConfigAttri.CHATPM, false));

        initconfigs.put(ConfigTag.URLDecode, new Pair(ConfigAttri.ROOMUSER, true));
        initconfigs.put(ConfigTag.RoomFontSize, new Pair(ConfigAttri.ROOMUSER, 12));

        initconfigs.put(ConfigTag.ChatSound, new Pair(ConfigAttri.SOUND, false));
        initconfigs.put(ConfigTag.PMOpenSound, new Pair(ConfigAttri.SOUND, false));
        initconfigs.put(ConfigTag.FriendPMSound, new Pair(ConfigAttri.SOUND, false));
        initconfigs.put(ConfigTag.FriendChatSound, new Pair(ConfigAttri.SOUND, false));
        initconfigs.put(ConfigTag.FriendOnlineSound, new Pair(ConfigAttri.SOUND, false));
        initconfigs.put(ConfigTag.ArenaPMSound, new Pair(ConfigAttri.SOUND, false));
        initconfigs.put(ConfigTag.ModeratorChatSound, new Pair(ConfigAttri.SOUND, false));
        initconfigs.put(ConfigTag.SendSound, new Pair(ConfigAttri.SOUND, false));
        initconfigs.put(ConfigTag.ChatSoundFile, new Pair(ConfigAttri.SOUND, "default"));
        initconfigs.put(ConfigTag.PMOpenSoundFile, new Pair(ConfigAttri.SOUND, "default"));
        initconfigs.put(ConfigTag.FriendPMSoundFile, new Pair(ConfigAttri.SOUND, "default"));
        initconfigs.put(ConfigTag.FriendChatSoundFile, new Pair(ConfigAttri.SOUND, "default"));
        initconfigs.put(ConfigTag.FriendOnlineSoundFile, new Pair(ConfigAttri.SOUND, "default"));
        initconfigs.put(ConfigTag.ArenaPMSoundFile, new Pair(ConfigAttri.SOUND, "default"));
        initconfigs.put(ConfigTag.ModeratorChatSoundFile, new Pair(ConfigAttri.SOUND, "default"));
        initconfigs.put(ConfigTag.SendSoundFile, new Pair(ConfigAttri.SOUND, "default"));
               
        configs = (HashMap)initconfigs.clone();
        
        //settingfoler
        String className = this.getClass().getName().replace('.', '/');
        String classPath = this.getClass().getResource("/" + className + ".class").toString();
        String url = classPath.replace("/" + className + ".class", "");
        settingfolder = (new File(url).getParent()).replaceFirst("jar:", "").replaceFirst("file:", "");
//        System.out.println("setting folder"+settingfolder);
        
        readConfig();
    }
    
    
    public Object getConfig(Enum ConfigTag){
        try {
            return ((Pair)configs.get(ConfigTag)).second;
        } catch (Exception e) {
            System.out.println("setting error1:" + ConfigTag);
            return null;
        }
    }
    
    public String getConfigString(Enum ConfigTag) {
        try {
            return (String)((Pair)configs.get(ConfigTag)).second;
        } catch (Exception e) {
            System.out.println("setting error2:" + ConfigTag);
            return null;
        }
    }

    public boolean getConfigBoolean(Enum ConfigTag) {
        try {
            return ((Boolean) ((Pair)configs.get(ConfigTag)).second).booleanValue();
        } catch (Exception e) {
            System.out.println("setting error3:" +ConfigTag);
            return false;
        }
    }

    public int getConfigInt(Enum ConfigTag) {
        try {
            return ((Integer) ((Pair)configs.get(ConfigTag)).second).intValue();
        } catch (Exception e) {
            System.out.println("setting error4:" + ConfigTag);
            return 0;
        }
    }
    
    public String getConfigSettingFolder() {
        try {
          String folder = (String)((Pair)configs.get(ConfigTag.SettingFolder)).second;
          if(folder.equalsIgnoreCase("default:")){
              return settingfolder;
          }else{
              return folder.replaceFirst("~", System.getProperty("user.home"));
          }
        } catch (Exception e) {
            System.out.println("setting error5:" + ConfigTag.SettingFolder);
            return null;
        }
    }
    
    public String getConfigFile(Enum ConfigTag) {
        try {
            return getConfigSettingFolder()+"/"+((String)((Pair)configs.get(ConfigTag)).second);
        } catch (Exception e) {
            System.out.println("setting error6:" + ConfigTag);
            return null;
        }
    }
    
    public void setConfig(Enum configtag, Object setting){
        if(configs.containsKey(configtag)){
            configs.remove(configtag);
        }
        configs.put(configtag, new Pair(((Pair)initconfigs.get(configtag)).first ,setting));
    }
    
    private void setConfig(String configtag, Object setting){
        setConfig(ConfigTag.valueOf(configtag), setting);
    }
    
    public void readConfig() {
        preferences = Preferences.userRoot().node(PREFERENCES + JKaiUI.Info.getShortVersion());
        
        Iterator initconfigsiter = initconfigs.keySet().iterator();
        while (initconfigsiter.hasNext()) {
            ConfigTag key = (ConfigTag)initconfigsiter.next();
//            System.out.println(key);
            Pair pair = (Pair)initconfigs.get(key);
            
            if (pair.second instanceof String) {
                configs.put(key, new Pair(pair.first, preferences.get(key.name(), (String)pair.second)));
            } else if (pair.second instanceof Integer) {
                configs.put(key, new Pair(pair.first, preferences.getInt(key.name(), ((Integer)pair.second).intValue())));
            } else if (pair.second instanceof Boolean) {
                configs.put(key, new Pair(pair.first, preferences.getBoolean(key.name(), ((Boolean)pair.second).booleanValue())));
            }
        }
    }
    
    public void saveConfig(){
        Iterator initconfigsiter = initconfigs.keySet().iterator();
        while (initconfigsiter.hasNext()) {
            ConfigTag key = (ConfigTag) initconfigsiter.next();
            Pair pair = (Pair)initconfigs.get(key);
            Pair pair2 = (Pair)configs.get(key);
            
            if (pair.second instanceof String) {
                preferences.put(key.name(), (String)pair2.second);
            } else if (pair.second instanceof Integer) {
                preferences.putInt(key.name(), ((Integer) pair2.second).intValue());
            } else if (pair.second instanceof Boolean) {
                preferences.putBoolean(key.name(), ((Boolean) pair2.second).booleanValue());
            }
        }
    }

    public void resetConfig() {
//        configs = new HashMap(initconfigs);
        configs = (HashMap) initconfigs.clone();
        saveConfig();
    }

    public void copytoClipboard() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(copyconfig("all"));
        clipboard.setContents(selection, null);
    }

    public String copyconfig(String kinds) {

        StringBuilder strbuf = new StringBuilder("");
        if (kinds.equalsIgnoreCase("all")) {

            strbuf.append("Setting information \n\n");
            strbuf.append(JKaiUI.Info.getLongVersion()).append("\n");

            strbuf.append(copyconfig("original"));
            strbuf.append(copyconfig("other"));
            strbuf.append(copyconfig("log"));
            strbuf.append(copyconfig("chatpm"));
            strbuf.append(copyconfig("roomuser"));
            strbuf.append(copyconfig("sound"));
        } else {

            ConfigAttri tmp = null;
            if (kinds.equalsIgnoreCase("original")) {
                strbuf.append("\nOriginal Setting\n\n");
                tmp = ConfigAttri.ORIGINAL;
            }

            if (kinds.equalsIgnoreCase("other")) {
                strbuf.append("\nOther Setting\n\n");
                tmp = ConfigAttri.OTHER;
            }
            if (kinds.equalsIgnoreCase("log")) {
                strbuf.append("\nLog File Setting\n\n");
                tmp = ConfigAttri.LOG;
            }
            if (kinds.equalsIgnoreCase("chatpm")) {
                strbuf.append("\nChat/PM Setting\n\n");
                tmp = ConfigAttri.CHATPM;
            }
            if (kinds.equalsIgnoreCase("roomuser")) {
                strbuf.append("\nRoom/User Setting\n\n");
                tmp = ConfigAttri.ROOMUSER;
            }
            if (kinds.equalsIgnoreCase("sound")) {
                strbuf.append("\nSound Setting\n\n");
                tmp = ConfigAttri.SOUND;
            }

            ConfigTag[] keys = ConfigTag.values();
            for (int i = 0; i < keys.length; i++) {
                ConfigTag key = keys[i];
                if (configs.containsKey(key)) {
                    Pair pair = (Pair) initconfigs.get(key);
                    if (pair.first.equals(tmp)) {
                        if (pair.second instanceof String) {
                            strbuf.append(key).append(":").append(preferences.get(key.name(), (String) pair.second)).append("\n");
                        } else if (pair.second instanceof Integer) {
                            strbuf.append(key).append(":").append(preferences.getInt(key.name(), ((Integer) pair.second).intValue())).append("\n");
                        } else if (pair.second instanceof Boolean) {
                            strbuf.append(key).append(":").append(preferences.getBoolean(key.name(), ((Boolean) pair.second).booleanValue())).append("\n");
                        }
                    }
                }
            }
        }
        
        return strbuf.toString();
    }
    
    //save file config
    public String savetoFileConfig() {

        StringBuilder strbuf = new StringBuilder("");


        ConfigTag[] keys = ConfigTag.values();
        for (int i = 0; i < keys.length-1; i++) {
            ConfigTag key = keys[i];
            if (configs.containsKey(key)) {
                Pair pair = (Pair) initconfigs.get(key);
                if (pair.second instanceof String) {
                    strbuf.append(key).append(":").append(preferences.get(key.name(), (String) pair.second)).append("\n");
                } else if (pair.second instanceof Integer) {
                    strbuf.append(key).append(":").append(preferences.getInt(key.name(), ((Integer) pair.second).intValue())).append("\n");
                } else if (pair.second instanceof Boolean) {
                    strbuf.append(key).append(":").append(preferences.getBoolean(key.name(), ((Boolean) pair.second).booleanValue())).append("\n");
                }
            }
        }
        
        ConfigTag key = keys[keys.length - 1];
        if (configs.containsKey(key)) {
            Pair pair = (Pair) initconfigs.get(key);
            if (pair.second instanceof String) {
                strbuf.append(key).append(":").append(preferences.get(key.name(), (String) pair.second));
            } else if (pair.second instanceof Integer) {
                strbuf.append(key).append(":").append(preferences.getInt(key.name(), ((Integer) pair.second).intValue()));
            } else if (pair.second instanceof Boolean) {
                strbuf.append(key).append(":").append(preferences.getBoolean(key.name(), ((Boolean) pair.second).booleanValue()));
            }
        }

        return strbuf.toString();
    }
    
    //load file config
    public void loadtoFileConfig(String data) {

        String[] config = data.split(":");
        String configtmp = "";
        if (config.length > 1) {
            configtmp = config[1];
        }

        ConfigTag key = ConfigTag.valueOf(config[0]);

        if (initconfigs.containsKey(key)) {
            Pair pair = (Pair) initconfigs.get(key);

            if (pair.second instanceof String) {
                configs.put(key, new Pair(pair.first, configtmp));
            } else if (pair.second instanceof Integer) {
                configs.put(key, new Pair(pair.first, Integer.parseInt(configtmp)));
            } else if (pair.second instanceof Boolean) {
                configs.put(key, new Pair(pair.first, Boolean.parseBoolean(configtmp)));
            }
        }
    }
    
    public void saveBookmarks(Vector Bookmarks) {
        Collections.sort(Bookmarks);
        
        String tmp = Bookmarks.get(0).toString();
        for(int i=1;i<Bookmarks.size();i++){
            tmp += ";";
            tmp += Bookmarks.get(i).toString();
        }
        preferences.put("BOOKMARKS", tmp);

        
//        preferences.put("BOOKMARKS", Bookmarks.toString());
        try {
            preferences.flush();
        } catch(Exception e) {
            System.out.println("KaiConfig:"+e);
        }
    }
    
    public void storeWindowSizePosition(int heigth, int width, double x, double y) {
        preferences.putInt("WINDOWHEIGTH", heigth);
        preferences.putInt("WINDOWWIDTH", width);
        preferences.putInt("WINDOWX", (int)x);
        preferences.putInt("WINDOWY", (int)y);
    }
}


