/*
 * JKaiUI.java
 *
 * Created on November 16, 2004, 3:46 PM
 */

package pt.jkaiui;

import java.io.File;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import pt.jkaiui.core.KaiConfig;
import static pt.jkaiui.core.KaiConfig.ConfigTag.HOST;
import static pt.jkaiui.core.KaiConfig.ConfigTag.PASSWORD;
import pt.jkaiui.core.messages.DetachEngineOut;
import pt.jkaiui.filelog.LogFileManager;
import pt.jkaiui.manager.ChatManager;
import pt.jkaiui.manager.Manager;
import pt.jkaiui.manager.SoundManager;
import pt.jkaiui.tools.log.ConfigLog;
import pt.jkaiui.ui.MainUI;
import pt.jkaiui.ui.modes.*;

/**
 *
 * @author  pedro
 */
public class JKaiUI {
    
    public static final int DISCONNECTED = 10;
    public static final int CONNECTED = 11;
    public static int status = DISCONNECTED;
    
    public static final int MESSENGER_MODE = 0;
    public static final int ARENA_MODE = 1;
    public static final int DIAG_MODE = 2;
    public static int CURRENT_MODE = MESSENGER_MODE;
    
    private static MainUI mainUI;
    private static KaiConfig kaiConfig;
    private static Logger _logger;
    private static Vector modesVector;
    private static Manager manager;
    
    public static String ARENA;
    
    public static HashSet ADMINISTRATORS = new HashSet();
    public static HashSet MODERATORS = new HashSet();
    
    /**
     * Holds value of property chatManager.
     */
    private static ChatManager chatManager;    
    private static LogFileManager logFileManager;
    private static SoundManager soundManager;
    
    private static final String uiname = "JKaiUI Custom";
    private static final String version = " ver.0.6.0(2012/4/2)";
    private static final String version2 = "0.6.0";
    private static String KaiEngineVersion;
    public static boolean develflag = false;//true: devel verion false:normal version
    
    /** Creates a new instance of JKaiUI */
//    public JKaiUI(){
//        init();
//    }
    
    public JKaiUI(String[] args) {
        //switch normal, devel mode depend on argments
        if (args.length > 0 && args[0].equals("devel")) {
            develflag = true;
        }

        if (isMac()) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");

            System.setProperty("com.apple.mrj.application.apple.menu.about.name", JKaiUI.getUIName());
        }

//        System.out.println(Locale.getDefault().getDisplayCountry());

