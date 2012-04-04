/*
 * ArenaMode.java
 *
 * Created on November 22, 2004, 12:08 AM
 */

package pt.jkaiui.ui.modes;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.*;
import pt.jkaiui.JKaiUI;
import pt.jkaiui.core.*;
import pt.jkaiui.core.messages.*;
import pt.jkaiui.manager.ActionExecuter;
import pt.jkaiui.manager.ChatManager;
import pt.jkaiui.manager.Manager;
import pt.jkaiui.tools.log.ConfigLog;
import pt.jkaiui.ui.MainUI;

/**
 *
 * @author  pedro
 */
public class ArenaMode extends MainMode {
    
    private static final long serialVersionUID = 143414;
    private static final String NAME = java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_ArenaMode");
    private MessengerModeListModel listModel;
    
    // current arena
    public static Arena arena;
    
    
    private JMenuItem jmiAddBuddy;
    private JMenuItem jmiEnterArena;
    private JMenuItem jmiParentArena;
    private JMenuItem jmiBookmark;
    private JMenuItem jmiBookmarkDelete;    
    private JMenuItem jmiChat;
    private JMenuItem jmiUserProfile;
    private JMenuItem jmiBanUser;
    private JMenuItem jmiKickUser;
    private JMenuItem jmiDisconnectUser;
    
    private final ImageIcon GOBACK_ICON = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/goback.png"));
    private final ImageIcon CREATE_ARENA_ICON = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/create_arena.png"));
    private final ImageIcon PARENT_ARENA_ICON = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/parent_arena.png"));
    private final ImageIcon BOOKMARK_ARENA_ICON = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/bookmark_arena.png"));
    private final ImageIcon BOOKMARK_DELETE_ICON = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/bookmark_delete.png"));
    private final ImageIcon KICK_USER_ICON = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/kick_user.png"));
    private final ImageIcon BAN_USER_ICON = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/ban_user.png"));
    private final ImageIcon DISCONNECT_USER_ICON = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/disconnect_user.png"));
    private final ImageIcon ENTER_ARENA_ICON = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/enter_arena.png"));
    private final ImageIcon ADD_BUDDY_ICON = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/add_buddy.png"));
    private final ImageIcon USER_PROFILE_ICON = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/user_profile.png"));
    
    private Vector components = new Vector();
    
    private JButton goBackButton;
    private JButton createArenaButton;
    private JComboBox arenaModeComboBox;
    
    private final Integer[] arenaModeIntegers = {new Integer(0), new Integer(1), new Integer(2)};
    
    private final ImageIcon[] arenaModeIcons = {
        new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/arena_mode_1.png")),
                new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/arena_mode_2.png")),
                new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/arena_mode_3.png"))
    };
    
    private final String[] arenaModeText = {
        java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("MSG_ArenaMode1"),
                java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("MSG_ArenaMode2"),
                java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("MSG_ArenaMode3"),
    };
    
    /** Creates a new instance of GameMode */
    public ArenaMode() {
        
        _logger = ConfigLog.getLogger(this.getClass().getName());
        _logger.config("Initializing Game mode");
        
        // TODO: Should this hoverToggle MouseAdapter object be applied to all the
        // other UI buttons that contain this same code?
        MouseAdapter hoverToggler = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                if(me.getSource() instanceof JButton) {
                    JButton source = (JButton) me.getSource();
                    if(source.isEnabled()) {
                        source.setBorderPainted(true);
                        source.setContentAreaFilled(true);
                    }
                }
            }
            
