/*
 * MainUI.java
 *
 * Created on November 16, 2004, 4:09 PM
 */

package pt.jkaiui.ui;

import java.io.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.util.*;
import java.util.logging.*;
import java.awt.*;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import javax.swing.ImageIcon;
import pt.jkaiui.core.Diags;
import pt.jkaiui.ui.modes.*;
import pt.jkaiui.ui.tools.XLinkNetworkRawStatsParser;

import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.JList;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkEvent.EventType;
import java.util.Vector;
import java.util.HashMap;
import java.net.URL;
import java.io.File;
import java.awt.Toolkit;
import java.awt.CardLayout;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import pt.jkaiui.JKaiUI;
import pt.jkaiui.core.ChatMessage;
import pt.jkaiui.core.OutMessage;
import pt.jkaiui.core.User;
import pt.jkaiui.core.messages.*;
import pt.jkaiui.core.KaiString;
import pt.jkaiui.core.Arena;
import pt.jkaiui.ui.modes.MessengerModeListModel;
import static pt.jkaiui.core.KaiConfig.ConfigTag.*;

/**
 *
 * @author  pedro
 */
public class MainUI extends javax.swing.JFrame implements WindowListener {
    
    private static final long serialVersionUID = 123413;
    private final String ARENA_URL_PREFIX = "http://www.teamxlink.co.uk/media/avatars/";
    private final ImageIcon ARENA_IMAGE_ICON = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/agame.png"));
    private final ImageIcon CONNECT_ICON = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/connect.png"));
    private final ImageIcon DISCONNECT_ICON = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/disconnect.png"));
    private final ImageIcon PRIVATE_OVERLAY = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/private_overlay.png"));
    private final ImageIcon LOCKED_OVERLAY = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/locked_overlay.png"));
    
    private static Logger _logger;
    private static PreviewMode previewMode;
    private static ResourceBundle resourceBundle;
    private static RawStatsSetter statsSetter;
    private static Hashtable avatars;
    private static Hashtable loading = new Hashtable();
    
    private String fixedphrasefile = "./setting/fixedphrese.txt";

