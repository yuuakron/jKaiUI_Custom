/*
 * KaiListCellRenderer.java
 *
 * Created on December 1, 2004, 9:10 PM
 */

package pt.jkaiui.ui.modes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.*;
import java.util.Hashtable;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import pt.jkaiui.JKaiUI;
import pt.jkaiui.core.Arena;
import pt.jkaiui.core.Diags;
import pt.jkaiui.core.Game;
import pt.jkaiui.core.KaiConfig;
import pt.jkaiui.core.KaiObject;
import pt.jkaiui.core.User;


/**
 *
 * @author  pedro
 */
public class KaiListCellRenderer extends KaiListPanel implements ListCellRenderer  {
    
    private JPanel	_thePanel;
    private final ImageIcon USER_IMAGE_ICON = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/user.png"));
    private final ImageIcon USER_OK_IMAGE_ICON = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/user_ok.png"));
    private final ImageIcon USER_OFFLINE_IMAGE_ICON = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/user_offline.png"));
    
    
    // Status icons
    
    private final ImageIcon GAME_IMAGE_ICON = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/agame.png"));
    private final ImageIcon ARENA_IMAGE_ICON = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/agame.png"));
    
    
    private final ImageIcon HOSTING_OVERLAY = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/status_hosting_overlay.png"));
    private final ImageIcon DEDICATED_HOSTING_OVERLAY = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/status_dedicated_hosting_overlay.png"));
    private final ImageIcon PRIVATE_OVERLAY = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/private_overlay.png"));
    private final ImageIcon LOCKED_OVERLAY = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/locked_overlay.png"));
    private final ImageIcon CHAT_OVERLAY = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/using_chat_overlay.png"));
    private final ImageIcon BUSY_OVERLAY = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/playing_state_overlay.png"));
    private final ImageIcon ADMIN_OVERLAY = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/admin_overlay.png"));
    private final ImageIcon MODERATOR_OVERLAY = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/moderator_overlay.png"));
    
    
    private final String ARENA_URL_PREFIX = "http://www.teamxlink.co.uk/media/avatars/";
    
    private static Hashtable avatars;
    private static Hashtable loading = new Hashtable();
    