        init();
    }
    
    private void init() {


        mainUI = new MainUI();

        updateStatus();

        // Moved the setVisible call until after everything is loaded in MainUI's constructor.
        //mainUI.setVisible(true);

        try {

            ConfigLog.createDefaulLoggerHandlers(mainUI.getLogEditorPane());
            _logger = ConfigLog.getLogger(this.getClass().getName());
            _logger.log(Level.CONFIG, "Log Started at {0}", new java.util.Date().toString());

        } catch (Exception e) {
            System.out.println("Failed to init log: " + e.getMessage());
        }


        // Add Modes
        try {
            addModes(mainUI.getJPanelMode());
        } catch (Exception e) {
            System.out.println("JKaiUI init:" + e.getMessage());
        }

        chatManager = new ChatManager();
        soundManager = new SoundManager();
        logFileManager = new LogFileManager();

        mainUI.repaint();
        mainUI.setVisible(true);
      
        //setting folder
        File settingFolder = new File(JKaiUI.getConfig().getConfigSettingFolder());
        File settingSaveFolder = new File(JKaiUI.getConfig().getConfigSettingFolder()+"/setting");
        File soundFolder = new File(JKaiUI.getConfig().getConfigSettingFolder()+"/sound");
        File emoticonsFolder = new File(JKaiUI.getConfig().getConfigSettingFolder()+"/emoticons");
        
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
            System.out.println("setting folder open err:"+e);
        }
        
        connect();
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {       
        //javax.swing.SwingUtilities.invokeLater(new Runnable() {
        //  public void run() {
        new JKaiUI(args);
        //  }
        //});
    }
    
    private static boolean isMac() {
        return System.getProperty("os.name").toLowerCase().startsWith("mac os x");
    }
    
    /**
     * Getter for property mainUI.
     * @return Value of property mainUI.
     */
    public static MainUI getMainUI() {
        
        return mainUI;
    }
    
    /**
     * Setter for property mainUI.
     * @param mainUI New value of property mainUI.
     */
    public void setMainUI(MainUI mainUI) {
        
        this.mainUI = mainUI;
    }
    
    
    public static KaiConfig getConfig(){
        
        if( kaiConfig == null ){
            
            kaiConfig = new KaiConfig();
            kaiConfig.readConfig();
        }
        
        return kaiConfig;
        
    }
    
    
    public void addModes(JPanel panel){
        
        
        _logger.config("Creating modes");
        
        modesVector = new Vector();
        
        // Init Modesx
        MessengerMode messengerMode = new MessengerMode();
        ArenaMode arenaMode = new ArenaMode();
        DiagMode diagMode = new DiagMode();
        
        // Add to vector
        modesVector.add(messengerMode); // 0
        modesVector.add(arenaMode); // 1
        modesVector.add(diagMode); // 2
        
        // Show one
        _logger.info("Selecting Messenger Mode");
        
        ((MainMode)modesVector.get(MESSENGER_MODE)).selectMode();
        
    }
    
    
    public static void resetModeName(){
        
        MainMode m = (MainMode) modesVector.get(CURRENT_MODE);
        
        m.setModeName(m.getName());
    }
    
    public static void selectMode(Object cl){
        
        // Add all to UI
        
        for (Enumeration e = modesVector.elements(); e.hasMoreElements() ; ){
            
            MainMode m = (MainMode) e.nextElement();
            
            if ( m.getClass().getName().equals(cl.getClass().getName()) ){
                
                m.selectMode();
                
                return;
            }
        }
        
    }
    
    public static MessengerMode getMessengerMode(){
        return (MessengerMode) getModesVector().get(0);
    }
    
    public static ArenaMode getArenaMode(){
        return (ArenaMode) getModesVector().get(1);
    }
    
    public static DiagMode getDiagMode(){
        return (DiagMode) getModesVector().get(2);
    }
    
    public static Vector getModesVector(){
        
        return modesVector;
    }
    
    
    public static void connect(){
        if(kaiConfig != null && mainUI != null) {
            if(kaiConfig.getConfig(HOST).equals("") || kaiConfig.getConfig(PASSWORD).equals("")) {
                boolean shouldOpenSettingsDialog;
                // Should check if the program is being ran for the first time?
                if(mainUI.askYesNoDialog("MSG_SettingsNotConfiguredQuestion", "MSG_Error")) {
                    mainUI.openSettings();
                }
            } else {
                getManager().connect();
            }
        }
    }
    
    
    public static void connected(){
        status = CONNECTED;
        updateStatus();
    }
    
    public static Manager getManager(){
        
        if(manager == null)
            manager = new Manager();
        
        return manager;
        
    }
    
    public static void disconnect(){
        
        if(status != DISCONNECTED){
            DetachEngineOut out = new DetachEngineOut();
            JKaiUI.getManager().getExecuter().execute(out);
        }
        
    }
    
    public static void disconnected(){
        
        //Clean lists
        MessengerModeListModel model = (MessengerModeListModel) JKaiUI.getArenaMode().getListModel();
        model.clear();
        
        model = (MessengerModeListModel) JKaiUI.getMessengerMode().getListModel();
        model.clear();
        
        model = (MessengerModeListModel) JKaiUI.getDiagMode().getListModel();
        model.clear();
        
        mainUI.getListModelChatUsers().clear();
        mainUI.UpdateChatUsersQuantity();
        
        status = DISCONNECTED;
        updateStatus();
        
    }
    
    
    public static void updateStatus(){
        
        if (status == DISCONNECTED){
            mainUI.jButtonMessengerMode.setEnabled(false);
            mainUI.jButtonArenaMode.setEnabled(false);
            mainUI.jButtonDiagMode.setEnabled(false);
            mainUI.SetDisConnectedStatus();
            
            try{
                getArenaMode().enableGoParentArena(false);
            } catch (NullPointerException e){
                // do nothing. must be first init
                System.out.println("JKaiUI updateStatus:"+e);
            }
        } else if(status == CONNECTED){
            mainUI.jButtonArenaMode.setEnabled(true);
            mainUI.jButtonDiagMode.setEnabled(true);
            mainUI.SetConnectedStatus();
            getArenaMode().enableGoParentArena(true);
        }
        
    }
    
    /**
     * Getter for property chatManager.
     * @return Value of property chatManager.
     */
    public static ChatManager getChatManager() { 
        return chatManager;
    }
    
    public static LogFileManager getLogFileManager() {
        return logFileManager;
    }
    
    public static SoundManager getSoundManager(){
        return soundManager;
    }
    
    public static String getVersion(){
        return version;
    }
    
    public static String getVersion2(){
        return version2;
    }
    
    public static void setKaiEngineVersion(String version){
        KaiEngineVersion = version;
    } 
    
    public static String getUIName(){
        return uiname;
    }
    
    public static String getKaiEngineVersion(){
        return KaiEngineVersion;
    }
}