    /** Creates new form MainUI */
    public MainUI() {
        
        // Check to see if JKaiUI is running on OSX or Windows. If so, use their
        // appropriate System Look&Feel. The default Look&Feel for Linux is the
        // GTK Theme which looks like absolute garbage and is often unstable.
        if((System.getProperty("mrj.version") != null) || System.getProperty("os.name").startsWith("Windows")) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.out.println("MainUI:"+e);
            }
        }
        
        resourceBundle = ResourceBundle.getBundle("pt/jkaiui/ui/Bundle");
        
        initComponents();
        // Init preview mode
        previewMode = new PreviewMode();
        
        jMenuItemSettings.setText(resourceBundle.getString("PROP_MenuConfig"));
        jMenuItemExit.setText(resourceBundle.getString("PROP_MenuExit"));
        jMenuItemLog.setText(resourceBundle.getString("LBL_LogWindow"));
        
        InfoPanel.setVisible(false);
        
        TitledBorder logBorder = (TitledBorder) logScrollPane.getBorder();
        logBorder.setTitle(resourceBundle.getString("LBL_LogWindow"));
        logBorder.setTitleFont(new java.awt.Font("Dialog", 0, 10));
        logScrollPane.setVisible(false);
        
        jButtonMessengerMode.setToolTipText(resourceBundle.getString("MSG_MessengerButtonTooltip"));
        jButtonArenaMode.setToolTipText(resourceBundle.getString("MSG_ArenaButtonTooltip"));
        
        jButtonMessengerMode.setToolTipText(resourceBundle.getString("MSG_MessengerButtonTooltip"));
        jButtonArenaMode.setToolTipText(resourceBundle.getString("MSG_ArenaButtonTooltip"));
        jButtonDiagMode.setToolTipText(resourceBundle.getString("MSG_DiagButtonTooltip"));
        
        addWindowListener(this);
        
        // Get Bookmarks
        String BookmarksAtStart = JKaiUI.getConfig().getConfigString(BOOKMARKS);
        if(!BookmarksAtStart.equals("") && BookmarksAtStart != null) {
            String[] sTemp = BookmarksAtStart.substring(0,BookmarksAtStart.length()).split(";");
            for (int i=0; i<sTemp.length; i++) {
                Arena tmpArena = new Arena();
                tmpArena.setUser(false);
                tmpArena.setVector(sTemp[i]);
                addBookmark(tmpArena, false);
            }
        }
        
        // Start the timer to keep the UT in the toolbar. It refreshes every 10 seconds.
        // Before start JKaiUI.getConfig() has to be called (is done at 'Get Bookmarks')
        new UniversalTimeSetter(toolbarTimeLabel).start();
        
        // Start the timer to scroll the stats at the bottom.
        if (JKaiUI.getConfig().getConfigBoolean(SHOWSERVERSTATS)) {
            statsSetter = new RawStatsSetter(toolbarRawStatsLabel);
            statsSetter.start();
        }

        if (JKaiUI.getConfig().getConfigBoolean(STOREWINDOWSIZEPOSITION)) {
            this.setSize(JKaiUI.getConfig().getConfigInt(WINDOWWIDTH), JKaiUI.getConfig().getConfigInt(WINDOWHEIGTH));
            this.setLocation(JKaiUI.getConfig().getConfigInt(WINDOWX), JKaiUI.getConfig().getConfigInt(WINDOWY));
        }
        
        buttonCopyInfo.setVisible(false);
        jTabbedPane.setComponentPopupMenu(jPopupMenuTabs);
        jPanel2.setVisible(false);
        buttonSavePhrase.setVisible(false);
        fixedphraseinit();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">
    
    //private void initComponents() {
    //    java.awt.GridBagConstraints gridBagConstraints;
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPopupMenuChatUsers = new javax.swing.JPopupMenu();
        jMenuItemUserProfile = new javax.swing.JMenuItem();
        jMenuItemAddBuddy = new javax.swing.JMenuItem();
        jMenuItemChatUser = new javax.swing.JMenuItem();
        jPopupMenuTabs = new javax.swing.JPopupMenu();
        menuClosePM = new javax.swing.JMenuItem();
        northPanel = new javax.swing.JPanel();
        modeButtonPanel = new javax.swing.JPanel();
        jButtonMessengerMode = new javax.swing.JButton();
        jButtonArenaMode = new javax.swing.JButton();
        jButtonDiagMode = new javax.swing.JButton();
        specialCommandsPanel = new javax.swing.JPanel();
        ConnectButtonPanel = new javax.swing.JPanel();
        jButtonConnectDisconnect = new javax.swing.JButton();
        westPanel = new javax.swing.JPanel();
        jPanelModes = new javax.swing.JPanel();
        InfoPanel = new pt.jkaiui.ui.InfoPanel();
        buttonCopyInfo = new javax.swing.JButton();
        southPanel = new javax.swing.JPanel();
        footerToolbar = new javax.swing.JToolBar();
        footerToolbarContainer = new javax.swing.JPanel();
        toolbarTimeLabel = new javax.swing.JLabel();
        toolbarRawStatsLabel = new javax.swing.JLabel();
        logScrollPane = new javax.swing.JScrollPane();
        jLogPane = new javax.swing.JEditorPane();
        eastPanel = new javax.swing.JPanel();
        jPanelMessengerMode = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        JListChatUsers = new javax.swing.JList();
        jPanelArenaMode = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListArenaUsers = new javax.swing.JList();
        jLabel2 = new javax.swing.JLabel();
        centerPanel = new javax.swing.JPanel();
        jTabbedPane = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        buttonSavePhrase = new javax.swing.JButton();
        buttonChangePhraseEditor = new javax.swing.JToggleButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        PhraseList = new javax.swing.JList();
        jScrollPane4 = new javax.swing.JScrollPane();
        PhraseEditorPane = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        EmotIconPane = new javax.swing.JTextPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemLog = new javax.swing.JCheckBoxMenuItem();
        jMenuItemSettings = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuItemExit = new javax.swing.JMenuItem();
        bookmarkMenu = new javax.swing.JMenu();
        versionMenu = new javax.swing.JMenu();
        menuitemVersion = new javax.swing.JMenuItem();

        jMenuItemUserProfile.setFont(new java.awt.Font("Dialog", 0, 10));
        jMenuItemUserProfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/user_profile.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle"); // NOI18N
        jMenuItemUserProfile.setText(bundle.getString("LBL_UserProfile")); // NOI18N
        jMenuItemUserProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuChatUsersActionPerformed(evt);
            }
        });
        jPopupMenuChatUsers.add(jMenuItemUserProfile);

        jMenuItemAddBuddy.setFont(new java.awt.Font("Dialog", 0, 10));
        jMenuItemAddBuddy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/add_buddy.png"))); // NOI18N
        jMenuItemAddBuddy.setText(bundle.getString("LBL_AddBuddy")); // NOI18N
        jMenuItemAddBuddy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuChatUsersActionPerformed(evt);
            }
        });
        jPopupMenuChatUsers.add(jMenuItemAddBuddy);

        jMenuItemChatUser.setFont(new java.awt.Font("Dialog", 0, 10));
        jMenuItemChatUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/chat.png"))); // NOI18N
        jMenuItemChatUser.setText(bundle.getString("LBL_OpenChat")); // NOI18N
        jMenuItemChatUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuChatUsersActionPerformed(evt);
            }
        });
        jPopupMenuChatUsers.add(jMenuItemChatUser);

        menuClosePM.setText("Close PM Panel");
        menuClosePM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuClosePMActionPerformed(evt);
            }
        });
        jPopupMenuTabs.add(menuClosePM);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle(JKaiUI.getUIName());
        setIconImage(this.getToolkit().createImage(getClass().getResource("/pt/jkaiui/ui/resources/mainui-icon.gif")));
        setName("mainFrame"); // NOI18N

        northPanel.setMinimumSize(new java.awt.Dimension(230, 41));
        northPanel.setPreferredSize(new java.awt.Dimension(230, 41));
        northPanel.setLayout(new java.awt.BorderLayout());

        modeButtonPanel.setLayout(new javax.swing.BoxLayout(modeButtonPanel, javax.swing.BoxLayout.LINE_AXIS));

        jButtonMessengerMode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/mess.png"))); // NOI18N
        jButtonMessengerMode.setBorderPainted(false);
        jButtonMessengerMode.setContentAreaFilled(false);
        jButtonMessengerMode.setEnabled(false);
        jButtonMessengerMode.setFocusable(false);
        jButtonMessengerMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMessengerModeActionPerformed(evt);
            }
        });
        jButtonMessengerMode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                messengerModeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                messengerModeMouseExited(evt);
            }
        });
        modeButtonPanel.add(jButtonMessengerMode);

        jButtonArenaMode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/arena.png"))); // NOI18N
        jButtonArenaMode.setBorderPainted(false);
        jButtonArenaMode.setContentAreaFilled(false);
        jButtonArenaMode.setEnabled(false);
        jButtonArenaMode.setFocusable(false);
        jButtonArenaMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonArenaModeActionPerformed(evt);
            }
        });
        jButtonArenaMode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                arenaModeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                arenaModeMouseExited(evt);
            }
        });
        modeButtonPanel.add(jButtonArenaMode);

        jButtonDiagMode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/diag.png"))); // NOI18N
        jButtonDiagMode.setBorderPainted(false);
        jButtonDiagMode.setContentAreaFilled(false);
        jButtonDiagMode.setEnabled(false);
        jButtonDiagMode.setFocusPainted(false);
        jButtonDiagMode.setFocusable(false);
        jButtonDiagMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDiagModeActionPerformed(evt);
            }
        });
        jButtonDiagMode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                diagModeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                diagModeMouseExited(evt);
            }
        });
        modeButtonPanel.add(jButtonDiagMode);

        specialCommandsPanel.setLayout(new javax.swing.BoxLayout(specialCommandsPanel, javax.swing.BoxLayout.LINE_AXIS));
        modeButtonPanel.add(specialCommandsPanel);

        northPanel.add(modeButtonPanel, java.awt.BorderLayout.WEST);

        ConnectButtonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        jButtonConnectDisconnect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/connect.png"))); // NOI18N
        jButtonConnectDisconnect.setBorderPainted(false);
        jButtonConnectDisconnect.setContentAreaFilled(false);
        jButtonConnectDisconnect.setFocusPainted(false);
        jButtonConnectDisconnect.setFocusable(false);
        jButtonConnectDisconnect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonConnectDisconnectMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonConnectDisconnectMouseEntered(evt);
            }
        });
        jButtonConnectDisconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConnectDisconnectActionPerformed(evt);
            }
        });
        ConnectButtonPanel.add(jButtonConnectDisconnect);

        northPanel.add(ConnectButtonPanel, java.awt.BorderLayout.EAST);

        getContentPane().add(northPanel, java.awt.BorderLayout.NORTH);

        westPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), bundle.getString("LBL_MessengerMode"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 10))); // NOI18N
        westPanel.setFocusable(false);
        westPanel.setRequestFocusEnabled(false);
        westPanel.setLayout(new java.awt.BorderLayout());

        jPanelModes.setLayout(new java.awt.BorderLayout());
        westPanel.add(jPanelModes, java.awt.BorderLayout.CENTER);
        westPanel.add(InfoPanel, java.awt.BorderLayout.SOUTH);

        buttonCopyInfo.setText("Copy Info");
        buttonCopyInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCopyInfoActionPerformed(evt);
            }
        });
        westPanel.add(buttonCopyInfo, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(westPanel, java.awt.BorderLayout.WEST);

        southPanel.setLayout(new java.awt.BorderLayout());

        footerToolbar.setFloatable(false);
        footerToolbar.setFocusable(false);

        footerToolbarContainer.setOpaque(false);
        footerToolbarContainer.setLayout(new java.awt.BorderLayout(20, 0));

        toolbarTimeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        toolbarTimeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/gmt.png"))); // NOI18N
        toolbarTimeLabel.setText(" ");
        footerToolbarContainer.add(toolbarTimeLabel, java.awt.BorderLayout.EAST);

        toolbarRawStatsLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        toolbarRawStatsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toolbarRawStatsLabelMouseClicked(evt);
            }
        });
        footerToolbarContainer.add(toolbarRawStatsLabel, java.awt.BorderLayout.CENTER);

        footerToolbar.add(footerToolbarContainer);

        southPanel.add(footerToolbar, java.awt.BorderLayout.SOUTH);

        logScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Log Window"));
        logScrollPane.setMaximumSize(new java.awt.Dimension(32767, 100));
        logScrollPane.setMinimumSize(new java.awt.Dimension(31, 100));
        logScrollPane.setPreferredSize(new java.awt.Dimension(116, 100));

        jLogPane.setContentType("text/html");
        jLogPane.setEditable(false);
        logScrollPane.setViewportView(jLogPane);

        southPanel.add(logScrollPane, java.awt.BorderLayout.NORTH);

        getContentPane().add(southPanel, java.awt.BorderLayout.SOUTH);

        eastPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), bundle.getString("LBL_ChatUsers"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 10))); // NOI18N
        eastPanel.setFocusable(false);
        eastPanel.setPreferredSize(new java.awt.Dimension(130, 144));
        eastPanel.setLayout(new java.awt.CardLayout());

        jPanelMessengerMode.setLayout(new java.awt.BorderLayout());

        JListChatUsers.setFont(new java.awt.Font("Dialog", 0, 10));
        JListChatUsers.setModel(ListModelChatUsers);
        JListChatUsers.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        JListChatUsers.setFocusable(false);
        JListChatUsers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JListChatUsersMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(JListChatUsers);

        jPanelMessengerMode.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        eastPanel.add(jPanelMessengerMode, "card1");

        jPanelArenaMode.setLayout(new java.awt.BorderLayout());

        jListArenaUsers.setFont(new java.awt.Font("Dialog", 0, 10));
        jListArenaUsers.setModel(ListModelArenaUsers);
        jScrollPane2.setViewportView(jListArenaUsers);

        jPanelArenaMode.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 10));
        jLabel2.setText("Arena Users");
        jPanelArenaMode.add(jLabel2, java.awt.BorderLayout.NORTH);

        eastPanel.add(jPanelArenaMode, "card2");

        getContentPane().add(eastPanel, java.awt.BorderLayout.EAST);

        centerPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), bundle.getString("LBL_Chats"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 10))); // NOI18N
        centerPanel.setFocusable(false);
        centerPanel.setLayout(new java.awt.BorderLayout());

        jTabbedPane.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        jTabbedPane.setFocusable(false);
        jTabbedPane.setFont(new java.awt.Font("Dialog", 0, 10));
        jTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPaneStateChanged(evt);
            }
        });
        jTabbedPane.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTabbedPaneFocusGained(evt);
            }
        });
        centerPanel.add(jTabbedPane, java.awt.BorderLayout.CENTER);

        jPanel2.setMinimumSize(new java.awt.Dimension(121, 100));
        jPanel2.setPreferredSize(new java.awt.Dimension(563, 175));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.GridBagLayout());

        buttonSavePhrase.setText("save");
        buttonSavePhrase.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonSavePhraseMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel3.add(buttonSavePhrase, gridBagConstraints);

        buttonChangePhraseEditor.setText("Edit");
        buttonChangePhraseEditor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonChangePhraseEditorMouseClicked(evt);
            }
        });
        jPanel3.add(buttonChangePhraseEditor, new java.awt.GridBagConstraints());

        jPanel2.add(jPanel3, java.awt.BorderLayout.WEST);

        jPanel5.setLayout(new java.awt.GridLayout());

        jPanel4.setLayout(new java.awt.CardLayout());

        PhraseList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "定型文です", "editボタンで編集", "saveボタンで保存できます" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        PhraseList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PhraseListMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(PhraseList);

        jPanel4.add(jScrollPane3, "card2");

        PhraseEditorPane.setColumns(20);
        PhraseEditorPane.setRows(5);
        jScrollPane4.setViewportView(PhraseEditorPane);

        jPanel4.add(jScrollPane4, "card3");

        jPanel5.add(jPanel4);

        jPanel6.setLayout(new java.awt.GridLayout());

        EmotIconPane.setContentType("text/html");
        EmotIconPane.setEditable(false);
        EmotIconPane.addHyperlinkListener(new javax.swing.event.HyperlinkListener() {
            public void hyperlinkUpdate(javax.swing.event.HyperlinkEvent evt) {
                EmotIconPaneHyperlinkUpdate(evt);
            }
        });
        jScrollPane5.setViewportView(EmotIconPane);

        jPanel6.add(jScrollPane5);

        jPanel5.add(jPanel6);

        jPanel2.add(jPanel5, java.awt.BorderLayout.CENTER);

        centerPanel.add(jPanel2, java.awt.BorderLayout.SOUTH);

        getContentPane().add(centerPanel, java.awt.BorderLayout.CENTER);

        jMenu1.setText("Menu");
        jMenu1.setFont(new java.awt.Font("Dialog", 0, 12));

        jMenuItemLog.setFont(new java.awt.Font("Dialog", 0, 12));
        jMenuItemLog.setText("Log Window");
        jMenuItemLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemLogActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemLog);

        jMenuItemSettings.setFont(new java.awt.Font("Dialog", 0, 12));
        jMenuItemSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openSettings(evt);
            }
        });
        jMenu1.add(jMenuItemSettings);
        jMenu1.add(jSeparator1);

        jMenuItemExit.setFont(new java.awt.Font("Dialog", 0, 12));
        jMenuItemExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                JMenuItemExitPressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItemExitMouseClicked(evt);
            }
        });
        jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExitActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemExit);

        jMenuBar1.add(jMenu1);

        bookmarkMenu.setText(bundle.getString("MENU_Bookmark")); // NOI18N
        bookmarkMenu.setFont(new java.awt.Font("Dialog", 0, 12));
        bookmarkMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookmarkMenuActionPerformed(evt);
            }
        });
        jMenuBar1.add(bookmarkMenu);

        versionMenu.setText("Version");
        versionMenu.setFont(new java.awt.Font("Dialog", 0, 12));

        menuitemVersion.setText("version");
        menuitemVersion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuitemVersionActionPerformed(evt);
            }
        });
        versionMenu.add(menuitemVersion);

        jMenuBar1.add(versionMenu);

        setJMenuBar(jMenuBar1);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-871)/2, (screenSize.height-657)/2, 871, 657);
    }// </editor-fold>//GEN-END:initComponents
    
    private void jButtonConnectDisconnectMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonConnectDisconnectMouseExited
        jButtonConnectDisconnect.setBorderPainted(false);
        jButtonConnectDisconnect.setContentAreaFilled(false);
    }//GEN-LAST:event_jButtonConnectDisconnectMouseExited
    
    private void jButtonConnectDisconnectMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonConnectDisconnectMouseEntered
        jButtonConnectDisconnect.setBorderPainted(true);
        jButtonConnectDisconnect.setContentAreaFilled(true);
    }//GEN-LAST:event_jButtonConnectDisconnectMouseEntered
    
    private void jButtonConnectDisconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConnectDisconnectActionPerformed
        if (JKaiUI.status == JKaiUI.CONNECTED) {
            JKaiUI.disconnect();
        } else {
            JKaiUI.connect();
        }
    }//GEN-LAST:event_jButtonConnectDisconnectActionPerformed
    
    private void openSettings(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openSettings
        // TODO: Find a good icon for settings to be displayed in the tab!
        Component[] children = jTabbedPane.getComponents();
        for (int i = 0; i < children.length; i++)
            if(children[i] instanceof KaiSettingsPanel)
                return;
        
        KaiSettingsPanel newSettingsPanel = new KaiSettingsPanel();
        jTabbedPane.addTab(ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_Config_Header"), new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/settings_tabicon.png")), newSettingsPanel);
        jTabbedPane.setSelectedComponent(newSettingsPanel);
        
    }//GEN-LAST:event_openSettings
    
    private void bookmarkMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookmarkMenuActionPerformed
    }//GEN-LAST:event_bookmarkMenuActionPerformed
        private void jMenuChatUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuChatUsersActionPerformed
            Object source = evt.getSource();
            String UserName = JListChatUsers.getSelectedValue().toString();
            if (UserName == null) {
                return;
            }
            User user = new User();
            user.setName(UserName);
            if (source == jMenuItemUserProfile) {
                JKaiUI.getManager().send(new GetUserProfile(UserName));
            } else if (source == jMenuItemChatUser) {
                JKaiUI.getChatManager().openChat(user);
            } else if (source == jMenuItemAddBuddy) {
                AddContactOut out = new AddContactOut();
                out.setUser(new KaiString(user.getUser()));
                JKaiUI.getManager().getExecuter().execute(out);
            }
    }//GEN-LAST:event_jMenuChatUsersActionPerformed
        
    private void JListChatUsersMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JListChatUsersMousePressed
        if (SwingUtilities.isRightMouseButton(evt)) {
            JListChatUsers.setSelectedIndex(JListChatUsers.locationToIndex(evt.getPoint()));
            jPopupMenuChatUsers.show(JListChatUsers, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_JListChatUsersMousePressed
    
    private void toolbarRawStatsLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toolbarRawStatsLabelMouseClicked
        statsSetter.setNextString(true);
    }//GEN-LAST:event_toolbarRawStatsLabelMouseClicked
    
    private void jMenuItemExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItemExitMouseClicked
        
    }//GEN-LAST:event_jMenuItemExitMouseClicked
    
    private void jMenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExitActionPerformed
        this.windowClosing(null);
    }//GEN-LAST:event_jMenuItemExitActionPerformed
    
	private void diagModeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_diagModeMouseExited
            //previewMode.startTimer();
            jButtonDiagMode.setBorderPainted(false);
            jButtonDiagMode.setContentAreaFilled(false);
	}//GEN-LAST:event_diagModeMouseExited
        
	private void diagModeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_diagModeMouseEntered
            //previewMode(JKaiUI.DIAG_MODE);
            if(jButtonDiagMode.isEnabled()) {
                jButtonDiagMode.setBorderPainted(true);
                jButtonDiagMode.setContentAreaFilled(true);
            }
	}//GEN-LAST:event_diagModeMouseEntered
        
	private void jButtonDiagModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDiagModeActionPerformed
            selectDiagMode();
            
            JKaiUI.getManager().send( new GetMetrics() );
            jButtonArenaMode.setEnabled(true);
            jButtonMessengerMode.setEnabled(true);
            jButtonDiagMode.setEnabled(false);
            JKaiUI.CURRENT_MODE = JKaiUI.DIAG_MODE;
            // vector must be changed if switching from messenger to diag and back to messenger mode
