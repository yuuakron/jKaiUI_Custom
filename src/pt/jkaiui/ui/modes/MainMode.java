/*
 * MainMode.java
 *
 * Created on November 21, 2004, 12:59 AM
 */

package pt.jkaiui.ui.modes;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import pt.jkaiui.JKaiUI;
import pt.jkaiui.core.Arena;
import pt.jkaiui.core.KaiObject;
import pt.jkaiui.core.User;
import pt.jkaiui.manager.ChatManager;
import pt.jkaiui.manager.Manager;
import pt.jkaiui.ui.ChatPanel;
import pt.jkaiui.ui.MainUI;
/**
 *
 * @author  pedro
 */
public abstract class MainMode extends javax.swing.JPanel implements I_MainMode, ActionListener {
    
    protected static Logger _logger;
    
    public static final int WIDTH = 250;
    
    protected final ImageIcon CHAT_ICON = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/chat.png"));
    private ListModel listModel;
    public JList list;
    
    /** Creates new form MainMode */
    public MainMode() {
        
        setLayout(new java.awt.BorderLayout());
        setFocusable(false);
        
        list = new JList();
        list.setCellRenderer(new KaiListCellRenderer());
        listModel = getListModel();
        list.setModel(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        list.addMouseListener(
                new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent me){
                
                // Display menu if right mouse button clicked
                if (SwingUtilities.isRightMouseButton(me)){
                    
//                    System.out.println("mainmode click");
                    
                    JList list = (JList) me.getComponent();
                    
                    // What an ugly way to get MainMode... !
//                    MainMode mode = (MainMode) list.getParent().getParent().getParent();
                    
                    int Index = list.locationToIndex(me.getPoint());
                    if (Index != -1) {
                        list.setSelectedIndex(Index);
                        JPopupMenu popup = getPopupMenu( (KaiObject) list.getSelectedValue());
                        if (popup != null) {
                            popup.show(list, me.getX(), me.getY());
                        }
                    }
                }
                
                // Lets fetch double-click!
                else if(SwingUtilities.isLeftMouseButton(me) && me.getClickCount() == 2){
                    
                    JList list = (JList) me.getComponent();
                    Object obj = list.getSelectedValue();
                    
                    if( obj instanceof User){
                        
                        User user = (User) obj;
                        if(user.isOnline()){
                            
                            ChatManager.getInstance().openChat(user);
                        }
                    }
                    if( obj instanceof Arena){
                        // enter arena
                        Arena arena = (Arena) obj;
                        
                        Manager.enterArena(arena);
                        
                    }
                }
                
                Object selected = MainUI.getInstance().jTabbedPane.getSelectedComponent();
                if(selected instanceof ChatPanel) {
                    ((ChatPanel)selected).getInputField().requestFocus();
                }
            }
        }
        );
        
        // Set the preferred row count. This affects the preferredSize
        // of the JList when it's in a scrollpane.
        list.setVisibleRowCount(15);
        list.setFixedCellWidth(WIDTH);
        
        // Add list to a scrollpane
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane,BorderLayout.CENTER);
        
        setVisible(false);
    }
    
    /**
     * Setter for property modeName.
     * @param modeName New value of property modeName.
     */
    public void setModeName(String modeName) {
        MainUI.getInstance().SetModeTitle(modeName);
    }
    
    @Override
    public abstract String getName();
    
    public abstract ListModel getListModel();
    
    public void previewMode(){
        
        _logger.log(Level.FINE, "Previewing {0}", getName());
        
        setModeName(getName() + " - " + java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_Preview"));
        
        MainUI.getInstance().getJPanelMode().removeAll();
        MainUI.getInstance().getJPanelMode().add(this,BorderLayout.CENTER);
        setVisible(true);
        
        MainUI.getInstance().getJPanelMode().repaint();
        
        
    }
    
    public void selectMode(){
        
        _logger.log(Level.FINE, "Selecting {0}", getName());
        
        
        MainUI.getInstance().getJPanelMode().removeAll();
        setSize(250, MainUI.getInstance().jTabbedPane.getHeight());
        MainUI.getInstance().getJPanelMode().add(this,BorderLayout.CENTER);
        
        setModeName(getName());
        setVisible(true);
        MainUI.getInstance().getJPanelMode().repaint();
        
        // Setting special commands
        
        JPanel panel = MainUI.getSpecialCommandsPanel();
        panel.removeAll();
        
        _logger.config("Adding special commands");
        
        Vector components = getSpecialComponents();
        Iterator i = components.iterator();
        while(i.hasNext()){
            
            Component comp = (Component) i.next();
            panel.add(comp);
        }
        panel.repaint();
        
    }
    
    @Override
    public Insets getInsets(){
        
        return new Insets(0,3,3,0);
        
    }
    
    protected abstract JPopupMenu getPopupMenu(KaiObject obj);
    
    public abstract Vector getSpecialComponents();
    
    public void setRoomFontSize(int size){
        ((KaiListPanel)list.getCellRenderer()).setFontSize(size);
    }    
}