    /** Creates a new instance of KaiListCellRenderer */
    public KaiListCellRenderer() {
        
        super();
        
        setOpaque(true);
        setPreferredSize(new Dimension(MainMode.WIDTH, 56));
        
        avatars = new Hashtable();
    }
    
    
    
    
    public Component getListCellRendererComponent(
    JList list,
    Object object,
    int index,
    boolean isSelected,
    boolean cellHasFocus) {
        
        ImageIcon icon = null;
        
        //this.setColor(_theColors[selectedIndex]);
        
        KaiObject kaiObject = (KaiObject) object;
        
        String suffix = "";

        if (object == null) {
            return null;
        }

        if (kaiObject instanceof User) {

            User user = (User) kaiObject;
 
            if (user.getUser().equals("@")) {
                setText("");
                setIcon(null);
                setDescription("");
                setC1("");
                setC2("");
//                this.setVisible(false);
//                this.setEnabled(false);
                setBackground(Color.WHITE);
                repaint();
                return this;
            }
            
            this.setBackground(isSelected);
            
            icon = USER_OK_IMAGE_ICON;
            
            if(user.isOnline()){
                if(user.getPing()>0){
                    
                    setC1("Ping: "+ user.getPing()+" ms");
                    setC2("");
                    
                    if (user.getCurrentArena()!=null){
                        
                        String arena = user.getCurrentArena().length()>0?user.getCurrentArena():java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_MessengerMode");
                        setDescription(arena);
                    } else {
                        setDescription("");
                    }
                    
                    // Lets put the icon
                    
                    switch(user.getStatus()){
                        
                        case User.IDLE:
                            if (user.getIcon() != null )
                                icon = user.getIcon();
                            else
                                icon = USER_OK_IMAGE_ICON;
                            
                            break;
                            
                        case User.BUSY:
                            icon = USER_OK_IMAGE_ICON;
                            break;
                            
                    }
                    
                }
                else{
                    icon = USER_IMAGE_ICON;
                    setDescription(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("MSG_EstablishingLink"));
                    setC1("");
                    setC2("");
                }
            }
            else{
                icon = USER_OFFLINE_IMAGE_ICON;
                setDescription(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("MSG_UserOffline"));
                setC1("");
                setC2("");
            }
            
            
            // Lets see if the user is chatting.
            
            if ( user.isChat() ){
                
                icon = overlapGraphics(icon,CHAT_OVERLAY);
            }
            
            
            // checking the user status
            if ( user.getStatus() == User.BUSY )
                icon = overlapGraphics(icon,BUSY_OVERLAY);
            
            
            if ( user.getStatus() == User.HOSTING )
                icon = overlapGraphics(icon,HOSTING_OVERLAY);
            
            if ( user.getStatus() == User.DEDICATED_HOSTING )
                icon = overlapGraphics(icon,DEDICATED_HOSTING_OVERLAY);
            
            if ( user.isAdmin() && JKaiUI.CURRENT_MODE == JKaiUI.ARENA_MODE)
                icon = overlapGraphics(icon,ADMIN_OVERLAY);
            
            if ( user.isModerator() && JKaiUI.CURRENT_MODE == JKaiUI.ARENA_MODE )
                icon = overlapGraphics(icon,MODERATOR_OVERLAY);
            
            
            
        } else {

            this.setBackground(isSelected);
        }
        if (kaiObject instanceof Game) {
            icon = GAME_IMAGE_ICON;
        }

        if ( kaiObject instanceof Arena){
            
            
            
            final Arena arena = (Arena) kaiObject;
            
            // Lets start with standard icon
            icon = ARENA_IMAGE_ICON;
            
            if(arena.getSubs() > 0)
                setDescription(arena.getSubs() + " " + java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_SubArenas") );
            else
                setDescription(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("MSG_NoSubArenas") );
            
            String PlayerCount = "";
            if(arena.getUsers() > 0)
                PlayerCount = arena.getUsers() + " " + java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_Players");
            else
                PlayerCount = java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("MSG_NoPlayers");
            
            if(arena.getMaxPlayers() > 0)
                setC1(PlayerCount + " / "+ java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_Max") +": " + arena.getMaxPlayers());
            else
                setC1(PlayerCount);
            
            if (arena.isUser()){
                
                setDescription(arena.getDescription());
            }
            else{
                
                // Haha!! Lets fetch the arena icon!
                ImageIcon avatar;
                
                if ( avatars.get(arena.getVector()) != null){
                    // Cached
                    icon = (ImageIcon) avatars.get(arena.getVector());
                    
                }
                else{
                    // No icon yet! Lets fetch it!
                    
                    final SwingWorker worker = new SwingWorker() {
                        public Object construct() {
                            
                            String urlString = ARENA_URL_PREFIX + arena.getVector().replaceAll(" ","%20") + ".jpg";
                            
                            if (loading.get(urlString) == "1" )
                                return null;
                            
                            // mark as loaded
                            loading.put(urlString,"1");
                            
                            ImageIcon avatar = null;
                            
                            File iconLocation = new File(JKaiUI.getConfig().getConfigString(KaiConfig.ConfigTag.AVATARCACHE), arena.getVector().replace('/', File.separatorChar).toLowerCase() + ".ii");

                            File iconFolder = new File(iconLocation.getParent());
                            if(!iconFolder.exists()) iconFolder.mkdirs(); 
                            
                            // 86400000 milliseconds in a day
                            if(iconLocation.exists() && (iconLocation.lastModified() < System.currentTimeMillis() + (86400000 * JKaiUI.getConfig().getConfigInt(KaiConfig.ConfigTag.CACHEDAYS)))) {
                                try {
                                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(iconLocation));
                                    avatar = (ImageIcon) ois.readObject();
                                    ois.close();
                                } catch(ClassNotFoundException cnfe) {
                                    System.out.println("KaiListCellRenderer:"+cnfe);
                                } catch(IOException ioe) {
                                    //System.out.println("Couldn't read avatar cache! Reason: " + ioe.getMessage());
                                    System.out.println("KaiListCellRenderer:"+ioe);
                                }
                            }
                            
                            if(avatar == null) {
                                try{
                                    URL url = new URL( ARENA_URL_PREFIX + arena.getVector().replaceAll(" ","%20") + ".jpg");
                                    avatar = new ImageIcon(url);    
                                }
                                catch (MalformedURLException e){
                                    System.err.println("Malformed image url for arena " + arena.getVector() +": " + e.getMessage());
                                    setIcon(ARENA_IMAGE_ICON);
                                }
                                
                                try {
                                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(iconLocation));
                                    oos.writeObject(avatar);
                                    oos.close();
                                } catch(IOException ioe) {
                                    //System.out.println("Couldn't cache avatar! Reason: " + ioe.getMessage());
                                    System.out.println("KaiListCellRenderer:"+ioe);
                                }
                            }
                            
                            // Caching...
                            if (avatar.getImageLoadStatus() != MediaTracker.COMPLETE)
                                avatar = ARENA_IMAGE_ICON;
                            
                            avatars.put(arena.getVector(),avatar);
                            
                            return avatar;
                            
                        }
                    };
                    worker.start();  //required for SwingWorker 3
                    
                    
                }
            }
            
            
            // is it a private arena?
            
            if ( arena.isUser() )
                icon = overlapGraphics(icon,PRIVATE_OVERLAY);
            
            // Has pass?
            
            if ( arena.isPass() )
                icon = overlapGraphics(icon,LOCKED_OVERLAY);
        }

            if (kaiObject instanceof Diags) {

                final Diags diags = (Diags) kaiObject;

                setDescription(diags.getValue1());
                setC1(diags.getValue2());
                setC2(diags.getValue3());
                icon = diags.getIcon();

            }
            
            try {
                setText(kaiObject.getName());
                setIcon(icon);

                return this;
            }
            catch (Exception e) {
                System.out.println("ListCellRenderer:"+e);
                return null;
            }
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
    
    
}