/*            KaiVectorOut vector = new KaiVectorOut();
            if (JKaiUI.ARENA == null)
                vector.setVector(new KaiString("Arena"));
            else
                vector.setVector(new KaiString(JKaiUI.ARENA));
            JKaiUI.getManager().getExecuter().execute(vector);
*/            
	}//GEN-LAST:event_jButtonDiagModeActionPerformed
        
    private void arenaModeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_arenaModeMouseExited
        //previewMode.startTimer();
        jButtonArenaMode.setBorderPainted(false);
        jButtonArenaMode.setContentAreaFilled(false);
    }//GEN-LAST:event_arenaModeMouseExited
    
    private void arenaModeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_arenaModeMouseEntered
        //previewMode(JKaiUI.ARENA_MODE);
        if(jButtonArenaMode.isEnabled()) {
            jButtonArenaMode.setBorderPainted(true);
            jButtonArenaMode.setContentAreaFilled(true);
        }
    }//GEN-LAST:event_arenaModeMouseEntered
    
    private void messengerModeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_messengerModeMouseExited
        previewMode.startTimer();
        jButtonMessengerMode.setBorderPainted(false);
        jButtonMessengerMode.setContentAreaFilled(false);
    }//GEN-LAST:event_messengerModeMouseExited
    
    private void messengerModeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_messengerModeMouseEntered
        if(jButtonMessengerMode.isEnabled()) {
            jButtonMessengerMode.setBorderPainted(true);
            jButtonMessengerMode.setContentAreaFilled(true);
        }
        if(JKaiUI.CURRENT_MODE == JKaiUI.ARENA_MODE){
            previewMode(JKaiUI.MESSENGER_MODE);
        }
        
    }//GEN-LAST:event_messengerModeMouseEntered
    
    private void jTabbedPaneFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTabbedPaneFocusGained
        
        if(evt.getSource() instanceof ChatPanel) {
            JTabbedPane pane = (JTabbedPane) evt.getSource();
            ChatPanel panel = (ChatPanel) pane.getSelectedComponent();
            panel.jTextFieldInput.requestFocus();
            
            JKaiUI.getChatManager().disableIcon(panel);
        }
    }//GEN-LAST:event_jTabbedPaneFocusGained
    
    private void jButtonArenaModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonArenaModeActionPerformed
        
        
        selectArenaMode();
        
        if (JKaiUI.CURRENT_MODE == JKaiUI.MESSENGER_MODE) {
            KaiVectorOut vector = new KaiVectorOut();

            if (JKaiUI.ARENA == null) {
                // Init arena mode
                vector.setVector(new KaiString("Arena"));
            } else {
                vector.setVector(new KaiString(JKaiUI.ARENA));
            }

            JKaiUI.getManager().getExecuter().execute(vector);
        }
        jButtonArenaMode.setEnabled(false);
        jButtonMessengerMode.setEnabled(true);
        jButtonDiagMode.setEnabled(true);

        JKaiUI.CURRENT_MODE = JKaiUI.ARENA_MODE;
        
    }//GEN-LAST:event_jButtonArenaModeActionPerformed
    
    private void jButtonMessengerModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMessengerModeActionPerformed
        
        selectMessengerMode();
        
        // Go back to messenger mode
        KaiVectorOut vector = new KaiVectorOut();
        vector.setVector(new KaiString(""));
        JKaiUI.getManager().getExecuter().execute(vector);
        
        jButtonArenaMode.setEnabled(true);
        jButtonMessengerMode.setEnabled(false);
        jButtonDiagMode.setEnabled(true);
        
        
        JKaiUI.CURRENT_MODE = JKaiUI.MESSENGER_MODE;
        
    }//GEN-LAST:event_jButtonMessengerModeActionPerformed
    
    private void jMenuItemLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemLogActionPerformed
        
        if(jMenuItemLog.isSelected())
            logScrollPane.setVisible(true);
        else
            logScrollPane.setVisible(false);
        
        paintAll(getGraphics());
        
    }//GEN-LAST:event_jMenuItemLogActionPerformed
    
    private void JMenuItemExitPressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JMenuItemExitPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JMenuItemExitPressed

