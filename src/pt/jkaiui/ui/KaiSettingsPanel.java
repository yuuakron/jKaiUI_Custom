/*
 * KaiSettingsPanel.java
 *
 * Created on July 2, 2005, 11:05 AM
 */
package pt.jkaiui.ui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Enumeration;
import java.util.logging.Logger;
import javax.swing.*;
import pt.jkaiui.JKaiUI;
import pt.jkaiui.core.KaiConfig;
import pt.jkaiui.tools.log.ConfigLog;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import static pt.jkaiui.core.KaiConfig.ConfigTag.*;

/**
 *
 * @author  jicksta
 */
public class KaiSettingsPanel extends javax.swing.JPanel {

    private static Logger _logger;
    private KaiConfig kaiConfig;

    /** Creates new form KaiSettingsPanel */
    public KaiSettingsPanel() {
        initComponents();

        kaiConfig = JKaiUI.getConfig();

        ActionListener radioListener = new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                ((CardLayout) panelMainContent.getLayout()).show(panelMainContent, evt.getActionCommand());
                choiceSelectCategory.setSelected(((AbstractButton) evt.getSource()).getModel(), true);
            }
        };
        Enumeration radios = choiceSelectCategory.getElements();
        while (radios.hasMoreElements()) {
            ((AbstractButton) radios.nextElement()).addActionListener(radioListener);
        }


        _logger = ConfigLog.getLogger(this.getClass().getName());

        resetValues();
        
        //配布バージョン用
        if (!(JKaiUI.develflag)) {
            radioAllLogger.setVisible(false);
            radioUserLogger.setVisible(false);
            radioRoomLogger.setVisible(false);
            radioFriendLogger.setVisible(false);
            radioMACLogger.setVisible(false);
            fieldAllLog.setVisible(false);
            fieldUserLog.setVisible(false);
            fieldRoomLog.setVisible(false);
            fieldFriendLog.setVisible(false);
            fieldMACLog.setVisible(false);
            fieldAllLogPattern.setVisible(false);
            fieldUserLogPattern.setVisible(false);
            fieldRoomLogPattern.setVisible(false);
            fieldFriendLogPattern.setVisible(false);
            fieldMACLogPattern.setVisible(false);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        choiceEngineConnection = new javax.swing.ButtonGroup();
        choiceSelectCategory = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        choiceChatStyle = new javax.swing.ButtonGroup();
        jLabelHeader = new javax.swing.JLabel();
        scrollerPane = new javax.swing.JScrollPane();
        panelMainContent = new javax.swing.JPanel();
        panelConnection = new javax.swing.JPanel();
        panelEngineConnection = new javax.swing.JPanel();
        panelEngineSettings = new javax.swing.JPanel();
        labelEngineAddress = new javax.swing.JLabel();
        labelEnginePort = new javax.swing.JLabel();
        fieldEngineAddress = new javax.swing.JTextField();
        fieldEnginePort = new javax.swing.JTextField();
        radioAutomaticConnection = new javax.swing.JRadioButton();
        radioManualConnection = new javax.swing.JRadioButton();
        panelLogin = new javax.swing.JPanel();
        fieldPassword = new javax.swing.JPasswordField();
        fieldXTag = new javax.swing.JTextField();
        labelXTag = new javax.swing.JLabel();
        labelPassword = new javax.swing.JLabel();
        panelAppearance = new javax.swing.JPanel();
        panelIcons = new javax.swing.JPanel();
        checkboxShowIcons = new javax.swing.JCheckBox();
        spinnerCacheTime = new javax.swing.JSpinner();
        labelCacheTime = new javax.swing.JLabel();
        buttonClearCache = new javax.swing.JButton();
        panelLookAndFeel = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        panelMiscellaneous = new javax.swing.JPanel();
        choiceShowTimestamps = new javax.swing.JCheckBox();
        fieldNTPServer = new javax.swing.JTextField();
        labelNTPServer = new javax.swing.JLabel();
        choiceStoreWindowPosition = new javax.swing.JCheckBox();
        choiceShowServerStats = new javax.swing.JCheckBox();
        choicePlayMessageSound = new javax.swing.JCheckBox();
        panelOriginal = new javax.swing.JPanel();
        panelChatPM = new javax.swing.JPanel();
        checkShowLatestChat = new javax.swing.JCheckBox();
        checkHideServerMessage = new javax.swing.JCheckBox();
        checkShowFriendsLoginInfo = new javax.swing.JCheckBox();
        checkPaintColor = new javax.swing.JCheckBox();
        checkShowImageMouseover = new javax.swing.JCheckBox();
        checkChatWrap = new javax.swing.JCheckBox();
        checkCUICommand = new javax.swing.JCheckBox();
        checkAskCommand = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        panelChatFont = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        spinnerChatFontSize = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        spinnerSystemFontSize = new javax.swing.JSpinner();
        spinnerInputFieldFontSize = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        radioJKaiUIStyle = new javax.swing.JRadioButton();
        radioGUIStyle = new javax.swing.JRadioButton();
        radioWebUIStyle = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        spinnermaxChatHistory = new javax.swing.JSpinner();
        panelRoomUser = new javax.swing.JPanel();
        choiceURLDecode = new javax.swing.JCheckBox();
        panelRoomFont = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        spinnerRoomFontSize = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        panelAutoSetting = new javax.swing.JPanel();
        checkAutoHostSetting = new javax.swing.JCheckBox();
        checkAutoArenaMoving = new javax.swing.JCheckBox();
        panelGeneral = new javax.swing.JPanel();
        fieldSettingFolder = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        panelLogger = new javax.swing.JPanel();
        radioAllLogger = new javax.swing.JRadioButton();
        radioChatLogger = new javax.swing.JRadioButton();
        radioUserLogger = new javax.swing.JRadioButton();
        radioRoomLogger = new javax.swing.JRadioButton();
        radioFriendLogger = new javax.swing.JRadioButton();
        radioMACLogger = new javax.swing.JRadioButton();
        labelLogdirectory = new javax.swing.JLabel();
        fieldAllLog = new javax.swing.JTextField();
        fieldChatLog = new javax.swing.JTextField();
        fieldUserLog = new javax.swing.JTextField();
        fieldRoomLog = new javax.swing.JTextField();
        fieldFriendLog = new javax.swing.JTextField();
        fieldMACLog = new javax.swing.JTextField();
        labelLogPattern = new javax.swing.JLabel();
        fieldChatLogPattern = new javax.swing.JTextField();
        fieldUserLogPattern = new javax.swing.JTextField();
        fieldRoomLogPattern = new javax.swing.JTextField();
        fieldFriendLogPattern = new javax.swing.JTextField();
        fieldAllLogPattern = new javax.swing.JTextField();
        fieldMACLogPattern = new javax.swing.JTextField();
        panelSounds = new javax.swing.JPanel();
        panelSound = new javax.swing.JPanel();
        radioChat = new javax.swing.JRadioButton();
        radioPMOpen = new javax.swing.JRadioButton();
        radioFriendPM = new javax.swing.JRadioButton();
        radioFriendChat = new javax.swing.JRadioButton();
        radioFriendOnline = new javax.swing.JRadioButton();
        radioArenaPM = new javax.swing.JRadioButton();
        radioModeratorChat = new javax.swing.JRadioButton();
        radioSend = new javax.swing.JRadioButton();
        labelSoundFile = new javax.swing.JLabel();
        comboChat = new javax.swing.JComboBox();
        comboPMOpen = new javax.swing.JComboBox();
        comboFriendPM = new javax.swing.JComboBox();
        comboFriendChat = new javax.swing.JComboBox();
        comboFriendOnline = new javax.swing.JComboBox();
        comboArenaPM = new javax.swing.JComboBox();
        comboModeratorChat = new javax.swing.JComboBox();
        comboSend = new javax.swing.JComboBox();
        panelCommonFooter = new javax.swing.JPanel();
        jButtonSave = new javax.swing.JButton();
        jButtonClose = new javax.swing.JButton();
        jButtonReset = new javax.swing.JButton();
        jButtonCopySetting = new javax.swing.JButton();
        buttonSaveSettingFile = new javax.swing.JButton();
        buttonLoadSettingFile = new javax.swing.JButton();
        panelSwitchers = new javax.swing.JPanel();
        toggleConnection = new javax.swing.JToggleButton();
        toggleAppearance = new javax.swing.JToggleButton();
        toggleMisc = new javax.swing.JToggleButton();
        toggleOriginal = new javax.swing.JToggleButton();
        jToggleButton1 = new javax.swing.JToggleButton();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle"); // NOI18N
        setName(bundle.getString("LBL_Config_Header")); // NOI18N
        setLayout(new java.awt.GridBagLayout());

        jLabelHeader.setBackground(new java.awt.Color(200, 221, 242));
        jLabelHeader.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 24)); // NOI18N
        jLabelHeader.setForeground(new java.awt.Color(255, 255, 255));
        jLabelHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelHeader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/config.png"))); // NOI18N
        jLabelHeader.setText(bundle.getString("LBL_Config_Header")); // NOI18N
        jLabelHeader.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jLabelHeader.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        add(jLabelHeader, gridBagConstraints);

        scrollerPane.setBorder(null);

        panelMainContent.setLayout(new java.awt.CardLayout());

        panelConnection.setLayout(new java.awt.GridBagLayout());

        panelEngineConnection.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("LBL_PanelEngineConnection"))); // NOI18N
        panelEngineConnection.setLayout(new java.awt.GridBagLayout());

        panelEngineSettings.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("LBL_PanelEngineSettings"))); // NOI18N
        panelEngineSettings.setLayout(new java.awt.GridBagLayout());

        labelEngineAddress.setText(bundle.getString("LBL_KaidHost") + ": "); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        panelEngineSettings.add(labelEngineAddress, gridBagConstraints);

        labelEnginePort.setText(bundle.getString("LBL_KaidPort") + ": "); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
        panelEngineSettings.add(labelEnginePort, gridBagConstraints);

        fieldEngineAddress.setColumns(10);
        fieldEngineAddress.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelEngineSettings.add(fieldEngineAddress, gridBagConstraints);

        fieldEnginePort.setColumns(10);
        fieldEnginePort.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
        panelEngineSettings.add(fieldEnginePort, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 3, 3);
        panelEngineConnection.add(panelEngineSettings, gridBagConstraints);

        choiceEngineConnection.add(radioAutomaticConnection);
        radioAutomaticConnection.setSelected(true);
        radioAutomaticConnection.setText("Detect engine automatically");
        radioAutomaticConnection.setActionCommand("true");
        radioAutomaticConnection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioAutomaticConnectionActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panelEngineConnection.add(radioAutomaticConnection, gridBagConstraints);

        choiceEngineConnection.add(radioManualConnection);
        radioManualConnection.setText("Use specific engine address");
        radioManualConnection.setActionCommand("false");
        radioManualConnection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioManualConnectionActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panelEngineConnection.add(radioManualConnection, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.weightx = 1.0;
        panelConnection.add(panelEngineConnection, gridBagConstraints);

        panelLogin.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("LBL_PanelLogin"))); // NOI18N
        panelLogin.setLayout(new java.awt.GridBagLayout());

        fieldPassword.setColumns(10);
        fieldPassword.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
        panelLogin.add(fieldPassword, gridBagConstraints);

        fieldXTag.setColumns(10);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        panelLogin.add(fieldXTag, gridBagConstraints);

        labelXTag.setText(bundle.getString("LBL_Tag") + ": "); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        panelLogin.add(labelXTag, gridBagConstraints);

        labelPassword.setText(bundle.getString("LBL_Password") + ": "); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
        panelLogin.add(labelPassword, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        panelConnection.add(panelLogin, gridBagConstraints);

        panelMainContent.add(panelConnection, "LoginCard");

        panelAppearance.setLayout(new java.awt.GridBagLayout());

        panelIcons.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("LBL_PanelIconSettings"))); // NOI18N
        panelIcons.setLayout(new java.awt.GridBagLayout());

        checkboxShowIcons.setText(bundle.getString("LBL_ShowIcons")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelIcons.add(checkboxShowIcons, gridBagConstraints);

        spinnerCacheTime.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        spinnerCacheTime.setModel(new SpinnerNumberModel(3, 0, 30, 1));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 7;
        gridBagConstraints.ipady = 2;
        panelIcons.add(spinnerCacheTime, gridBagConstraints);

        labelCacheTime.setText(bundle.getString("LBL_CacheTime")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelIcons.add(labelCacheTime, gridBagConstraints);

        buttonClearCache.setText(bundle.getString("BTN_ClearCacheButton")); // NOI18N
        buttonClearCache.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonClearCacheActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        panelIcons.add(buttonClearCache, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panelAppearance.add(panelIcons, gridBagConstraints);

        panelLookAndFeel.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("LBL_PanelLookAndFeel"))); // NOI18N

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Default System Look and Feel", "Windows Look and Feel", "GTK Look and Feel", "Apple OSX Look and Feel", "Metal Look and Feel", "Motif Look and Feel" }));
        jComboBox1.setEnabled(false);
        panelLookAndFeel.add(jComboBox1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panelAppearance.add(panelLookAndFeel, gridBagConstraints);

        panelMainContent.add(panelAppearance, "AppearanceCard");

        panelMiscellaneous.setLayout(new java.awt.GridBagLayout());

        choiceShowTimestamps.setText(bundle.getString("LBL_ShowTimestamps")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelMiscellaneous.add(choiceShowTimestamps, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelMiscellaneous.add(fieldNTPServer, gridBagConstraints);

        labelNTPServer.setText(bundle.getString("LBL_NTPServer") + ": "); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        panelMiscellaneous.add(labelNTPServer, gridBagConstraints);

        choiceStoreWindowPosition.setText(bundle.getString("LBL_StoreWindowSizePosition")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelMiscellaneous.add(choiceStoreWindowPosition, gridBagConstraints);

        choiceShowServerStats.setText(bundle.getString("LBL_ShowServerStats")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelMiscellaneous.add(choiceShowServerStats, gridBagConstraints);

        choicePlayMessageSound.setText(bundle.getString("LBL_PlayMessageSound")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelMiscellaneous.add(choicePlayMessageSound, gridBagConstraints);

        panelMainContent.add(panelMiscellaneous, "MiscellaneousCard");

        panelOriginal.setLayout(new java.awt.GridBagLayout());

        panelChatPM.setBorder(javax.swing.BorderFactory.createTitledBorder("Chat/PM"));
        panelChatPM.setLayout(new java.awt.GridBagLayout());

        checkShowLatestChat.setSelected(true);
        checkShowLatestChat.setText("Show the latest chat");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelChatPM.add(checkShowLatestChat, gridBagConstraints);

        checkHideServerMessage.setText("Hide Server Messages");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelChatPM.add(checkHideServerMessage, gridBagConstraints);

        checkShowFriendsLoginInfo.setText("Show a friends login info");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelChatPM.add(checkShowFriendsLoginInfo, gridBagConstraints);

        checkPaintColor.setSelected(true);
        checkPaintColor.setText("Color in the Background");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelChatPM.add(checkPaintColor, gridBagConstraints);

        checkShowImageMouseover.setText("Show Image with Mouse over Link");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelChatPM.add(checkShowImageMouseover, gridBagConstraints);

        checkChatWrap.setText("Chat Wrap");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelChatPM.add(checkChatWrap, gridBagConstraints);

        checkCUICommand.setSelected(true);
        checkCUICommand.setText("Enable CUI Command");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelChatPM.add(checkCUICommand, gridBagConstraints);

        checkAskCommand.setText("Enable Ask Command");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelChatPM.add(checkAskCommand, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        panelChatFont.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Font Size"));
        panelChatFont.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Main Chat");
        panelChatFont.add(jLabel1, new java.awt.GridBagConstraints());

        spinnerChatFontSize.setModel(new javax.swing.SpinnerNumberModel(12, 0, 256, 1));
        panelChatFont.add(spinnerChatFontSize, new java.awt.GridBagConstraints());

        jLabel3.setText("px");
        panelChatFont.add(jLabel3, new java.awt.GridBagConstraints());

        jLabel4.setText("px");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        panelChatFont.add(jLabel4, gridBagConstraints);

        jLabel2.setText("System");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panelChatFont.add(jLabel2, gridBagConstraints);

        jLabel7.setText("Input Field");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        panelChatFont.add(jLabel7, gridBagConstraints);

        spinnerSystemFontSize.setModel(new javax.swing.SpinnerNumberModel(10, 0, 256, 1));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        panelChatFont.add(spinnerSystemFontSize, gridBagConstraints);

        spinnerInputFieldFontSize.setModel(new javax.swing.SpinnerNumberModel(12, 0, 256, 1));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        panelChatFont.add(spinnerInputFieldFontSize, gridBagConstraints);

        jLabel8.setText("px");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        panelChatFont.add(jLabel8, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(panelChatFont, gridBagConstraints);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Chat Display Style"));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        choiceChatStyle.add(radioJKaiUIStyle);
        radioJKaiUIStyle.setSelected(true);
        radioJKaiUIStyle.setText("JKaiUI");
        radioJKaiUIStyle.setActionCommand("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(radioJKaiUIStyle, gridBagConstraints);

        choiceChatStyle.add(radioGUIStyle);
        radioGUIStyle.setText("Like GUI");
        radioGUIStyle.setActionCommand("1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(radioGUIStyle, gridBagConstraints);

        choiceChatStyle.add(radioWebUIStyle);
        radioWebUIStyle.setText("Like WebUI");
        radioWebUIStyle.setActionCommand("2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(radioWebUIStyle, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(jPanel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panelChatPM.add(jPanel2, gridBagConstraints);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Chat History"));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        jLabel9.setText("# to save");
        jPanel3.add(jLabel9);

        spinnermaxChatHistory.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(100), Integer.valueOf(0), null, Integer.valueOf(1)));
        jPanel3.add(spinnermaxChatHistory);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelChatPM.add(jPanel3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panelOriginal.add(panelChatPM, gridBagConstraints);

        panelRoomUser.setBorder(javax.swing.BorderFactory.createTitledBorder("Room/User"));
        panelRoomUser.setLayout(new java.awt.GridBagLayout());

        choiceURLDecode.setSelected(true);
        choiceURLDecode.setText("Decode Room Description");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        panelRoomUser.add(choiceURLDecode, gridBagConstraints);

        panelRoomFont.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Font Size"));
        panelRoomFont.setLayout(new java.awt.GridBagLayout());

        jLabel5.setText("Room/User");
        panelRoomFont.add(jLabel5, new java.awt.GridBagConstraints());

        spinnerRoomFontSize.setModel(new javax.swing.SpinnerNumberModel(10, 0, 256, 1));
        panelRoomFont.add(spinnerRoomFontSize, new java.awt.GridBagConstraints());

        jLabel6.setText("px");
        panelRoomFont.add(jLabel6, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panelRoomUser.add(panelRoomFont, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panelOriginal.add(panelRoomUser, gridBagConstraints);

        panelAutoSetting.setBorder(javax.swing.BorderFactory.createTitledBorder("AutoSetting"));
        panelAutoSetting.setLayout(new java.awt.GridBagLayout());

        checkAutoHostSetting.setText("AutoHostSetting");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelAutoSetting.add(checkAutoHostSetting, gridBagConstraints);

        checkAutoArenaMoving.setText("AutoArenaMoving");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelAutoSetting.add(checkAutoArenaMoving, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelOriginal.add(panelAutoSetting, gridBagConstraints);

        panelGeneral.setBorder(javax.swing.BorderFactory.createTitledBorder("General"));
        panelGeneral.setLayout(new java.awt.GridBagLayout());

        fieldSettingFolder.setText("default:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        panelGeneral.add(fieldSettingFolder, gridBagConstraints);

        jLabel10.setText("Settings Folder");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        panelGeneral.add(jLabel10, gridBagConstraints);

        jLabel11.setText("\"~\":Home Folder \"default:\":default");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        panelGeneral.add(jLabel11, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panelOriginal.add(panelGeneral, gridBagConstraints);

        panelLogger.setBorder(javax.swing.BorderFactory.createTitledBorder("Logging"));
        panelLogger.setLayout(new java.awt.GridBagLayout());

        radioAllLogger.setText("All");
        radioAllLogger.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                radioAllLoggerStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelLogger.add(radioAllLogger, gridBagConstraints);

        radioChatLogger.setText("Chat");
        radioChatLogger.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                radioChatLoggerStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelLogger.add(radioChatLogger, gridBagConstraints);

        radioUserLogger.setText("User");
        radioUserLogger.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                radioUserLoggerStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelLogger.add(radioUserLogger, gridBagConstraints);

        radioRoomLogger.setText("Room");
        radioRoomLogger.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                radioRoomLoggerStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelLogger.add(radioRoomLogger, gridBagConstraints);

        radioFriendLogger.setText("Friend");
        radioFriendLogger.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                radioFriendLoggerStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelLogger.add(radioFriendLogger, gridBagConstraints);

        radioMACLogger.setText("MAC");
        radioMACLogger.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                radioMACLoggerStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelLogger.add(radioMACLogger, gridBagConstraints);

        labelLogdirectory.setText("Folder of Log File");
        labelLogdirectory.setAlignmentX(0.5F);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelLogger.add(labelLogdirectory, gridBagConstraints);

        fieldAllLog.setText("log/Alllog-%Y%M%D.txt");
        fieldAllLog.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelLogger.add(fieldAllLog, gridBagConstraints);

        fieldChatLog.setText("log/Chatlog-%Y%M%D.txt");
        fieldChatLog.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelLogger.add(fieldChatLog, gridBagConstraints);

        fieldUserLog.setText("log/Userlog-%Y%M%D.txt");
        fieldUserLog.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelLogger.add(fieldUserLog, gridBagConstraints);

        fieldRoomLog.setText("log/Roomlog-%Y%M%D.txt");
        fieldRoomLog.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelLogger.add(fieldRoomLog, gridBagConstraints);

        fieldFriendLog.setText("log/Friendlog-%Y%M%D.txt");
        fieldFriendLog.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelLogger.add(fieldFriendLog, gridBagConstraints);

        fieldMACLog.setText("log/MAClog-%Y%M%D.txt");
        fieldMACLog.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelLogger.add(fieldMACLog, gridBagConstraints);

        labelLogPattern.setText("Log Pattern");
        labelLogPattern.setAlignmentX(0.5F);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelLogger.add(labelLogPattern, gridBagConstraints);

        fieldChatLogPattern.setText("%T;%K;%R;%S;%M");
        fieldChatLogPattern.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelLogger.add(fieldChatLogPattern, gridBagConstraints);

        fieldUserLogPattern.setText("%N");
        fieldUserLogPattern.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelLogger.add(fieldUserLogPattern, gridBagConstraints);

        fieldRoomLogPattern.setText("%V;%C;%S;%P;%M;%D");
        fieldRoomLogPattern.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelLogger.add(fieldRoomLogPattern, gridBagConstraints);

        fieldFriendLogPattern.setText("%T;%K;%N");
        fieldFriendLogPattern.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelLogger.add(fieldFriendLogPattern, gridBagConstraints);

        fieldAllLogPattern.setText("%T;%M");
        fieldAllLogPattern.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelLogger.add(fieldAllLogPattern, gridBagConstraints);

        fieldMACLogPattern.setText("%N;%A");
        fieldMACLogPattern.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelLogger.add(fieldMACLogPattern, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panelOriginal.add(panelLogger, gridBagConstraints);

        panelMainContent.add(panelOriginal, "OriginalCard");

        panelSounds.setLayout(new java.awt.GridBagLayout());

        panelSound.setBorder(javax.swing.BorderFactory.createTitledBorder("Sound"));
        panelSound.setLayout(new java.awt.GridBagLayout());

        radioChat.setText("Chat");
        radioChat.setActionCommand("1");
        radioChat.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelSound.add(radioChat, gridBagConstraints);

        radioPMOpen.setText("PM Open");
        radioPMOpen.setActionCommand("3");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelSound.add(radioPMOpen, gridBagConstraints);

        radioFriendPM.setText("Friend PM");
        radioFriendPM.setActionCommand("2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelSound.add(radioFriendPM, gridBagConstraints);

        radioFriendChat.setText("Friend Chat");
        radioFriendChat.setActionCommand("4");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelSound.add(radioFriendChat, gridBagConstraints);

        radioFriendOnline.setText("Friend Online");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelSound.add(radioFriendOnline, gridBagConstraints);

        radioArenaPM.setText("ArenaPM");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelSound.add(radioArenaPM, gridBagConstraints);

        radioModeratorChat.setText("Moderator Chat");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelSound.add(radioModeratorChat, gridBagConstraints);

        radioSend.setText("Send Chat");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelSound.add(radioSend, gridBagConstraints);

        labelSoundFile.setText("SoundFile");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        panelSound.add(labelSoundFile, gridBagConstraints);

        comboChat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "default" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelSound.add(comboChat, gridBagConstraints);

        comboPMOpen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "default" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelSound.add(comboPMOpen, gridBagConstraints);

        comboFriendPM.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "default" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelSound.add(comboFriendPM, gridBagConstraints);

        comboFriendChat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "default" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelSound.add(comboFriendChat, gridBagConstraints);

        comboFriendOnline.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "default" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelSound.add(comboFriendOnline, gridBagConstraints);

        comboArenaPM.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "default" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelSound.add(comboArenaPM, gridBagConstraints);

        comboModeratorChat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "default" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelSound.add(comboModeratorChat, gridBagConstraints);

        comboSend.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "default" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panelSound.add(comboSend, gridBagConstraints);

        panelSounds.add(panelSound, new java.awt.GridBagConstraints());

        panelMainContent.add(panelSounds, "SoundCard");

        scrollerPane.setViewportView(panelMainContent);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(scrollerPane, gridBagConstraints);

        jButtonSave.setText(bundle.getString("BTN_Save")); // NOI18N
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });
        panelCommonFooter.add(jButtonSave);

        jButtonClose.setText(bundle.getString("BTN_Cancel")); // NOI18N
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });
        panelCommonFooter.add(jButtonClose);

        jButtonReset.setText("Reset");
        jButtonReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetActionPerformed(evt);
            }
        });
        panelCommonFooter.add(jButtonReset);

        jButtonCopySetting.setText("Copy Setting");
        jButtonCopySetting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCopySettingActionPerformed(evt);
            }
        });
        panelCommonFooter.add(jButtonCopySetting);

        buttonSaveSettingFile.setText("Save File");
        buttonSaveSettingFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveSettingFileActionPerformed(evt);
            }
        });
        panelCommonFooter.add(buttonSaveSettingFile);

        buttonLoadSettingFile.setText("Load File");
        buttonLoadSettingFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLoadSettingFileActionPerformed(evt);
            }
        });
        panelCommonFooter.add(buttonLoadSettingFile);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        add(panelCommonFooter, gridBagConstraints);

        panelSwitchers.setLayout(new java.awt.GridBagLayout());

        choiceSelectCategory.add(toggleConnection);
        toggleConnection.setText(bundle.getString("LBL_Connection")); // NOI18N
        toggleConnection.setActionCommand("LoginCard");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        panelSwitchers.add(toggleConnection, gridBagConstraints);

        choiceSelectCategory.add(toggleAppearance);
        toggleAppearance.setText(bundle.getString("LBL_Appearance")); // NOI18N
        toggleAppearance.setActionCommand("AppearanceCard");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        panelSwitchers.add(toggleAppearance, gridBagConstraints);

        choiceSelectCategory.add(toggleMisc);
        toggleMisc.setText(bundle.getString("LBL_Miscellaneous")); // NOI18N
        toggleMisc.setActionCommand("MiscellaneousCard");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        panelSwitchers.add(toggleMisc, gridBagConstraints);

        choiceSelectCategory.add(toggleOriginal);
        toggleOriginal.setText("Original");
        toggleOriginal.setActionCommand("OriginalCard");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        panelSwitchers.add(toggleOriginal, gridBagConstraints);

        choiceSelectCategory.add(jToggleButton1);
        jToggleButton1.setText("Sound");
        jToggleButton1.setActionCommand("SoundCard");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        panelSwitchers.add(jToggleButton1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 5);
        add(panelSwitchers, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void radioAutomaticConnectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioAutomaticConnectionActionPerformed
        fieldEngineAddress.setEnabled(false);
        fieldEnginePort.setEnabled(false);
    }//GEN-LAST:event_radioAutomaticConnectionActionPerformed

    private void radioManualConnectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioManualConnectionActionPerformed
        fieldEngineAddress.setEnabled(true);
        fieldEnginePort.setEnabled(true);
    }//GEN-LAST:event_radioManualConnectionActionPerformed

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        JKaiUI.getMainUI().jTabbedPane.remove(this);
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        saveSettings();
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void buttonClearCacheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonClearCacheActionPerformed
        String s = File.separator;
        File cacheLocation = new File(kaiConfig.getConfigString(AVATARCACHE));
        if (cacheLocation.exists()) {
            boolean result = cacheLocation.delete();
            _logger.fine("Cache clear was " + (result ? "successful" : "unsuccessful"));
            if (result) {
                buttonClearCache.setEnabled(false);
            }
        }
    }//GEN-LAST:event_buttonClearCacheActionPerformed

    private void radioFriendLoggerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_radioFriendLoggerStateChanged
        fieldFriendLog.setEnabled(!fieldFriendLog.isEnabled());
        fieldFriendLogPattern.setEnabled(!fieldFriendLogPattern.isEnabled());
    }//GEN-LAST:event_radioFriendLoggerStateChanged

    private void radioRoomLoggerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_radioRoomLoggerStateChanged
        fieldRoomLog.setEnabled(!fieldRoomLog.isEnabled());
        fieldRoomLogPattern.setEnabled(!fieldRoomLogPattern.isEnabled());
    }//GEN-LAST:event_radioRoomLoggerStateChanged

    private void radioUserLoggerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_radioUserLoggerStateChanged
        fieldUserLog.setEnabled(!fieldUserLog.isEnabled());
        fieldUserLogPattern.setEnabled(!fieldUserLogPattern.isEnabled());
    }//GEN-LAST:event_radioUserLoggerStateChanged

    private void radioChatLoggerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_radioChatLoggerStateChanged
        fieldChatLog.setEnabled(!fieldChatLog.isEnabled());
        fieldChatLogPattern.setEnabled(!fieldChatLogPattern.isEnabled());
    }//GEN-LAST:event_radioChatLoggerStateChanged

