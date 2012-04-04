/*
 * JKaiUI.java
 *
 * Created on November 16, 2004, 3:46 PM
 */
package pt.jkaiui;

import java.io.File;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import static pt.jkaiui.JKaiUI.Mode.*;
import static pt.jkaiui.JKaiUI.Status.CONNECTED;
import static pt.jkaiui.JKaiUI.Status.DISCONNECTED;
import pt.jkaiui.core.KaiConfig;
import static pt.jkaiui.core.KaiConfig.ConfigTag.HOST;
import static pt.jkaiui.core.KaiConfig.ConfigTag.PASSWORD;
import pt.jkaiui.core.messages.DetachEngineOut;
import pt.jkaiui.manager.ActionExecuter;
import pt.jkaiui.manager.Manager;
import pt.jkaiui.tools.log.ConfigLog;
import pt.jkaiui.ui.MainUI;
import pt.jkaiui.ui.modes.*;

/**
 *
 * @author pedro
 */
public class JKaiUI {

    //internal enum/class
    public enum Status {

        DISCONNECTED,
        CONNECTED
    }

    public enum Mode {

        MESSENGER_MODE,
        ARENA_MODE,
        DIAG_MODE;
    }

    public static class Info {

        private static final String UIName = "JKaiUI Custom";
        private static final String LongVersion = " ver.0.6.0(2012/4/2)";
        private static final String ShortVersion = "0.6.0";

        public static String getLongVersion() {
            return LongVersion;
        }

        public static String getShortVersion() {
            return ShortVersion;
        }

        public static String getUIName() {
            return UIName;
        }
    }
    
    //variable
    private static Status status = DISCONNECTED;
    private static Mode CURRENT_MODE = MESSENGER_MODE;
    private static Map<Mode, MainMode> modesMap;
    private static Set<String> ADMINISTRATORS = new HashSet<String>();
    private static Set<String> MODERATORS = new HashSet<String>();
    private static Logger _logger;
    
    private static String ARENA;
    private static String KaiEngineVersion;
    private static boolean devel = false;//true: devel verion false:normal version

    //setter and getter
    public static boolean isDevel() {
        return devel;
    }

    public static Status getStatus() {
        return status;
    }

    public static void setCURRENT_MODE(Mode CURRENT_MODE) {
        JKaiUI.CURRENT_MODE = CURRENT_MODE;
    }

    public static Mode getCURRENT_MODE() {
        return CURRENT_MODE;
    }

    public static Set<String> getADMINISTRATORS() {
        return ADMINISTRATORS;
    }

    public static Set<String> getMODERATORS() {
        return MODERATORS;
    }

    public static void setKaiEngineVersion(String version) {
        KaiEngineVersion = version;
    }

    public static String getKaiEngineVersion() {
        return KaiEngineVersion;
    }

    public static String getARENA() {
        return ARENA;
    }

    public static void setARENA(String ARENA) {
        JKaiUI.ARENA = ARENA;
    }

    public static MessengerMode getMessengerMode() {
        return (MessengerMode) modesMap.get(MESSENGER_MODE);
    }

    public static ArenaMode getArenaMode() {
        return (ArenaMode) modesMap.get(ARENA_MODE);
    }

    public static DiagMode getDiagMode() {
        return (DiagMode) modesMap.get(DIAG_MODE);
    }

    public static Map<Mode, MainMode> getModesMap() {
        return Collections.unmodifiableMap(modesMap);
    }

    //
    public JKaiUI(String[] args) {
        //switch normal, devel mode depend on argments
        if (args.length > 0 && args[0].equals("devel")) {
            devel = true;
        }

//        System.out.println(Locale.getDefault().getDisplayCountry());

        init();
    }

    private void init() {

        if (isMac()) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", Info.getUIName());
        }

        MainUI mainUI = MainUI.getInstance();

        updateStatus();

        try {

            ConfigLog.createDefaulLoggerHandlers(MainUI.getInstance().getLogEditorPane());
            _logger = ConfigLog.getLogger(this.getClass().getName());
            _logger.log(Level.CONFIG, "Log Started at {0}", new java.util.Date().toString());

        } catch (Exception e) {
            System.out.println("Failed to init log: " + e.getMessage());
        }


        // Add Modes
        try {
            addModes();
        } catch (Exception e) {
            System.out.println("JKaiUI init:" + e.getMessage());
        }