private void menuitemVersionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuitemVersionActionPerformed
        //JOptionPane.showConfirmDialog(null, JKaiUI.getVersion()+"\nOriginal jKaiUI:http://jkaiui.sourceforge.net/downloads/\nJKaiUI Modification:https://sites.google.com/site/yuuakron/", "jKaiUI version", JOptionPane.CLOSED_OPTION);
    String objects[] = {"Close", "Copy Version"};
    
    int result = JOptionPane.showOptionDialog(
            this, 
            JKaiUI.getUIName()+JKaiUI.getVersion() + "\nOriginal jKaiUI:http://jkaiui.sourceforge.net/downloads/\n"+JKaiUI.getUIName()+":https://sites.google.com/site/yuuakron/", 
            "jKaiUI version", 
            JOptionPane.CANCEL_OPTION, 
            JOptionPane.INFORMATION_MESSAGE, 
            null, 
            objects, 
            objects[0]);
    if(result == 1){
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(JKaiUI.getVersion());
        clipboard.setContents(selection, null);
    }
}//GEN-LAST:event_menuitemVersionActionPerformed

private void buttonCopyInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCopyInfoActionPerformed

    MessengerModeListModel model = (MessengerModeListModel) JKaiUI.getDiagMode().getListModel();
        StringBuffer strbuf = new StringBuffer("Diags infomation \n\n");//保存する設定情報
        

    Diags diags = (Diags) model.get(0);
    strbuf.append("OrbServer: "+diags.getValue1() + "\n");
    diags = (Diags) model.get(1);
