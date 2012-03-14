/*
 * MessengerMode.java
 *
 * Created on November 21, 2004, 1:20 AM
 */

package pt.jkaiui.ui.modes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.ListModel;
import pt.jkaiui.JKaiUI;
import pt.jkaiui.core.KaiObject;
import pt.jkaiui.core.KaiString;
import pt.jkaiui.core.User;
import pt.jkaiui.core.messages.GetUserProfile;
import pt.jkaiui.core.messages.Message;
import pt.jkaiui.core.messages.RemoveContactOut;
import pt.jkaiui.manager.ActionExecuter;
import pt.jkaiui.tools.log.ConfigLog;


/**
 *
 * @author  pedro
 */
public class MessengerMode extends MainMode implements ActionListener {
    
    
    private static final long serialVersionUID = 123414;
    private static final String NAME = java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_MessengerMode");
    private final ImageIcon DELETE_ICON = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/remove.png"));
    private final ImageIcon USERPROFILE_ICON = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/user_profile.png"));
    private final ImageIcon FOLLOW_ICON = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/follow_user.png"));
    
    
    private MessengerModeListModel listModel;
    
    private JMenuItem jmiRemove, jmiChat, jmiUserProfile, jmiFollowUser;
    
    /** Creates a new instance of MessengerMode */
    public MessengerMode() {
        
        _logger = ConfigLog.getLogger(this.getClass().getName());
        _logger.config("Initializing messenger mode");
        
        
        this.setModeName(NAME);
        
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
        
        if( obj instanceof User){
            
            User user = (User) obj;

            if (user.isOnline()) {
                String currentArena = user.getCurrentArena();
                
                jmiFollowUser = new JMenuItem(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_FollowUser"));
                jmiFollowUser.setIcon(FOLLOW_ICON);
                jmiFollowUser.addActionListener(this);
                if(currentArena == null || currentArena.equals("") || !user.getCurrentArena().startsWith("Arena")) {
                    jmiFollowUser.setEnabled(false);
                }
                popup.add(jmiFollowUser);
                
                jmiChat = new JMenuItem(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_OpenChat"));
                jmiChat.setIcon(CHAT_ICON);
                jmiChat.addActionListener(this);
                popup.add(jmiChat);
            }
        }
        
        jmiUserProfile = new JMenuItem(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_UserProfile"));
        jmiUserProfile.setIcon(USERPROFILE_ICON);
        popup.add(jmiUserProfile);
        jmiUserProfile.addActionListener(this);
        
        popup.addSeparator();
        
        jmiRemove = new JMenuItem(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_RemoveBuddy"));
        jmiRemove.setIcon(DELETE_ICON);
        popup.add(jmiRemove);
        jmiRemove.addActionListener(this);
        
        return popup;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ev){
        
        User user = (User) list.getSelectedValue();
        
        if (user == null) {
            return;
        }
        
        if (ev.getSource() == jmiRemove){
            
            RemoveContactOut out = new RemoveContactOut();
            out.setUser(new KaiString(user.getUser()));
            
            ActionExecuter.execute(out);
            
        } else if (ev.getSource() == jmiChat){
            
            JKaiUI.getChatManager().openChat(user);
            
        }
        
        else if (ev.getSource() == jmiUserProfile){
            
            /* Debuged By Stephen */
            JKaiUI.getManager().send( new GetUserProfile(user.getName()));
            
            
        }
        
        else if (ev.getSource() == jmiFollowUser) {
            
            String vectorLocation = user.getCurrentArena();
            
            if(vectorLocation.startsWith("Arena")) {
//                Arena arena = new Arena();
//                arena.setVector(vectorLocation);
                //System.out.println(arena);
                
                ActionListener[] als = JKaiUI.getMainUI().jButtonArenaMode.getActionListeners();
                
                JKaiUI.ARENA = vectorLocation;
                if(als.length == 1) {
                    als[0].actionPerformed(new ActionEvent(new Object(), 0, ""));
                }
//                JKaiUI.getManager().enterArena(arena);
            }
        }
    }
    
    @Override
    public Vector getSpecialComponents(){
        
        // nothing...
        return new Vector();
    }
}