private void radioAllLoggerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_radioAllLoggerStateChanged
    fieldAllLog.setEnabled(!fieldAllLog.isEnabled());
    fieldAllLogPattern.setEnabled(!fieldAllLogPattern.isEnabled());
}//GEN-LAST:event_radioAllLoggerStateChanged

private void jButtonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetActionPerformed
    kaiConfig.resetConfig();
    resetValues();
}//GEN-LAST:event_jButtonResetActionPerformed

private void jButtonCopySettingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCopySettingActionPerformed
    kaiConfig.copytoClipboard();
}//GEN-LAST:event_jButtonCopySettingActionPerformed

private void radioMACLoggerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_radioMACLoggerStateChanged
    fieldMACLog.setEnabled(!fieldMACLog.isEnabled());
    fieldMACLogPattern.setEnabled(!fieldMACLogPattern.isEnabled());
}//GEN-LAST:event_radioMACLoggerStateChanged

private void buttonSaveSettingFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveSettingFileActionPerformed
    saveSettings();
    
    JFileChooser fc = new JFileChooser();

    fc.setFileFilter(new FileNameExtensionFilter("*.conf", "conf"));
    File cd = new File(JKaiUI.getConfig().getConfigSettingFolder()+"/setting");
    fc.setCurrentDirectory(cd);