//    strbuf.append("Network: "+diags.getValue1()+" "+diags.getValue2() + "\n");
    strbuf.append("Network: "+diags.getValue2() + "\n");
    diags = (Diags) model.get(2);
    strbuf.append("Hardware: "+diags.getValue1()+ " " +diags.getValue2()+ "\n");
    diags = (Diags) model.get(3);
    strbuf.append("Engine: "+diags.getValue1()+ " " +diags.getValue2() + "\n");
    
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    StringSelection selection = new StringSelection(strbuf.toString());
    clipboard.setContents(selection, null);
}//GEN-LAST:event_buttonCopyInfoActionPerformed

private void menuClosePMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuClosePMActionPerformed
    ((ChatPanel)this.jTabbedPane.getSelectedComponent()).jButtonClose.doClick();
}//GEN-LAST:event_menuClosePMActionPerformed

private void jTabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPaneStateChanged
    if (jTabbedPane.getSelectedIndex() == 0) {
        menuClosePM.setVisible(false);
    }else{
        menuClosePM.setVisible(true);
    }
//    System.out.println(jTabbedPane.getSelectedIndex());
}//GEN-LAST:event_jTabbedPaneStateChanged

private void buttonSavePhraseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonSavePhraseMouseClicked
    fixedphrasesave();
}//GEN-LAST:event_buttonSavePhraseMouseClicked

private void buttonChangePhraseEditorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonChangePhraseEditorMouseClicked
    buttonSavePhrase.setVisible(!buttonSavePhrase.isVisible());
    ((CardLayout) jPanel4.getLayout()).next(jPanel4);
}//GEN-LAST:event_buttonChangePhraseEditorMouseClicked

private void PhraseListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PhraseListMouseClicked
    if (SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount() == 2) {

        JList list = (JList) evt.getComponent();
        Object obj = list.getSelectedValue();

        
        String text = (String)obj;

        if (text.length() == 0) {
            return;
        }
/*
        text = JKaiUIcommand(text);
        if (text == null) {
            return;
        }
*/        
        // Build a OutMessage and pass it to ChatManager

        // Now Im testing a few messages
        OutMessage msg = new OutMessage();

        if(!(jTabbedPane.getSelectedComponent() instanceof ChatPanel)){
            return;
        }
        
        ChatPanel tmp = (ChatPanel)jTabbedPane.getSelectedComponent();
        
        if (!tmp.isClosable()) {
            msg.setType(ChatMessage.PUBLIC_MESSAGE);
        } else {
            msg.setType(ChatMessage.PRIVATE_MESSAGE);
            msg.setUser(new User(tmp.getName()));

        }

        msg.setMessage(text);
        JKaiUI.getChatManager().processMessage(msg);
    }
}//GEN-LAST:event_PhraseListMouseClicked