            @Override
            public void mouseExited(MouseEvent me) {
                if(me.getSource() instanceof JButton) {
                    JButton source = (JButton) me.getSource();
                    source.setBorderPainted(false);
                    source.setContentAreaFilled(false);
                }
            }
        };
        
        this.setModeName(NAME);
        
        // Init special components
        goBackButton = new JButton(GOBACK_ICON);
        goBackButton.addActionListener(this);
        goBackButton.setBorderPainted(true);
        goBackButton.setContentAreaFilled(true);
        goBackButton.setFocusable(false);
        goBackButton.setToolTipText(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_ParentArena"));
        goBackButton.setBorderPainted(false);
        goBackButton.setContentAreaFilled(false);
        goBackButton.addMouseListener(hoverToggler);
        components.add(goBackButton);
        
        createArenaButton = new JButton(CREATE_ARENA_ICON);
        createArenaButton.addActionListener(this);
        createArenaButton.setBorderPainted(true);
        createArenaButton.setContentAreaFilled(true);
        createArenaButton.setFocusable(false);
        createArenaButton.setEnabled(false);
        createArenaButton.setToolTipText(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_CreateArena"));
        createArenaButton.setBorderPainted(false);
        createArenaButton.setContentAreaFilled(false);
        createArenaButton.addMouseListener(hoverToggler);
        components.add(createArenaButton);
        
        arenaModeComboBox = new JComboBox(arenaModeIntegers);
        arenaModeComboBox.setSelectedIndex(0);
        ComboBoxRenderer renderer = new ComboBoxRenderer();
        arenaModeComboBox.setRenderer(renderer);
        arenaModeComboBox.setSize(new Dimension(100, 32));
        arenaModeComboBox.addActionListener(this);
        
        /*
         
        // Uncomment this block and comment out the following "components.add(arenaModeComboBox);" line
        // if you want to add the arena mode combobox to the toolbar instead of the header thing.
        // (For a permanent solution to adding it to the toolbar, it'd be better to add this code to the
        // toolbar's initialization code or something.)
         
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, 10, 0, 0);
        MainUI.getInstance().getToolbarPanel().add(arenaModeComboBox, c);
         
         */
        
        components.add(arenaModeComboBox);
    }
    
    
    @Override
    public String getName(){
        return NAME;
    }
    
    @Override
    public void processMessage(Message message) throws ModeException {
        
        // Do nothing
        
    }
    
    @Override
    public ListModel getListModel() {
        if(listModel == null) {
            listModel = new MessengerModeListModel();
        }
        
        return listModel;
    }
    
    
    
    @Override
    protected JPopupMenu getPopupMenu(KaiObject obj){
        
        JPopupMenu popup = new JPopupMenu();
        
        // check for preview mode
        boolean preview = JKaiUI.getCURRENT_MODE() != JKaiUI.Mode.ARENA_MODE;
        
        // Is it an arena?
        if(obj instanceof Arena){
            
            jmiEnterArena = new JMenuItem(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_EnterArena"));
            jmiEnterArena.setIcon(ENTER_ARENA_ICON);
            if (!preview) {
                popup.add(jmiEnterArena);
            }
            jmiEnterArena.addActionListener(this);
        } else if( obj instanceof User){
            
            if (((User)obj).getUser().equals("　")) {
                return popup;
            }
            
            jmiUserProfile = new JMenuItem(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_UserProfile"));
            jmiUserProfile.setIcon(USER_PROFILE_ICON);
            popup.add(jmiUserProfile);
            jmiUserProfile.addActionListener(this);
            
            jmiAddBuddy = new JMenuItem(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_AddBuddy"));
            jmiAddBuddy.setIcon(ADD_BUDDY_ICON);
            popup.add(jmiAddBuddy);
            jmiAddBuddy.addActionListener(this);
            
            jmiChat = new JMenuItem(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_OpenChat"));
            jmiChat.setIcon(CHAT_ICON);
            
            if (!preview) {
                popup.add(jmiChat);
            }
            
            
            jmiChat.addActionListener(this);
            
            if (JKaiUI.getMODERATORS().contains(KaiConfig.getInstance().getConfigString(KaiConfig.ConfigTag.TAG))) {
                
                popup.addSeparator();
                
                jmiBanUser = new JMenuItem(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_BanUser"));
                jmiBanUser.setIcon(BAN_USER_ICON);
                popup.add(jmiBanUser);
                jmiBanUser.addActionListener(this);
                
                jmiKickUser = new JMenuItem(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_KickUser"));
                jmiKickUser.setIcon(KICK_USER_ICON);
                popup.add(jmiKickUser);
                jmiKickUser.addActionListener(this);
                
                jmiDisconnectUser = new JMenuItem(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_DisconnectUser"));
                jmiDisconnectUser.setIcon(DISCONNECT_USER_ICON);
                popup.add(jmiDisconnectUser);
                jmiDisconnectUser.addActionListener(this);
                
                popup.addSeparator();
            }
            
        }
        
        if(!preview) {
            jmiParentArena = new JMenuItem(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_ParentArena"));
            jmiParentArena.setIcon(PARENT_ARENA_ICON);
            if(JKaiUI.getARENA().equals("Arena")){
                jmiParentArena.setEnabled(false);
            } else {
                jmiParentArena.addActionListener(this);
            }
            
            popup.add(jmiParentArena);
        }
        
        Object currentlySelectedObject = list.getSelectedValue();
        if(currentlySelectedObject instanceof Arena) {
        
            jmiBookmark = new JMenuItem(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_AddBookmark"));
            jmiBookmark.setIcon(BOOKMARK_ARENA_ICON);
            jmiBookmarkDelete = new JMenuItem(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_DeleteBookmark"));
            jmiBookmarkDelete.setIcon(BOOKMARK_DELETE_ICON);

            if(MainUI.getInstance().isBookmark((Arena) currentlySelectedObject)) {
                  jmiBookmark.setEnabled(false);
                  jmiBookmarkDelete.addActionListener(this);
            } else {
                  jmiBookmarkDelete.setEnabled(false);
                  jmiBookmark.addActionListener(this);
            }
            
            popup.addSeparator();
            popup.add(jmiBookmark);
            popup.add(jmiBookmarkDelete);
        }
        
        return popup;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ev){
        
        Object source = ev.getSource();
        
        try {
            if (source == jmiAddBuddy){
                
                User user = (User) list.getSelectedValue();
                
                AddContactOut out = new AddContactOut();
                out.setUser(new KaiString(user.getUser()));
                
                ActionExecuter.execute(out);
                
            } else if (source == jmiEnterArena){
                
                Arena arena = (Arena) list.getSelectedValue();
                
                Manager.enterArena(arena);
                ArenaMode.arena = arena;
                
                
            } else if (source == jmiParentArena || source == goBackButton){
                
                
                // since I know that parent arena never has password,
                // I can safelly send this message to Executor   
//                if (JKaiUI.MODERATORS.contains(KaiConfig.getInstance().getConfigString("TAG"))) {
//                    JKaiUI.MODERATORS.remove(KaiConfig.getInstance().getConfigString("TAG"));
//                }

                KaiVectorParent vector = new KaiVectorParent();
                vector.setVector(new KaiString(JKaiUI.getARENA()));
                ActionExecuter.execute(vector);
                
            } else if (source == jmiChat){
                
                User user = (User) list.getSelectedValue();
                ChatManager.getInstance().openChat(user);
                
            } else if (source == arenaModeComboBox){
                
                // status start in 1
                
                int status = arenaModeComboBox.getSelectedIndex()+1;
                
                ArenaStatusOut statusMsg = new ArenaStatusOut();
                statusMsg.setStatus(status);
                statusMsg.setPlayers(1); // TODO: SUPPORT MORE LOCAL PLAYERS
                
                ActionExecuter.execute(statusMsg);
                
            } else if (source == createArenaButton) {
                createArenaPane();
            }
            
            else if (source == jmiUserProfile) {
                User user = (User) list.getSelectedValue();
                Manager.getInstance().send(new GetUserProfile(user.getName()));
            }
            
            else if (source == jmiBanUser) {
                User user = (User) list.getSelectedValue();
                Manager.getInstance().send(new BanUser(user.getName()));
            }
            
            else if (source == jmiKickUser) {
                User user = (User) list.getSelectedValue();
                Manager.getInstance().send(new KickUser(user.getName()));
            }
            
            else if (source == jmiDisconnectUser) {
                User user = (User) list.getSelectedValue();
                Manager.getInstance().send(new DisconnectUser(user.getName()));
            }
            
            else if (source == jmiBookmark){
                    Arena arena = (Arena) list.getSelectedValue();
                    MainUI.getInstance().addBookmark(arena);
            }       
            else if (source == jmiBookmarkDelete){
                    Arena arena = (Arena) list.getSelectedValue();
                    MainUI.getInstance().deleteBookmark(arena);
            }                         
        } catch (Exception e) {
            System.out.println("ArenaMode:"+e);
        }
    }
    
    
    @Override
    public Vector getSpecialComponents(){        
        return components;
    }
    
    
    class ComboBoxRenderer extends JLabel implements ListCellRenderer {
        
        
        public ComboBoxRenderer() {
            setOpaque(true);
            setHorizontalAlignment(CENTER);
            setVerticalAlignment(CENTER);
        }
        
    /*
     * This method finds the image and text corresponding
     * to the selected value and returns the label, set up
     * to display the text and image.
     */
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            //Get the selected index. (The index param isn't
            //always valid, so just use the value.)
            
            int selectedIndex = ((Integer)value).intValue();
            
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            
            //Set the icon and text.  If icon was null, say so.
            ImageIcon icon = arenaModeIcons[selectedIndex];
            setIcon(icon);
            setText(arenaModeText[selectedIndex]);
            setHorizontalAlignment(LEFT);
            return this;
        }
    }
    
    public void resetArenaMode(){
        
        arenaModeComboBox.setSelectedIndex(0);
        
    }
    
    public void enableCreateArena(boolean bool){
        
        createArenaButton.setEnabled(bool);
        
    }
    
    
    public void enableGoParentArena(boolean bool){
        
        goBackButton.setEnabled(bool);
        
    }
    
    
    private void createArenaPane(){
        
        Object[] message = new Object[7];
        
        message[0] = java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_Description");
        
        JTextField descriptionTextField = new JTextField();
        message[1] = descriptionTextField;
        
        message[2] = java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_MaxPlayers");
        
        JTextField maxPlayersTextField = new JTextField();
        message[3] = maxPlayersTextField;
        
        message[4] = java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_Password");
        
        JTextField passwordTextField = new JTextField();
        message[5] = passwordTextField;
        
        JCheckBox hostmodeCheckBox = new JCheckBox(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_SwitchToHost"));
        hostmodeCheckBox.setSelected(true);
        message[6] = hostmodeCheckBox;
        
        String[] options = {
            java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("BTN_Ok"),
                    java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("BTN_Cancel")
        };
        
        int result = JOptionPane.showOptionDialog(
                null,                                       // the parent that the dialog blocks
                message,                                    // the dialog message array
                java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_CreateArena"), // the title of the dialog window
                JOptionPane.DEFAULT_OPTION,                 // option type
                JOptionPane.QUESTION_MESSAGE,               // message type
                null,                                       // optional icon, use null to use the default icon
                options,                                    // options string array, will be made into buttons
                options[0]                                  // option that should be made into a default button
                );
        
        String description = "";
        String password = "";
        int maxPlayers = 16;
        
        String error = null;
        
        switch(result) {
            case 0: // ok -- Validating input!
                
                description = descriptionTextField.getText();
                password = passwordTextField.getText();
                String maxPlayersString = maxPlayersTextField.getText();
                
                if(! maxPlayersString.matches("^\\d{0,4}$")){
                    
                    error = java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("MSG_InvalidMaxPlayers");
                    break;
                }
                maxPlayers = Integer.parseInt(maxPlayersString.length()>0?maxPlayersString:"0");
                
                if(! description.matches("^.{0,1000}$")){
                    
                    error = java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("MSG_InvalidDescription");
                    break;
                }
                
                if(! password.matches("^.{0,100}$")){
                    
                    error = java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("MSG_InvalidPassword");
                    break;
                }
                
                
                break;
            case 1: // no
                return;
        }
        
        if (error != null){
            JOptionPane.showMessageDialog(null,error);
            
            return;
        }
        // Create vector
        
        CreateVectorOut out = new CreateVectorOut();
        out.setDescription(new KaiString(description));
        out.setPassword(new KaiString(password));
        out.setMaxPlayers(maxPlayers);
        
        
        ActionExecuter.execute(out);
        
        if (hostmodeCheckBox.isSelected()) {
            arenaModeComboBox.setSelectedIndex(1);
        }
    }
    
    public void setIndexArenaModeComboBox(int index) {
        arenaModeComboBox.setSelectedIndex(index);
    }
    
    public void clickgoBackButton(){
        goBackButton.doClick();
    }
    
    public void clickcreateArenaButton(){
        createArenaButton.doClick();
    }
}