        mainUI.repaint();
        mainUI.setVisible(true);

        //setting folder
        File settingFolder = new File(KaiConfig.getInstance().getConfigSettingFolder());
        File settingSaveFolder = new File(KaiConfig.getInstance().getConfigSettingFolder() + "/setting");
        File soundFolder = new File(KaiConfig.getInstance().getConfigSettingFolder() + "/sound");
        File emoticonsFolder = new File(KaiConfig.getInstance().getConfigSettingFolder() + "/emoticons");

        try {
            if (!settingFolder.exists()) {
                settingFolder.mkdir();
            }
            if (!settingSaveFolder.exists()) {
                settingSaveFolder.mkdir();
            }
            if (!soundFolder.exists()) {
                soundFolder.mkdir();
            }
            if (!emoticonsFolder.exists()) {
                emoticonsFolder.mkdir();
            }
        } catch (Exception e) {
            System.out.println("setting folder open err:" + e);
        }

        connect();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new JKaiUI(args);
    }

    private static boolean isMac() {
        return System.getProperty("os.name").toLowerCase().startsWith("mac os x");
    }

    public void addModes() {

//        _logger.config("Creating modes");

        modesMap = new EnumMap<Mode, MainMode>(Mode.class);

        // Init ModesMap
        modesMap.put(MESSENGER_MODE, new MessengerMode());
        modesMap.put(ARENA_MODE, new ArenaMode());
        modesMap.put(DIAG_MODE, new DiagMode());

        // Show one
//        _logger.info("Selecting Messenger Mode");

        modesMap.get(MESSENGER_MODE).selectMode();
    }

    public static void resetModeName() {

        MainMode m = modesMap.get(CURRENT_MODE);

        m.setModeName(m.getName());
    }

    public static void selectMode(Object cl) {

        // Add all to UI
        for (Mode m : Mode.values()) {
            MainMode mode = modesMap.get(m);
            if (mode.getClass().getName().equals(cl.getClass().getName())) {

                mode.selectMode();

                return;
            }
        }
    }

    public static void connect() {
        if (KaiConfig.getInstance().getConfig(HOST).equals("") || KaiConfig.getInstance().getConfig(PASSWORD).equals("")) {
            boolean shouldOpenSettingsDialog;
            // Should check if the program is being ran for the first time?
            if (MainUI.getInstance().askYesNoDialog("MSG_SettingsNotConfiguredQuestion", "MSG_Error")) {
                MainUI.getInstance().openSettings();
            }
        } else {
            Manager.getInstance().connect();
        }
    }

    public static void connected() {
        status = CONNECTED;
        updateStatus();
    }

    public static void disconnect() {

        if (status != DISCONNECTED) {
            DetachEngineOut out = new DetachEngineOut();
            ActionExecuter.execute(out);
        }

    }

    public static void disconnected() {

        //Clean lists
        MessengerModeListModel model = (MessengerModeListModel) JKaiUI.getArenaMode().getListModel();
        model.clear();

        model = (MessengerModeListModel) JKaiUI.getMessengerMode().getListModel();
        model.clear();

        model = (MessengerModeListModel) JKaiUI.getDiagMode().getListModel();
        model.clear();

        MainUI.getInstance().getListModelChatUsers().clear();
        MainUI.getInstance().UpdateChatUsersQuantity();

        status = DISCONNECTED;
        updateStatus();

    }

    public static void updateStatus() {

        if (status == DISCONNECTED) {
            MainUI.getInstance().jButtonMessengerMode.setEnabled(false);
            MainUI.getInstance().jButtonArenaMode.setEnabled(false);
            MainUI.getInstance().jButtonDiagMode.setEnabled(false);
            MainUI.getInstance().SetDisConnectedStatus();

            try {
                getArenaMode().enableGoParentArena(false);
            } catch (NullPointerException e) {
                // do nothing. must be first init
                System.out.println("JKaiUI updateStatus:" + e);
            }
        } else if (status == CONNECTED) {
            MainUI.getInstance().jButtonArenaMode.setEnabled(true);
            MainUI.getInstance().jButtonDiagMode.setEnabled(true);
            MainUI.getInstance().SetConnectedStatus();
            getArenaMode().enableGoParentArena(true);
        }

    }
}