//    File conffile = new File("");

    if (fc.showSaveDialog(panelCommonFooter) == JFileChooser.APPROVE_OPTION) {
        System.out.println("You chose to save this file: " + fc.getSelectedFile());

        File conffile = fc.getSelectedFile();
//        File PhraseHolder = new File(PhraseFile.getParent());
        PrintWriter conffilepw;

        try {
            if (!cd.exists()) {
                cd.mkdir();
            }
            if (!conffile.exists()) {
                conffile.createNewFile();
            }

            if (conffile.isFile() && conffile.canWrite()) {
                //バッファを自動でフラッシュ
                conffilepw = new PrintWriter(new BufferedWriter(new FileWriter(conffile)), true);

                conffilepw.print(JKaiUI.getConfig().savetoFileConfig());

                conffilepw.close();
            }
        } catch (Exception e) {
            System.out.println("saveconffile:" + e);
        }
    }
}//GEN-LAST:event_buttonSaveSettingFileActionPerformed

private void buttonLoadSettingFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLoadSettingFileActionPerformed
    JFileChooser fc = new JFileChooser();

    fc.setFileFilter(new FileNameExtensionFilter("*.conf", "conf"));
    File cd = new File(JKaiUI.getConfig().getConfigSettingFolder()+"/setting");
    fc.setCurrentDirectory(cd);
    
    if (fc.showOpenDialog(panelCommonFooter) == JFileChooser.APPROVE_OPTION) {
        System.out.println("You chose to open this file: " + fc.getSelectedFile());
        File conffile=fc.getSelectedFile();
        
        if (!cd.exists()) {
            return;
        }
        if (!conffile.exists()) {
            return;
        }

        try {
            if (conffile.isFile() && conffile.canRead()) {

                BufferedReader conffilebr = new BufferedReader(new FileReader(conffile));

                String line;
                while ((line = conffilebr.readLine()) != null) {
                    //phrase.add(line);
                    //PhraseEditorPane.append(line + "\n");
                    JKaiUI.getConfig().loadtoFileConfig(line);
                }

                conffilebr.close();
            }
        } catch (Exception e) {
            System.out.println("loadconffile:" + e);
        }
        JKaiUI.getConfig().saveConfig();
        resetValues();
    }
}//GEN-LAST:event_buttonLoadSettingFileActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonClearCache;
    private javax.swing.JButton buttonLoadSettingFile;
    private javax.swing.JButton buttonSaveSettingFile;
    private javax.swing.JCheckBox checkAskCommand;
    private javax.swing.JCheckBox checkAutoArenaMoving;
    private javax.swing.JCheckBox checkAutoHostSetting;
    private javax.swing.JCheckBox checkCUICommand;
    private javax.swing.JCheckBox checkChatWrap;
    private javax.swing.JCheckBox checkHideServerMessage;
    private javax.swing.JCheckBox checkPaintColor;
    private javax.swing.JCheckBox checkShowFriendsLoginInfo;
    private javax.swing.JCheckBox checkShowImageMouseover;
    private javax.swing.JCheckBox checkShowLatestChat;
    private javax.swing.JCheckBox checkboxShowIcons;
    private javax.swing.ButtonGroup choiceChatStyle;
    private javax.swing.ButtonGroup choiceEngineConnection;
    private javax.swing.JCheckBox choicePlayMessageSound;
    private javax.swing.ButtonGroup choiceSelectCategory;
    private javax.swing.JCheckBox choiceShowServerStats;
    private javax.swing.JCheckBox choiceShowTimestamps;
    private javax.swing.JCheckBox choiceStoreWindowPosition;
    private javax.swing.JCheckBox choiceURLDecode;
    private javax.swing.JComboBox comboArenaPM;
    private javax.swing.JComboBox comboChat;
    private javax.swing.JComboBox comboFriendChat;
    private javax.swing.JComboBox comboFriendOnline;
    private javax.swing.JComboBox comboFriendPM;
    private javax.swing.JComboBox comboModeratorChat;
    private javax.swing.JComboBox comboPMOpen;
    private javax.swing.JComboBox comboSend;
    private javax.swing.JTextField fieldAllLog;
    private javax.swing.JTextField fieldAllLogPattern;
    private javax.swing.JTextField fieldChatLog;
    private javax.swing.JTextField fieldChatLogPattern;
    private javax.swing.JTextField fieldEngineAddress;
    private javax.swing.JTextField fieldEnginePort;
    private javax.swing.JTextField fieldFriendLog;
    private javax.swing.JTextField fieldFriendLogPattern;
    private javax.swing.JTextField fieldMACLog;
    private javax.swing.JTextField fieldMACLogPattern;
    private javax.swing.JTextField fieldNTPServer;
    private javax.swing.JPasswordField fieldPassword;
    private javax.swing.JTextField fieldRoomLog;
    private javax.swing.JTextField fieldRoomLogPattern;
    private javax.swing.JTextField fieldSettingFolder;
    private javax.swing.JTextField fieldUserLog;
    private javax.swing.JTextField fieldUserLogPattern;
    private javax.swing.JTextField fieldXTag;
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonCopySetting;
    private javax.swing.JButton jButtonReset;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelHeader;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JLabel labelCacheTime;
    private javax.swing.JLabel labelEngineAddress;
    private javax.swing.JLabel labelEnginePort;
    private javax.swing.JLabel labelLogPattern;
    private javax.swing.JLabel labelLogdirectory;
    private javax.swing.JLabel labelNTPServer;
    private javax.swing.JLabel labelPassword;
    private javax.swing.JLabel labelSoundFile;
    private javax.swing.JLabel labelXTag;
    private javax.swing.JPanel panelAppearance;
    private javax.swing.JPanel panelAutoSetting;
    private javax.swing.JPanel panelChatFont;
    private javax.swing.JPanel panelChatPM;
    private javax.swing.JPanel panelCommonFooter;
    private javax.swing.JPanel panelConnection;
    private javax.swing.JPanel panelEngineConnection;
    private javax.swing.JPanel panelEngineSettings;
    private javax.swing.JPanel panelGeneral;
    private javax.swing.JPanel panelIcons;
    private javax.swing.JPanel panelLogger;
    private javax.swing.JPanel panelLogin;
    private javax.swing.JPanel panelLookAndFeel;
    private javax.swing.JPanel panelMainContent;
    private javax.swing.JPanel panelMiscellaneous;
    private javax.swing.JPanel panelOriginal;
    private javax.swing.JPanel panelRoomFont;
    private javax.swing.JPanel panelRoomUser;
    private javax.swing.JPanel panelSound;
    private javax.swing.JPanel panelSounds;
    private javax.swing.JPanel panelSwitchers;
    private javax.swing.JRadioButton radioAllLogger;
    private javax.swing.JRadioButton radioArenaPM;
    private javax.swing.JRadioButton radioAutomaticConnection;
    private javax.swing.JRadioButton radioChat;
    private javax.swing.JRadioButton radioChatLogger;
    private javax.swing.JRadioButton radioFriendChat;
    private javax.swing.JRadioButton radioFriendLogger;
    private javax.swing.JRadioButton radioFriendOnline;
    private javax.swing.JRadioButton radioFriendPM;
    private javax.swing.JRadioButton radioGUIStyle;
    private javax.swing.JRadioButton radioJKaiUIStyle;
    private javax.swing.JRadioButton radioMACLogger;
    private javax.swing.JRadioButton radioManualConnection;
    private javax.swing.JRadioButton radioModeratorChat;
    private javax.swing.JRadioButton radioPMOpen;
    private javax.swing.JRadioButton radioRoomLogger;
    private javax.swing.JRadioButton radioSend;
    private javax.swing.JRadioButton radioUserLogger;
    private javax.swing.JRadioButton radioWebUIStyle;
    private javax.swing.JScrollPane scrollerPane;
    private javax.swing.JSpinner spinnerCacheTime;
    private javax.swing.JSpinner spinnerChatFontSize;
    private javax.swing.JSpinner spinnerInputFieldFontSize;
    private javax.swing.JSpinner spinnerRoomFontSize;
    private javax.swing.JSpinner spinnerSystemFontSize;
    private javax.swing.JSpinner spinnermaxChatHistory;
    private javax.swing.JToggleButton toggleAppearance;
    private javax.swing.JToggleButton toggleConnection;
    private javax.swing.JToggleButton toggleMisc;
    private javax.swing.JToggleButton toggleOriginal;
    // End of variables declaration//GEN-END:variables

    public void resetValues() {

        fieldXTag.setText(kaiConfig.getConfigString(TAG)); // 0
        fieldPassword.setText(kaiConfig.getConfigString(PASSWORD)); // 1
        fieldEngineAddress.setText(kaiConfig.getConfigString(HOST)); // 2
        fieldEnginePort.setText(String.valueOf(kaiConfig.getConfigInt(PORT))); // 3
        if (kaiConfig.getConfigBoolean(AUTOMATICALLYDETECTED)) {
            choiceEngineConnection.setSelected(radioAutomaticConnection.getModel(), true);
        } else {
            choiceEngineConnection.setSelected(radioManualConnection.getModel(), true);
            fieldEngineAddress.setEnabled(true);
            fieldEnginePort.setEnabled(true);
        }
/*
        switch (kaiConfig.getPlaySoundTiming()) {
            case 1:
                choicePlaySoundTiming.setSelected(radioChat.getModel(), true);
                break;
            case 2:
                choicePlaySoundTiming.setSelected(radioFriendPM.getModel(), true);
                break;
            case 3:
                choicePlaySoundTiming.setSelected(radioPMOpen.getModel(), true);
                break;
            case 4:
                choicePlaySoundTiming.setSelected(radioFriendChat.getModel(), true);
                break;
            default:
                break;
        }
*/
        
        spinnerCacheTime.setValue(new Integer(kaiConfig.getConfigInt(CACHEDAYS)));

        String s = File.separator;
        File cacheLocation = new File(kaiConfig.getConfigString(AVATARCACHE));
        buttonClearCache.setEnabled(cacheLocation.exists());
        fieldNTPServer.setText(kaiConfig.getConfigString(NTPSERVER));
        choiceShowTimestamps.setSelected(kaiConfig.getConfigBoolean(SHOWTIMESTAMPS));
        choiceShowServerStats.setSelected(kaiConfig.getConfigBoolean(SHOWSERVERSTATS));
        choiceStoreWindowPosition.setSelected(kaiConfig.getConfigBoolean(STOREWINDOWSIZEPOSITION));
        
        choicePlayMessageSound.setSelected(kaiConfig.getConfigBoolean(PLAYMESSAGESOUND));
        
        fieldSettingFolder.setText(kaiConfig.getConfigString(SettingFolder));
        
//        choiceHtmlUnicode.setSelected(kaiConfig.getConfigBoolean("HtmlUnicode());
        choiceURLDecode.setSelected(kaiConfig.getConfigBoolean(URLDecode));
        checkAutoHostSetting.setSelected(kaiConfig.getConfigBoolean(AutoHostSetting));
        checkAutoArenaMoving.setSelected(kaiConfig.getConfigBoolean(AutoArenaMoving));
        
        radioChatLogger.setSelected(kaiConfig.getConfigBoolean(ChatLog));
        fieldChatLog.setText(kaiConfig.getConfigString(ChatLogFile));
        fieldChatLogPattern.setText(kaiConfig.getConfigString(ChatLogPattern));

        if (JKaiUI.develflag) {
            radioAllLogger.setSelected(kaiConfig.getConfigBoolean(AllLog));
            radioUserLogger.setSelected(kaiConfig.getConfigBoolean(UserLog));
            radioRoomLogger.setSelected(kaiConfig.getConfigBoolean(RoomLog));
            radioFriendLogger.setSelected(kaiConfig.getConfigBoolean(FriendLog));
            radioMACLogger.setSelected(kaiConfig.getConfigBoolean(MACLog));

            fieldAllLog.setText(kaiConfig.getConfigString(AllLogFile));
            fieldUserLog.setText(kaiConfig.getConfigString(UserLogFile));
            fieldRoomLog.setText(kaiConfig.getConfigString(RoomLogFile));
            fieldFriendLog.setText(kaiConfig.getConfigString(FriendLogFile));
            fieldMACLog.setText(kaiConfig.getConfigString(MACLogFile));

            fieldAllLogPattern.setText(kaiConfig.getConfigString(AllLogPattern));
            fieldUserLogPattern.setText(kaiConfig.getConfigString(UserLogPattern));
            fieldRoomLogPattern.setText(kaiConfig.getConfigString(RoomLogPattern));
            fieldFriendLogPattern.setText(kaiConfig.getConfigString(FriendLogPattern));
            fieldMACLogPattern.setText(kaiConfig.getConfigString(MACLogPattern));
        }
        
        checkShowLatestChat.setSelected(kaiConfig.getConfigBoolean(ShowLatestChat));
        checkShowFriendsLoginInfo.setSelected(kaiConfig.getConfigBoolean(ShowFriendLoginInfo));
        checkHideServerMessage.setSelected(kaiConfig.getConfigBoolean(HideServerMessage));
        spinnerChatFontSize.setValue(new Integer(kaiConfig.getConfigInt(ChatFontSize)));
        spinnerSystemFontSize.setValue(new Integer(kaiConfig.getConfigInt(SystemFontSize)));
        spinnerInputFieldFontSize.setValue(new Integer(kaiConfig.getConfigInt(InputFieldFontSize)));
        spinnerRoomFontSize.setValue(new Integer(kaiConfig.getConfigInt(RoomFontSize)));
        spinnermaxChatHistory.setValue(new Integer(kaiConfig.getConfigInt(MaxChatHistory)));
        checkPaintColor.setSelected(kaiConfig.getConfigBoolean(ColorBackground));
        checkChatWrap.setSelected(kaiConfig.getConfigBoolean(ChatWrap));
        checkShowImageMouseover.setSelected(kaiConfig.getConfigBoolean(ShowImageMouseoverLink));
        checkCUICommand.setSelected(kaiConfig.getConfigBoolean(CUICommand));
        checkAskCommand.setSelected(kaiConfig.getConfigBoolean(AskCommand));
        
        switch (kaiConfig.getConfigInt(ChatDisplayStyle)) {
            case 0:
                choiceChatStyle.setSelected(radioJKaiUIStyle.getModel(), true);
                break;
            case 1:
                choiceChatStyle.setSelected(radioGUIStyle.getModel(), true);
                break;
            case 2:
                choiceChatStyle.setSelected(radioWebUIStyle.getModel(), true);
                break;
            default:
                break;
        }

        radioChat.setSelected(kaiConfig.getConfigBoolean(ChatSound));
        radioPMOpen.setSelected(kaiConfig.getConfigBoolean(PMOpenSound));
        radioFriendPM.setSelected(kaiConfig.getConfigBoolean(FriendPMSound));
        radioFriendChat.setSelected(kaiConfig.getConfigBoolean(FriendChatSound));
        radioFriendOnline.setSelected(kaiConfig.getConfigBoolean(FriendOnlineSound));
        radioArenaPM.setSelected(kaiConfig.getConfigBoolean(ArenaPMSound));
        radioModeratorChat.setSelected(kaiConfig.getConfigBoolean(ModeratorChatSound));
        radioSend.setSelected(kaiConfig.getConfigBoolean(SendSound));
          
        comboChat.setSelectedItem("default");
        comboPMOpen.setSelectedItem("default");
        comboFriendPM.setSelectedItem("default");
        comboFriendChat.setSelectedItem("default");
        comboFriendOnline.setSelectedItem("default");
        comboArenaPM.setSelectedItem("default");
        comboModeratorChat.setSelectedItem("default");
        comboSend.setSelectedItem("default");
        
        File dir = new File(JKaiUI.getConfig().getConfigSettingFolder()+"/sound");
        if(dir.exists()){
            File[] soundfiles = dir.listFiles();
            for (int i = 0; i < soundfiles.length; i++) {
                comboChat.addItem(soundfiles[i].getName());
                comboPMOpen.addItem(soundfiles[i].getName());
                comboFriendPM.addItem(soundfiles[i].getName());
                comboFriendChat.addItem(soundfiles[i].getName());
                comboFriendOnline.addItem(soundfiles[i].getName());
                comboArenaPM.addItem(soundfiles[i].getName());
                comboModeratorChat.addItem(soundfiles[i].getName());
                comboSend.addItem(soundfiles[i].getName());

                if (soundfiles[i].getName().equals(kaiConfig.getConfigString(ChatSoundFile))) {
                    comboChat.setSelectedItem(kaiConfig.getConfigString(ChatSoundFile));
                }
                if (soundfiles[i].getName().equals((kaiConfig.getConfigString(PMOpenSoundFile)))) {
                    comboPMOpen.setSelectedItem(kaiConfig.getConfigString(PMOpenSoundFile));
                }
                if (soundfiles[i].getName().equals((String) (kaiConfig.getConfigString(FriendPMSoundFile)))) {
                    comboFriendPM.setSelectedItem(kaiConfig.getConfigString(FriendPMSoundFile));
                }
                if (soundfiles[i].getName().equals((String) (kaiConfig.getConfigString(FriendChatSoundFile)))) {
                    comboFriendChat.setSelectedItem(kaiConfig.getConfigString(FriendChatSoundFile));
                }
                if (soundfiles[i].getName().equals((String) (kaiConfig.getConfigString(FriendOnlineSoundFile)))) {
                    comboFriendOnline.setSelectedItem(kaiConfig.getConfigString(FriendOnlineSoundFile));
                }
                if (soundfiles[i].getName().equals((String) (kaiConfig.getConfigString(ArenaPMSoundFile)))) {
                    comboArenaPM.setSelectedItem(kaiConfig.getConfigString(ArenaPMSoundFile));
                }
                if (soundfiles[i].getName().equals((String) (kaiConfig.getConfigString(ModeratorChatSoundFile)))) {
                    comboModeratorChat.setSelectedItem(kaiConfig.getConfigString(ModeratorChatSoundFile));
                }
                if (soundfiles[i].getName().equals((String) (kaiConfig.getConfigString(SendSoundFile)))) {
                    comboSend.setSelectedItem(kaiConfig.getConfigString(SendSoundFile));
                }
            }
        }
    }

    private void saveSettings() {
        kaiConfig.setConfig(TAG, fieldXTag.getText());
        kaiConfig.setConfig(PASSWORD, fieldPassword.getText());
        kaiConfig.setConfig(CACHEDAYS,((Integer) spinnerCacheTime.getValue()).intValue());
        kaiConfig.setConfig(HOST, fieldEngineAddress.getText());
        try {
            kaiConfig.setConfig(PORT, Integer.parseInt(fieldEnginePort.getText()));
        } catch (NumberFormatException nfe) {
            // Ignore it if it's bad. Maybe should specify a number-only document for the port field?
            System.out.println("KaiSettingPanel saveSetting"+nfe);
        }

        kaiConfig.setConfig(AUTOMATICALLYDETECTED, 
                choiceEngineConnection.getSelection().getActionCommand().equals("true"));

//        kaiConfig.setPlaySoundTiming(Integer.parseInt(choicePlaySoundTiming.getSelection().getActionCommand()));

        kaiConfig.setConfig(NTPSERVER, fieldNTPServer.getText());
        kaiConfig.setConfig(SHOWTIMESTAMPS, choiceShowTimestamps.isSelected());
        kaiConfig.setConfig(SHOWSERVERSTATS, choiceShowServerStats.isSelected());
        kaiConfig.setConfig(STOREWINDOWSIZEPOSITION, choiceStoreWindowPosition.isSelected());
        kaiConfig.setConfig(PLAYMESSAGESOUND, choicePlayMessageSound.isSelected());
        
        kaiConfig.setConfig(SettingFolder, fieldSettingFolder.getText());
        
//        kaiConfig.setConfig(HtmlUnicode(choiceHtmlUnicode.isSelected());
        kaiConfig.setConfig(URLDecode, choiceURLDecode.isSelected());
        kaiConfig.setConfig(AutoHostSetting, checkAutoHostSetting.isSelected());
        kaiConfig.setConfig(AutoArenaMoving, checkAutoArenaMoving.isSelected());
        
        kaiConfig.setConfig(ChatLog, radioChatLogger.isSelected());
        kaiConfig.setConfig(ChatLogFile, fieldChatLog.getText());
        kaiConfig.setConfig(ChatLogPattern, fieldChatLogPattern.getText());

        if (JKaiUI.develflag) {
            kaiConfig.setConfig(AllLog, radioAllLogger.isSelected());
            kaiConfig.setConfig(UserLog, radioUserLogger.isSelected());
            kaiConfig.setConfig(RoomLog, radioRoomLogger.isSelected());
            kaiConfig.setConfig(FriendLog, radioFriendLogger.isSelected());
            kaiConfig.setConfig(MACLog, radioMACLogger.isSelected());

            kaiConfig.setConfig(AllLogFile, fieldAllLog.getText());
            kaiConfig.setConfig(UserLogFile, fieldUserLog.getText());
            kaiConfig.setConfig(RoomLogFile, fieldRoomLog.getText());
            kaiConfig.setConfig(FriendLogFile, fieldFriendLog.getText());
            kaiConfig.setConfig(MACLogFile, fieldMACLog.getText());

            kaiConfig.setConfig(AllLogPattern, fieldAllLogPattern.getText());
            kaiConfig.setConfig(UserLogPattern, fieldUserLogPattern.getText());
            kaiConfig.setConfig(RoomLogPattern, fieldRoomLogPattern.getText());
            kaiConfig.setConfig(FriendLogPattern, fieldFriendLogPattern.getText());
            kaiConfig.setConfig(MACLogPattern, fieldMACLogPattern.getText());
        }

        kaiConfig.setConfig(ShowLatestChat, checkShowLatestChat.isSelected());
        kaiConfig.setConfig(ShowFriendLoginInfo, checkShowFriendsLoginInfo.isSelected());
        kaiConfig.setConfig(HideServerMessage, checkHideServerMessage.isSelected());
        kaiConfig.setConfig(ChatFontSize, ((Integer)spinnerChatFontSize.getValue()).intValue());
        kaiConfig.setConfig(SystemFontSize, ((Integer)spinnerSystemFontSize.getValue()).intValue());
        kaiConfig.setConfig(RoomFontSize, ((Integer)spinnerRoomFontSize.getValue()).intValue());
        kaiConfig.setConfig(InputFieldFontSize, ((Integer)spinnerInputFieldFontSize.getValue()).intValue());
        kaiConfig.setConfig(MaxChatHistory, ((Integer)spinnermaxChatHistory.getValue()).intValue());
        kaiConfig.setConfig(ChatWrap, checkChatWrap.isSelected());
        kaiConfig.setConfig(ColorBackground, checkPaintColor.isSelected());
        kaiConfig.setConfig(ShowImageMouseoverLink, checkShowImageMouseover.isSelected());
        kaiConfig.setConfig(ChatDisplayStyle, Integer.parseInt(choiceChatStyle.getSelection().getActionCommand()));
        kaiConfig.setConfig(CUICommand, checkCUICommand.isSelected());
        kaiConfig.setConfig(AskCommand, checkAskCommand.isSelected());
        
        kaiConfig.setConfig(ChatSound, radioChat.isSelected());
        kaiConfig.setConfig(FriendPMSound, radioFriendPM.isSelected());
        kaiConfig.setConfig(FriendChatSound, radioFriendChat.isSelected());
        kaiConfig.setConfig(PMOpenSound, radioPMOpen.isSelected());
        kaiConfig.setConfig(FriendOnlineSound, radioFriendOnline.isSelected());
        kaiConfig.setConfig(ArenaPMSound, radioArenaPM.isSelected());
        kaiConfig.setConfig(ModeratorChatSound, radioModeratorChat.isSelected());
        kaiConfig.setConfig(SendSound, radioSend.isSelected());

        kaiConfig.setConfig(ChatSoundFile, (String)comboChat.getSelectedItem());
        kaiConfig.setConfig(FriendPMSoundFile, (String)comboFriendPM.getSelectedItem());
        kaiConfig.setConfig(FriendChatSoundFile, (String)comboFriendChat.getSelectedItem());
        kaiConfig.setConfig(PMOpenSoundFile, (String)comboPMOpen.getSelectedItem());
        kaiConfig.setConfig(FriendOnlineSoundFile, (String)comboFriendOnline.getSelectedItem());
        kaiConfig.setConfig(ArenaPMSoundFile, (String)comboArenaPM.getSelectedItem());
        kaiConfig.setConfig(ModeratorChatSoundFile, (String)comboModeratorChat.getSelectedItem());
        kaiConfig.setConfig(SendSoundFile, (String)comboSend.getSelectedItem());
        
        // Ensure everything's saved
        kaiConfig.saveConfig();
        JKaiUI.getLogFileManager().updateAll();
        JKaiUI.getChatManager().initSoundFile();
        JKaiUI.getArenaMode().setRoomFontSize(((Integer) spinnerRoomFontSize.getValue()).intValue());
        JKaiUI.getDiagMode().setRoomFontSize(((Integer) spinnerRoomFontSize.getValue()).intValue());
        JKaiUI.getMessengerMode().setRoomFontSize(((Integer) spinnerRoomFontSize.getValue()).intValue());
        JKaiUI.getChatManager().setInputFieldFontSize(((Integer)spinnerInputFieldFontSize.getValue()).intValue());
    }

    public boolean equals(Object other) {
        return (other != null) && (other instanceof KaiSettingsPanel);
    }
}