private void EmotIconPaneHyperlinkUpdate(javax.swing.event.HyperlinkEvent evt) {//GEN-FIRST:event_EmotIconPaneHyperlinkUpdate

    String tooltipbuf = "";
    
    if (evt.getEventType() == EventType.ACTIVATED) {	//クリックされた時
        URL url = evt.getURL();

//        System.err.println(url.toString());
        
        if (url.toString().matches("^(https://sites.google.com/site/yuuakron/dummy/)(.*)")) {
            //絵文字をチャット入力欄にコピー
//            System.out.print("user");

            if (!(jTabbedPane.getSelectedComponent() instanceof ChatPanel)) {
                return;
            }
            
            ChatPanel tmp = (ChatPanel)jTabbedPane.getSelectedComponent();
            try {
                tmp.jTextFieldInput.getDocument().insertString(tmp.jTextFieldInput.getCaretPosition(), url.toString().replace("https://sites.google.com/site/yuuakron/dummy/", ""), null);
            } catch (Exception e) {
                System.out.println("hyperlink emoticon:" + e);
            }
        }
    } else if (evt.getEventType() == HyperlinkEvent.EventType.ENTERED) {//リンク上にきたとき
        tooltipbuf = EmotIconPane.getToolTipText();
        EmotIconPane.setToolTipText(null);
        URL url = evt.getURL();
        EmotIconPane.setToolTipText(url.toExternalForm().replace("https://sites.google.com/site/yuuakron/dummy/", "" ));
    } else if (evt.getEventType() == HyperlinkEvent.EventType.EXITED) {//リンク上から離れたとき
        EmotIconPane.setToolTipText(tooltipbuf);
    }
}//GEN-LAST:event_EmotIconPaneHyperlinkUpdate

    public void openSettings() {
        openSettings(null);
    }
    
    public ImageIcon getBookmarkIcon(Arena arena) {
        
        File iconLocation = new File(JKaiUI.getConfig().getConfigString(AVATARCACHE), arena.getVector().replace('/', File.separatorChar).toLowerCase() + ".ii");
        ImageIcon avatar = null;
        
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(iconLocation));
            avatar = (ImageIcon) ois.readObject();
            ois.close();
        } catch(Exception e) {
            System.out.println("MainUI openSetting:"+e);
        }
        
        return avatar;
    }
    
    private ImageIcon overlapGraphics(ImageIcon originalIcon, ImageIcon overIcon){
        
        // Original Image
        
        Image image = originalIcon.getImage();
        Image image2 = overIcon.getImage();
        
        BufferedImage originalImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);
        
        Graphics g = originalImage.createGraphics();
        //g.setColor(Color.WHITE);
        //g.fillRect(0,0,image.getWidth(null),image.getHeight(null));
        g.drawImage(image, 0, 0, null);
        g.drawImage(image2, 0, 0, null);
        
        g.dispose();
        
        return new ImageIcon(originalImage);
    }
    
    public void addBookmark(Arena arena) {
        addBookmark(arena, true);
    }
    
    public void addBookmark(Arena arena, boolean store) {
        final JMenuItem item;
        if(!isBookmark(arena)) {
            bookmarkVector.add(arena);
            item = new JMenuItem(arena.getName());
            item.setFont(bookmarkMenu.getFont());
            bookmarkMenu.add(item);
            if (store)
                JKaiUI.getConfig().saveBookmarks(bookmarkVector);
            item.setIcon(this.getBookmarkIcon(arena));
            item.addActionListener(new java.awt.event.ActionListener(){
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    Arena arena = new Arena();
                    for(int i=0;i<bookmarkVector.size();i++){
                        arena = (Arena)bookmarkVector.get(i);
                        if (arena.getName().equals(item.getText())){
                            if (JKaiUI.CURRENT_MODE != JKaiUI.ARENA_MODE)
                                jButtonArenaModeActionPerformed(null);
                            JKaiUI.getManager().enterArena(arena);
                            return;
                        }
                    }
                }
            });
        }
    }
    
    public boolean isBookmark(Arena arena) {
        if(bookmarkVector.isEmpty() || !bookmarkVector.contains(arena)) {
            return false;
        }
        return true;
    }
    public void deleteBookmark(Arena arena) {
        
        int index = bookmarkVector.indexOf(arena);
        if (index == -1) return;
        
        bookmarkVector.remove(index);
        JKaiUI.getConfig().saveBookmarks(bookmarkVector);
        
        for(int i=0; i < bookmarkMenu.getItemCount() ; i++) {
            if(arena.getName().equals(bookmarkMenu.getItem(i).getText())) {
                bookmarkMenu.remove(i);
                return;
            }
        }
    }
    
    public void SetConnectedStatus() {
        jButtonConnectDisconnect.setIcon(DISCONNECT_ICON);
        jButtonConnectDisconnect.setToolTipText(resourceBundle.getString("LBL_DISCONNECT"));
    }
    
    public void SetDisConnectedStatus() {
        jButtonConnectDisconnect.setIcon(CONNECT_ICON);
        jButtonConnectDisconnect.setToolTipText(resourceBundle.getString("LBL_CONNECT"));
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainUI().setVisible(true);
            }
        });
    }
    
    public javax.swing.JPanel getJPanelMode(){
        
        return jPanelModes;
        
    }
    
    public JEditorPane getLogEditorPane(){
        
        return jLogPane;
        
    }
    
    public JPanel getToolbarPanel() {
        return footerToolbarContainer;
    }
    
    
    public InfoPanel getJPanelInfos() {
        return InfoPanel;
    }
    
    public static JPanel getSpecialCommandsPanel(){
        
        return specialCommandsPanel;
    }
    
    public void windowClosing(WindowEvent e) {
        
        System.out.println("Closing.... ");
        
        if (JKaiUI.getConfig().getConfigBoolean(STOREWINDOWSIZEPOSITION)) {
            Dimension windim = new Dimension();
            Point winloc = new Point();
            windim = this.getSize();
            winloc = this.getLocation();
            JKaiUI.getConfig().storeWindowSizePosition(windim.height, windim.width, winloc.getX(), winloc.getY());
        }

        JKaiUI.disconnect();
        
        System.exit(0);
        
    }
    
    // The other implementation of WindowListener... nothing! :)
    
    public void windowClosed(WindowEvent e) {
    }
    
    public void windowOpened(WindowEvent e) {
    }
    
    public void windowIconified(WindowEvent e) {
    }
    
    public void windowDeiconified(WindowEvent e) {
    }
    
    public void windowActivated(WindowEvent e) {
    }
    
    public void windowDeactivated(WindowEvent e) {
    }
    
    /**
     * <p>This is an easier way to prompt the user with a question than writing
     * horribly long JOptionPane calls every time a question needs to be asked.</p>
     *
     * <p>To use, simply provide the resource names from the ResourceBundle for
     * the question and title.</p>
     *
     * @param questionResource A reference to the question asked from the ResourceBundle properties file.
     * @param titleResource A reference to the title of the dialog from the ResourceBundle properties file.
     * @return true if "Yes" was pressed or false if "No" was.
     */
    public boolean askYesNoDialog(String questionResource, String titleResource) {
        Object[] opts = new Object[] { resourceBundle.getString("BTN_Yes"), resourceBundle.getString("BTN_No") };
        int result = JOptionPane.showOptionDialog(this,
                resourceBundle.getString(questionResource),
                resourceBundle.getString(titleResource),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opts,
                opts[0]
                );
        return (result == 0);
    }
    
    
    public void okDialog(String messageResource) {
        JOptionPane.showConfirmDialog(this, resourceBundle.getString(messageResource));
    }
    
    
    public boolean askRetry(String messageResource, String titleResource) {
        String[] options = new String[] {
            resourceBundle.getString("BTN_Retry"), // Index 0
                    resourceBundle.getString("BTN_Cancel") // Index 1
        };
        int result = JOptionPane.showOptionDialog(
                this,
                resourceBundle.getString(messageResource),
                resourceBundle.getString(titleResource),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
                );
        return (result == 0);
    }
    
    
    public String openEnginePrompt(String[] engines) {
        if(engines.length == 0) throw new IllegalArgumentException("MainUI.openEnginePrompt() should only be given an array with more than one engine!");
        
        Object result = JOptionPane.showInputDialog(this, resourceBundle.getString("MSG_ChooseEngine"), resourceBundle.getString("MSG_ChooseEngineDialogTitle"),JOptionPane.QUESTION_MESSAGE, null, engines, engines[0]);
        
        // This may be null if Cancel was pressed! Beware!
        return (result == null)?null:(String) result;
    }
    
    
    protected void resetPreview(){
        
        showCurrentMode();
        JKaiUI.resetModeName();
        
    }
    
    
    protected  void showCurrentMode(){
        
        if (JKaiUI.CURRENT_MODE == JKaiUI.MESSENGER_MODE)
            previewMessengerMode();
        
        else if (JKaiUI.CURRENT_MODE == JKaiUI.ARENA_MODE)
            previewArenaMode();
        
        else if (JKaiUI.CURRENT_MODE == JKaiUI.DIAG_MODE)
            previewDiagMode();
    }
    
    
    protected void selectMessengerMode(){
        
        if(InfoPanel != null) InfoPanel.hidePanel();
        
        Vector modesVector = JKaiUI.getModesVector();
        for (Enumeration e = modesVector.elements(); e.hasMoreElements() ; ){
            MainMode m = (MainMode) e.nextElement();
            if ( m instanceof MessengerMode ) {
                m.selectMode();
                ListModelChatUsers.clear();
                eastPanel.setPreferredSize(new Dimension(130, 0));
                eastPanel.setVisible(true);
                CardLayout cards = (CardLayout)eastPanel.getLayout();
                cards.show(eastPanel, "card1");
            }
        }
        buttonCopyInfo.setVisible(false);
    }
    
    protected void selectArenaMode() {

        if (InfoPanel != null) {
            InfoPanel.hidePanel();
        }

        Vector modesVector = JKaiUI.getModesVector();
       
        for (Enumeration e = modesVector.elements(); e.hasMoreElements() ; ){
            MainMode m = (MainMode) e.nextElement();
            if ( m instanceof ArenaMode) {
                m.selectMode();
                eastPanel.setVisible(false);
//                ListModelArenaUsers.clear();
//                eastPanel.setPreferredSize(new Dimension(210, 0));
//                eastPanel.setVisible(true);
//                CardLayout cards = (CardLayout)eastPanel.getLayout();
//                cards.show(eastPanel, "card2");
            }
        }
        
        buttonCopyInfo.setVisible(false);
    }

    protected void selectDiagMode() {

        if (InfoPanel != null) {
            InfoPanel.hidePanel();
        }

        Vector modesVector = JKaiUI.getModesVector();
        for (Enumeration e = modesVector.elements(); e.hasMoreElements() ; ){
            MainMode m = (MainMode) e.nextElement();
            if ( m instanceof DiagMode ) {
                m.selectMode();
                eastPanel.setVisible(false);
            }
        }
        buttonCopyInfo.setVisible(true);
    }
    
    protected void previewMessengerMode(){
        
        Vector modesVector = JKaiUI.getModesVector();
        for (Enumeration e = modesVector.elements(); e.hasMoreElements() ; ){
            MainMode m = (MainMode) e.nextElement();
            if ( m instanceof MessengerMode )
                m.previewMode();
        }
    }
    
    
    protected void previewArenaMode(){
        
        Vector modesVector = JKaiUI.getModesVector();
        
        for (Enumeration e = modesVector.elements(); e.hasMoreElements() ; ){
            MainMode m = (MainMode) e.nextElement();
            if ( m instanceof ArenaMode)
                m.previewMode();
        }
        

    }
    
    protected void previewDiagMode(){
        
        Vector modesVector = JKaiUI.getModesVector();
        
        for (Enumeration e = modesVector.elements(); e.hasMoreElements() ; ){
            MainMode m = (MainMode) e.nextElement();
            if ( m instanceof DiagMode)
                m.previewMode();
        }
    }
    
    public void previewMode(int mode){
        
        // first of all, this is only relevant if we are connected
        
        if (JKaiUI.status == JKaiUI.DISCONNECTED )
            return;
        
        // lets se if its running
        if (previewMode.isRunning()){
            
            // if it is not this mode, cancel and switch
            if ( previewMode.getMode() != mode){
                
                previewMode.stop();
                
                // Only start new preview if that mode is different than the current
                
                if (mode != JKaiUI.CURRENT_MODE){
                    previewMode.setMode(mode);
                    previewMode.start();
                }
            }
            
        } else{
            
            // Only start new preview if that mode is different than the current
            
            if (mode != JKaiUI.CURRENT_MODE){
                previewMode.setMode(mode);
                previewMode.start();
            }
            
        }
        
    }
    
    private DefaultSortableListModel ListModelChatUsers = new DefaultSortableListModel();
    private DefaultSortableListModel ListModelArenaUsers = new DefaultSortableListModel();
    
    public DefaultSortableListModel getListModelChatUsers() {
        return ListModelChatUsers;
    }
    
    public DefaultSortableListModel getListModelArenaUsers() {
        return ListModelArenaUsers;
    }
    
    public void UpdateChatUsersQuantity() {
        TitledBorder brd = (TitledBorder)eastPanel.getBorder();
        brd.setTitle(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_ChatUsers") + " (" + ListModelChatUsers.size() + ")");
        eastPanel.repaint(0,0, eastPanel.WIDTH, 15);
    }
    
    public void SetModeTitle(String title) {
        TitledBorder brd = (TitledBorder)westPanel.getBorder();
        brd.setTitle(title);
        westPanel.repaint(0,0, westPanel.WIDTH, 15);
    }
    
    private class PreviewMode implements Runnable{
        
        private static final int SHOW_AND_KEEP  = 1;
        private static final int SHOW_AND_CLOSE = 2;
        private int status;
        
        private boolean isRunning = false;
        private volatile Thread blinker;
        private Logger _logger;
        
        /**
         * Holds value of property mode.
         */
        private int mode;
        
        public void start() {
            
            isRunning = true;
            status = SHOW_AND_KEEP;
            
            if(getMode() == JKaiUI.MESSENGER_MODE)
                previewMessengerMode();
            
            else if(getMode() == JKaiUI.ARENA_MODE)
                previewArenaMode();
            
            else if(getMode() == JKaiUI.DIAG_MODE)
                previewDiagMode();
            
            
            blinker = new Thread(this);
            blinker.start();
        }
        
        public void startTimer(){
            
            
            if (JKaiUI.status == JKaiUI.DISCONNECTED || blinker == null)
                return ;
            
            status = SHOW_AND_CLOSE;
            blinker.interrupt();
            
        }
        
        public void stop() {
            Thread moribund = blinker;
            blinker = null;
            moribund.interrupt();
        }
        
        
        public void run() {
            Thread thisThread = Thread.currentThread();
            while (blinker == thisThread) {
                try {
                    if (status == SHOW_AND_KEEP){
                        
                        thisThread.sleep(100000000); // wait() thows exception...
                    } else if (status == SHOW_AND_CLOSE){
                        
                        thisThread.sleep(500);
                        
                        blinker = null;
                    }
                } catch (InterruptedException e){
                    System.out.println("MainUI run:"+e);
                }
                repaint();
                
            }
            
            isRunning = false;
            resetPreview();
            
        }
        
        public boolean isRunning(){
            
            return isRunning;
            
        }
        
        /**
         * Getter for property mode.
         * @return Value of property mode.
         */
        public int getMode() {
            
            return this.mode;
        }
        
        /**
         * Setter for property mode.
         * @param mode New value of property mode.
         */
        public void setMode(int mode) {
            
            this.mode = mode;
        }
        
    }
    
    private class UniversalTimeSetter extends Thread {
        
        private static final int RETRY_ATTEMPTS = 4;
        private static final int SLEEPTIME = 10000;
        
        private String pre;
        private JLabel victim;
        private java.text.SimpleDateFormat formatter;
        private long offset;
        private Thread runner;
        private boolean isActive;
        private boolean isOSX;
        
        protected UniversalTimeSetter(JLabel label) {
            super("MainUI Toolbar Universal Time Setter Thread");
            isActive = true;
            victim = label;
            pre = resourceBundle.getString("LBL_UniversalTime") + ": ";
            formatter = new java.text.SimpleDateFormat("H:mm");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT:00"));
            
            isOSX = System.getProperty("mrj.version") != null;
        }
        
        public void run() {
            
            if(JKaiUI.getConfig().getConfigString(NTPSERVER).equals("")) {
                offset = 0;
            } else {
                long ntptime = 0;
                for(int i = 0; i < RETRY_ATTEMPTS; i++) {
                    ntptime = pt.jkaiui.ui.tools.NTPClient.getNTPTime();
                    if(ntptime != 0) break;
                }
                offset = (ntptime != 0) ? ntptime - System.currentTimeMillis() : 0;
            }
            
            while(isActive) {
                victim.setText(pre +  formatter.format(new java.util.Date(System.currentTimeMillis() + offset)) + (isOSX ? "     ":""));
                try {
                    Thread.sleep(SLEEPTIME);
                } catch(InterruptedException ie) {
                    System.out.println("MainUI run:"+ie);
                }
            }
        }
        
        public void convinceToStop() {
            isActive = false;
        }
    }
    
    private class RawStatsSetter extends Thread {
        
        //private static final int SLEEPTIME = 600000;
        private static final int SLEEPTIME = 5000;
        private static final int RETRY_ATTEMPTS = 2;
        
        private String stats;
        private JLabel victim;
        private java.text.SimpleDateFormat formatter;
        private HashMap statsHashMap;
        private int cycle1;
        private int cycle2;
        private boolean isActive;
        
        protected RawStatsSetter(JLabel label) {
            super("MainUI Toolbar Raw Network Statistics Setter Thread");
            isActive = true;
            stats = "";
            victim = label;
            cycle1 = 9;
            cycle2 = 5;
        }
        
        public void run() {
            while(isActive) {
                if(cycle1 == 9) {
                    if(++cycle2 == 6) {
                        cycle2 = 1;
                        for(int i = 0; i < RETRY_ATTEMPTS; i++) {
                            statsHashMap = XLinkNetworkRawStatsParser.getRawStatsInfo();
                            if(statsHashMap == null) {
                                convinceToStop();
                                return;
                            }
                            if(!statsHashMap.isEmpty()) break;
                        }
                    }
                }
                
                setNextString(false);
                
                try {
                    if(isActive) Thread.sleep(SLEEPTIME);
                } catch (InterruptedException ie) {
                    System.out.println("MinUI run:"+ie);
                }
            }
        }
        
        public synchronized void setNextString(boolean quick) {
            cycle1 = (cycle1 >= 9) ? 1 : cycle1 + 1;
            
            String nextKey = XLinkNetworkRawStatsParser.sequenceToName(cycle1);
            
            if(!quick) {
                for(int i = 0; i <= 225; i += 25) {
                    victim.setForeground(new Color(i,i,i));
                    try {
                        Thread.sleep(30);
                    } catch(InterruptedException ie) {
                        System.out.println("MainUI setNextString:"+ie);
                    }
                }
            }
            victim.setText("<html><body><b>" + nextKey + ":</b> " + statsHashMap.get(nextKey) + "</body></html>");
            
            if(quick) victim.setForeground(Color.BLACK);
            else {
                for(int i = 250; i >= 0; i -= 25) {
                    victim.setForeground(new Color(i,i,i));
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException ie) {
                        System.out.println("MainUI setNextString:"+ie);
                    }
                }
            }
            
        }
        
        public void convinceToStop() {
            isActive = false;
        }
    }
    
    public void clickConnectDisconnectButton(){
        jButtonConnectDisconnect.doClick();
    }
    
    public Vector bookmarkVector = new Vector();
    
        private void fixedphraseinit(){
        File PhraseFile = new File(fixedphrasefile);
        File PhraseHolder = new File(PhraseFile.getParent());

        Vector phrase = new Vector();
        PhraseEditorPane.setText("");
        
        if (!PhraseHolder.exists()) {
            return;
        }
        if (!PhraseFile.exists()) {
            return;
        }
        
        try {
            if (PhraseFile.isFile() && PhraseFile.canRead()) {

                BufferedReader logfilebr = new BufferedReader(new FileReader(PhraseFile));
                
                String line;
                while ((line = logfilebr.readLine()) != null) {
                    phrase.add(line);
                    PhraseEditorPane.append(line + "\n");
                }

                logfilebr.close();
            }
        } catch (Exception e) {
            System.out.println("fixedphraseinit:" + e);
        }
        
        PhraseList.setListData(phrase);
    }
    
    private void fixedphrasesave() {
        File PhraseFile = new File(fixedphrasefile);
        File PhraseHolder = new File(PhraseFile.getParent());
        PrintWriter phrasefilepw;

        try {
            if (!PhraseHolder.exists()) {
                PhraseHolder.mkdir();
            }
            if (!PhraseFile.exists()) {
                PhraseFile.createNewFile();
            }

            if (PhraseFile.isFile() && PhraseFile.canWrite()) {
                //バッファを自動でフラッシュ
                phrasefilepw = new PrintWriter(new BufferedWriter(new FileWriter(PhraseFile)), true);
                
                phrasefilepw.print(PhraseEditorPane.getText());
                
                phrasefilepw.close();
                
                fixedphraseinit();
            }
        } catch (Exception e) {
            System.out.println("fileopen err:" + e);
        }    
    }
    
    public void initEmotIconPane(){
        
        StringBuffer s = new StringBuffer("");
            
        //JKaiUI
//        String out = "<table style=\"padding:2px;width:100%;font-family:Dialog;"+ wordbreak +"font-size:"+JKaiUI.getConfig().getChatFontSize()+"px\"><tr style=\"background-color:" + color + "\"><td>" + user + "</td><td align=\"right\">";
//        out += "</td></tr><tr><td colspan=2>" + msgtmp + "</td></tr></table>";

        String size = " width=\"30\" height=\"30\"";
        
        ArrayList emoticons = JKaiUI.getChatManager().getEmotIconList();
        for (int i = 0; i < emoticons.size(); i++) {
    
            String[] tmp = ((String) emoticons.get(i)).split(",");
            s = s.append(createlink(tmp[0], encodeImgTag(tmp[1], size)));

        }
    
        EmotIconPane.setText(s.toString());
    }
    
    public void resetEmotIconPane(){
        EmotIconPane.setText("");
    }
    
    //dummyアドレスを持ったリンクを作成
    private String createlink(String address, String v) {
        //userにリンクを追加　リンクをクリックするとチャット入力画面に出せる
        //https://sites.google.com/site/yuuakron/dummy/はダミーアドレス
        return "<a href=\"https://sites.google.com/site/yuuakron/dummy/" + address + "\">" + v + "</a>";
    }
    
    private String encodeImgTag(String s, String size) {

        s = "<img src=\"" + s + "\"" + size + ">";

        return s;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ConnectButtonPanel;
    public javax.swing.JTextPane EmotIconPane;
    private pt.jkaiui.ui.InfoPanel InfoPanel;
    private javax.swing.JList JListChatUsers;
    public javax.swing.JTextArea PhraseEditorPane;
    public javax.swing.JList PhraseList;
    private javax.swing.JMenu bookmarkMenu;
    private javax.swing.JToggleButton buttonChangePhraseEditor;
    private javax.swing.JButton buttonCopyInfo;
    private javax.swing.JButton buttonSavePhrase;
    private javax.swing.JPanel centerPanel;
    private javax.swing.JPanel eastPanel;
    private javax.swing.JToolBar footerToolbar;
    private javax.swing.JPanel footerToolbarContainer;
    public javax.swing.JButton jButtonArenaMode;
    public javax.swing.JButton jButtonConnectDisconnect;
    public javax.swing.JButton jButtonDiagMode;
    public javax.swing.JButton jButtonMessengerMode;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList jListArenaUsers;
    private javax.swing.JEditorPane jLogPane;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemAddBuddy;
    private javax.swing.JMenuItem jMenuItemChatUser;
    private javax.swing.JMenuItem jMenuItemExit;
    public javax.swing.JCheckBoxMenuItem jMenuItemLog;
    public javax.swing.JMenuItem jMenuItemSettings;
    private javax.swing.JMenuItem jMenuItemUserProfile;
    public javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanelArenaMode;
    private javax.swing.JPanel jPanelMessengerMode;
    private javax.swing.JPanel jPanelModes;
    private javax.swing.JPopupMenu jPopupMenuChatUsers;
    private javax.swing.JPopupMenu jPopupMenuTabs;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    public javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JScrollPane logScrollPane;
    private javax.swing.JMenuItem menuClosePM;
    private javax.swing.JMenuItem menuitemVersion;
    private javax.swing.JPanel modeButtonPanel;
    private javax.swing.JPanel northPanel;
    private javax.swing.JPanel southPanel;
    private static javax.swing.JPanel specialCommandsPanel;
    private javax.swing.JLabel toolbarRawStatsLabel;
    private javax.swing.JLabel toolbarTimeLabel;
    private javax.swing.JMenu versionMenu;
    private javax.swing.JPanel westPanel;
    // End of variables declaration//GEN-END:variables
}
