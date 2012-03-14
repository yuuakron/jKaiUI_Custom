/*
 * ActionExecuter.java
 *
 * Created on November 22, 2004, 7:43 PM
 */

package pt.jkaiui.manager;

import java.awt.MediaTracker;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import pt.jkaiui.JKaiUI;
import static pt.jkaiui.core.KaiConfig.ConfigTag.*;
import pt.jkaiui.core.*;
import pt.jkaiui.core.messages.*;
import pt.jkaiui.tools.log.ConfigLog;
import pt.jkaiui.ui.InfoPanel;
import pt.jkaiui.ui.modes.MessengerMode;
import pt.jkaiui.ui.modes.MessengerModeListModel;

/**
 * @author  pedro
 */
public class ActionExecuter {
    
    private static Logger _logger;
    private static Manager manager;
    
    private static String CurrentArena;
    private static boolean KaiVectorOutFlag;
//    private static Chat2 chatbuf;
    
    /** Creates a new instance of ActionExecuter */
    public ActionExecuter( Manager manager ) {
        _logger = ConfigLog.getLogger(this.getClass().getName());
        ActionExecuter.manager = manager;
    }
    
    
    public static void execute(Message msg){
        
        // UI -> Engine -- DiscoverMssage
        
        if (msg instanceof DiscoverEngine){
            // just send the message
            manager.send( (I_OutMessage) msg);
        }
        
        
        // UI -> Engine -- KaiVectorOut -> Enter an arena
        
        else if (msg instanceof KaiVectorOut){
            
            KaiVectorOutFlag = true;
            
            JKaiUI.getArenaMode().setIndexArenaModeComboBox(0);
            
            // just send the message
            KaiVectorOut vec = (KaiVectorOut) msg;
            //_logger.config("Entering arena "+ vec.getVector());
            manager.send( (I_OutMessage) msg);
        }
        
        // UI -> Engine -- KaiVectorOut -> Enter parent arena
        
        else if (msg instanceof KaiVectorParent){            
            // just send the message
            KaiVectorParent vec = (KaiVectorParent) msg;
            
            _logger.log(Level.CONFIG, "Entering arena {0}", vec.getVector());
            
            KaiVectorOut out = new KaiVectorOut();
            out.setVector(vec.getVector());
            
            
            MessengerModeListModel model = (MessengerModeListModel) JKaiUI.getArenaMode().getListModel();
            model.clear();
            
            KaiVectorOut vecOut = new KaiVectorOut();
            vecOut.setVector(vec.getVector());
            execute(vecOut);
            
        }
        
        
        // Engine -> UI -- Engine Here
        
        else if (msg instanceof EngineHere){
            _logger.info("Attaching to engine");
            manager.send(new AttachEngine());
        }
        
        // Engine -> UI -- Attached
        
        else if (msg instanceof AttachEngine){
            
            JKaiUI.connected();
            
            _logger.finest("Attached! Getting State");
            
            // set caps
            CapsOut caps = new CapsOut();
            manager.send(caps);
            
            manager.send( new GetState() );
        }
        
        // Engine -> UI -- EngineInUse
        
        else if (msg instanceof EngineInUse){
            
            _logger.info("Engine in use. Taking over...");
            manager.send( new Takeover() );
        }
        
        // Engine -> UI -- ClientNotLogged
        
        else if (msg instanceof ClientNotLoggedIn){
            
            ClientNotLoggedIn m = (ClientNotLoggedIn) msg;
            _logger.log(Level.FINEST, "Client not logged in; User: {0}; Auto: {1}", new Object[]{m.getUser().decode(), m.isAuto()?"on":"off"});
            LoginMessage login = new LoginMessage();
            login.setLogin(new KaiString(JKaiUI.getConfig().getConfigString(TAG)));
            login.setPassword(new KaiString(JKaiUI.getConfig().getConfigString(PASSWORD)));
            manager.send( login );
        }
        
        else if ( msg instanceof ConnectedArena){
            JKaiUI.getMainUI().jButtonArenaMode.doClick();
        }
        
        // Engine -> UI -- Status Message
        
        else if (msg instanceof Status){
            
            Status m = (Status) msg;
            _logger.log(Level.INFO, "Status: {0}", m.getMessage().decode());
        }
        
        
        // Engine -> UI -- Add Contact
        
        else if (msg instanceof AddContact){
            
            AddContact contact = (AddContact) msg;
            User user = new User();
            user.setName(contact.getUser().decode());
            
            MessengerModeListModel model = (MessengerModeListModel) JKaiUI.getMessengerMode().getListModel();
            model.addElement(user);
        }
        
        
        // Engine -> UI -- Connected Message
        
        else if (msg instanceof ConnectedMessenger){
            
            MessengerMode mode = JKaiUI.getMessengerMode();
            JKaiUI.selectMode(mode);
            _logger.info("In Messenger Mode");
        }
        
        
        // Engine -> UI -- AdminPrivileges
        
        else if (msg instanceof AdminPrivileges){
            
            AdminPrivileges m = (AdminPrivileges) msg;
            
            String[] admins = m.getUsers().decode().split("/");
            
            JKaiUI.ADMINISTRATORS.clear();
            JKaiUI.ADMINISTRATORS.addAll(Arrays.asList(admins));
            
            _logger.log(Level.CONFIG, "Administrators: {0}", m.getUsers().decode());
            
        }
        
        // Engine -> UI -- ModeratorPrivileges
        
        else if (msg instanceof ModeratorPrivileges){
            
            ModeratorPrivileges m = (ModeratorPrivileges) msg;
            // add this to the list of MODERATORS
            
            String[] moderators = m.getUsers().decode().split("/");
            
            JKaiUI.MODERATORS.clear();
            JKaiUI.MODERATORS.addAll(Arrays.asList(moderators));
            
            _logger.log(Level.CONFIG, "Moderators: {0}", m.getUsers().decode());
            
        }
        
        
        // Engine -> UI -- Contact Online
        
        else if (msg instanceof ContactOnline) {

            ContactOnline contact = (ContactOnline) msg;
            User user = new User();
            user.setName(contact.getUser().decode());

            MessengerModeListModel model = (MessengerModeListModel) JKaiUI.getMessengerMode().getListModel();
            user = (User) model.getElement(user);
            user.setOnline(true);

            model.updateElement(user);

            // Contact added. Lets fetch its avatar!
            AvatarOut avatar = new AvatarOut();
            avatar.setUser(contact.getUser());
            execute(avatar);

            if (JKaiUI.getConfig().getConfigBoolean(ShowFriendLoginInfo)) {
                JKaiUI.getChatManager().loginFriend(contact);
            }
            JKaiUI.getLogFileManager().println(contact);
        } // Engine -> UI -- Contact Offline
        else if (msg instanceof ContactOffline) {

            ContactOffline contact = (ContactOffline) msg;
            User user = new User();
            user.setName(contact.getUser().decode());

            MessengerModeListModel model = (MessengerModeListModel) JKaiUI.getMessengerMode().getListModel(); 
            user = (User) model.getElement(user);

            user.setOnline(false);
            model.updateElement(user);

            if (JKaiUI.getConfig().getConfigBoolean(ShowFriendLoginInfo)) {
                JKaiUI.getChatManager().logoutFriend(contact);
            }
            JKaiUI.getLogFileManager().println(contact);
        }
        
        // Engine -> UI -- Joins Vector
        
        else if (msg instanceof JoinsVector) {

            JoinsVector contact = (JoinsVector) msg;
            User user = new User();
            user.setName(contact.getUser().decode());

            MessengerModeListModel model = (MessengerModeListModel) JKaiUI.getArenaMode().getListModel();
            user = (User) model.getElement(user);
            user.setOnline(true);
            model.updateElement(user);

            JKaiUI.getLogFileManager().println(contact);

        }
        
        
        // Engine -> UI -- LeavesVector

        else if (msg instanceof LeavesVector) {
//            System.out.println("LeavesVectorstart");

            LeavesVector contact = (LeavesVector) msg;
            User user = new User();
            user.setName(contact.getUser().decode());

            MessengerModeListModel model = (MessengerModeListModel) JKaiUI.getArenaMode().getListModel();
//            System.out.println("LeavesVector:" + ((User) model.getElement(user)).getName());
            user = (User) model.getElement(user);
            user.setOnline(false);
            model.removeElement(user);

//            System.out.println("LeavesVectorend");
        }
        
        
        // Engine -> UI -- SubVector
        else if (msg instanceof SubVector){
            
            SubVector contact = (SubVector) msg;
            Arena arena = new Arena();
            arena.setUser(false);
            arena.setVector(contact.getVector().decode());
            arena.setUsers(contact.getCount());
            arena.setSubs(contact.getSubs());
            arena.setPass(contact.isPass());
            arena.setMaxPlayers(contact.getMaxPlayers());

            MessengerModeListModel model = (MessengerModeListModel) JKaiUI.getArenaMode().getListModel();
            model.updateElement(arena);

//            JKaiUI.getChatManager().addGotoArenaMenuItem(arena);
            JKaiUI.getLogFileManager().println(contact);
        }
        
        // Engine -> UI -- UserSubVector
        else if (msg instanceof UserSubVector) {

//             System.out.println("UserSubVectorstart");           
            
            UserSubVector contact = (UserSubVector) msg;
            Arena arena = new Arena();
            arena.setVector(contact.getVector().decode());
            arena.setUser(true);
            arena.setUsers(contact.getCount());
            arena.setSubs(contact.getSubs());
            arena.setPass(contact.isPass());
            arena.setMaxPlayers(contact.getMaxPlayers());
            arena.setMaxPlayers(contact.getMaxPlayers());
            
            try{
                if(JKaiUI.getConfig().getConfigBoolean(URLDecode)){
                //URL�f�R�[�h
                    arena.setDescription(URLDecoder.decode(contact.getDescription().decode(),"utf-8"));
                }else{
                    arena.setDescription(contact.getDescription().decode());
                }
            }catch(Exception e){
                System.out.println("Action UserSubVector:"+e);
            }            
            MessengerModeListModel model = (MessengerModeListModel) JKaiUI.getArenaMode().getListModel();
//           System.out.println("UserSubVector:" + arena.getName());
            model.updateElement(arena);
            
//            JKaiUI.getChatManager().addGotoArenaMenuItem(arena);
            
            JKaiUI.getLogFileManager().println(contact);
            
//             System.out.println("UserSubVectorend");
           
        }
        
        
        // Engine -> UI -- SubVectorUpdate
        
        else if (msg instanceof SubVectorUpdate){
            
//            System.out.println("SubVectorUpdatestart");

            SubVectorUpdate contact = (SubVectorUpdate) msg;

            Arena arena = new Arena();
            arena.setVector(contact.getVector().decode());

            // get all other values
            MessengerModeListModel model = (MessengerModeListModel) JKaiUI.getArenaMode().getListModel();

            int index = model.indexOf(arena);
            if (index != -1) {
//                System.out.println("SubVectorUpdate:" + (Arena) model.getElement(arena));
                arena = (Arena) model.getElementAt(index);

            }
            if (arena != null) { //JBI testing...
                arena.setUsers(contact.getCount());
                arena.setSubs(contact.getSubs());

                model.updateElement(arena);
            }
//            System.out.println("SubVectorUpdateend");
        }
        
        // Engine -> UI -- RemoveSubVector
        
        else if (msg instanceof RemoveSubVector){
            
//            System.out.println("RemoveSubVectorstart");
            
            RemoveSubVector contact = (RemoveSubVector) msg;
            
            Arena arena = new Arena();
            arena.setVector(contact.getVector().decode());
            
            // get all other values
            MessengerModeListModel model = (MessengerModeListModel) JKaiUI.getArenaMode().getListModel();
//            System.out.println("RemoveSubVector:" + arena.getName());
            model.removeElement(arena);
           
//            JKaiUI.getChatManager().removeGotoArenaMenuItem(arena);
 //            System.out.println("RemoveSubVectorend");
           
        }
        
        
        // Engine -> UI -- ArenaPing
        
        else if (msg instanceof ArenaPing){
            ArenaPing contact = (ArenaPing) msg;
            User user = new User();
            user.setName(contact.getUser().decode());
            
            MessengerModeListModel model = (MessengerModeListModel) JKaiUI.getArenaMode().getListModel();
            
            user = (User) model.getElement(user);
            user.setPing(contact.getPing());
            user.setStatus(contact.getStatus());

            model.updateElement(user);
        }
        
        
        // Engine -> UI -- ContactPing
        
        else if (msg instanceof ContactPing){
            ContactPing contact = (ContactPing) msg;
            User user = new User();
            user.setName(contact.getUser().decode());
            
            MessengerModeListModel model = (MessengerModeListModel) JKaiUI.getMessengerMode().getListModel();
            // To preserve other attributes

            user = (User) model.getElement(user);

            user.setOnline(true);
            user.setPing(contact.getPing());
            user.setCurrentArena(contact.getCurrentArena().decode());
            model.updateElement(user);
        }
        
        // Engine -> UI -- User Data
        
        else if (msg instanceof UserData){
            
            // I'll force messenger mode. This will initialize chats
            
            KaiVectorOut vecOut = new KaiVectorOut();
            vecOut.setVector(new KaiString(""));
            execute(vecOut);
        }
        
        
        // Engine -> UI -- Already logged in
        
        else if (msg instanceof LoggedIn){
 
            _logger.config("Already logged in");
        }
        
        
        // Engine -> UI -- KaiVectorIn
        
        else if (msg instanceof KaiVectorIn){
            if (KaiVectorOutFlag == false) {
                if (JKaiUI.getConfig().getConfigBoolean(AutoArenaMoving)) {
                    KaiVectorOut vecOut = new KaiVectorOut();
                    vecOut.setVector(new KaiString(CurrentArena));
                    execute(vecOut);
                }
            } else {
                KaiVectorOutFlag = false;
            }

            
            KaiVectorIn vec = (KaiVectorIn) msg;
            _logger.log(Level.INFO, "Entered arena: {0}", vec.getVector().decode());
            
            
            // Now that we are inside, do the other operations
            
            //Clean list, unless we went back to messenger mode
            if ( vec.getVector().decode().length() > 0){
                
                MessengerModeListModel model = (MessengerModeListModel) JKaiUI.getArenaMode().getListModel();
                model.clear();
            }
            
            // Set Status
            //JKaiUI.getArenaMode().resetArenaMode();
            
            
            // Enable or disable creating sub arenas
            JKaiUI.getArenaMode().enableCreateArena(vec.isCreatable());
            
            if (vec.getVector().decode().length()>0) {
                JKaiUI.ARENA = vec.getVector().decode();
            }
            
            // Enable or disable the "go parent"
            JKaiUI.getArenaMode().enableGoParentArena(! JKaiUI.ARENA.equals("Arena"));
            
            // After entering, we want to get the sub arenas.
            GetVectors info = new GetVectors();
            info.setVector(vec.getVector());
            
            manager.send(info);
            
            // After enter, set chatroom
            ChatModeOut chat = new ChatModeOut();
            chat.setRoom((vec.getVector().decode().length() > 0) ? vec.getVector() : new KaiString("General Chat"));
            execute(chat);

            JKaiUI.getManager().send(new GetMetrics());

            if (JKaiUI.getConfig().getConfigBoolean(AutoHostSetting)) {
                //Auto Host Setting when you move
                ArenaStatusOut statusMsg = new ArenaStatusOut();
                statusMsg.setStatus(2);
                statusMsg.setPlayers(1); // TODO: SUPPORT MORE LOCAL PLAYERS

                execute(statusMsg);
                JKaiUI.getArenaMode().setIndexArenaModeComboBox(1);
            }
        }
        
        // Engine -> UI -- ChatMode
        
        else if (msg instanceof ChatMode){
            
            //Im now chatting in this room. But I already knew that....
        }
        
        // Engine -> UI -- Chat message
        
        else if (msg instanceof Chat){
            
            Chat m = (Chat) msg;
            
            // Now Im testing a few messages
            InMessage _msg = new InMessage();
            _msg.setUser(new User(m.getUser().decode()));
            _msg.setType(ChatMessage.PUBLIC_MESSAGE);
            _msg.setMessage(m.getMessage().decode());

            // Dont echo my own messages
            if (!_msg.getUser().getName().equals(JKaiUI.getConfig().getConfigString(TAG))) {
                if ((JKaiUI.getConfig().getConfigBoolean(HideServerMessage)) && (_msg.getUser().getUser().equalsIgnoreCase("kai orbital mesh"))) {
                } else {
                    JKaiUI.getChatManager().processMessage(_msg);
                }
                //kai orbital mesh�͏��O
                if(!_msg.getUser().getUser().equalsIgnoreCase("kai orbital mesh")){
                    JKaiUI.getLogFileManager().println(m, CurrentArena);
                }
            }
        }
        
        // Engine -> UI -- Chat2 message
        
        else if (msg instanceof Chat2){
            
            Chat2 m = (Chat2) msg;
/*            
            if (m.getUnknown1().decode().equals("0") && m.getUnknown2().decode().equals("1")) {

                // Now Im testing a few messages
                chatbuf = new Chat2();
                chatbuf.setUser(m.getUser());
                chatbuf.setRoom(m.getRoom());
                chatbuf.setMessage(m.getMessage());

            } else if (m.getUnknown1().decode().equals("1") && m.getUnknown2().decode().equals("0")) {
*/
                // Now Im testing a few messages
                InMessage _msg = new InMessage();
                _msg.setUser(new User(m.getUser().decode()));
                _msg.setType(ChatMessage.PUBLIC_MESSAGE);
//                _msg.setMessage(chatbuf.getMessage().decode() + m.getMessage().decode());

                _msg.setMessage(m.getMessage().decode());

//                chatbuf = null;

                // Dont echo my own messages
                if (!_msg.getUser().getName().equals(JKaiUI.getConfig().getConfigString(TAG))) {
                    if ((JKaiUI.getConfig().getConfigBoolean(HideServerMessage)) && (_msg.getUser().getUser().equalsIgnoreCase("kai orbital mesh"))) {
                    } else {
                        JKaiUI.getChatManager().processMessage(_msg);
                    }
                    //kai orbital mesh�͏��O
                    if (!_msg.getUser().getUser().equalsIgnoreCase("kai orbital mesh")) {
                        JKaiUI.getLogFileManager().println(m, CurrentArena);
                    }

                }
//            }
        }
        
        // Engine -> UI -- PM message
        
        else if (msg instanceof PM ){
            
            PM m = (PM) msg;
            
            // Now Im testing a few messages
            InMessage _msg = new InMessage();
            _msg.setUser(new User(m.getUser().decode()));
            _msg.setType(ChatMessage.PRIVATE_MESSAGE);
            _msg.setMessage(m.getMessage().decode());
            JKaiUI.getChatManager().processMessage(_msg);
            
            JKaiUI.getLogFileManager().println(m);
        }
        
        // Engine -> UI -- Arena PM
        
        else if (msg instanceof ArenaPM ){
            
            ArenaPM m = (ArenaPM) msg;
            
            // Now Im testing a few messages
            InMessage _msg = new InMessage();
            _msg.setUser(new User(m.getUser().decode()));
            _msg.setType(ChatMessage.PRIVATE_MESSAGE);
            _msg.setMessage(m.getMessage().decode());
            JKaiUI.getChatManager().processMessage(_msg);
            
            JKaiUI.getLogFileManager().println(m);
        }
        
        
        // UI -> Engine -- PM
        
        else if (msg instanceof PMOut){
            // just send the message
            manager.send( (I_OutMessage) msg);
            
            JKaiUI.getLogFileManager().println((PMOut) msg);
        } else if (msg instanceof ArenaPMOut){
            // just send the message
            manager.send( (I_OutMessage) msg);
            
            JKaiUI.getLogFileManager().println((ArenaPMOut) msg);
        }
        
        // UI -> Engine -- Add and Remove
        
        else if (msg instanceof AddContactOut){
            // just send the message
            manager.send( (I_OutMessage) msg);
        } else if (msg instanceof RemoveContactOut){
            // just send the message
            manager.send( (I_OutMessage) msg);
        }
        
        
        // Engine -> UI -- Contact removed
        
        else if (msg instanceof RemoveContact){
            
            RemoveContact contact = (RemoveContact) msg;
            User user = new User();
            user.setName(contact.getUser().decode());
            user.setOnline(false);
            
            _logger.log(Level.INFO, "User {0} removed successfully", contact.getUser().decode());
            
            MessengerModeListModel model = (MessengerModeListModel) JKaiUI.getMessengerMode().getListModel();
            model.removeElement(user);
        }
        
        // UI -> Engine -- Enter chatroom
        
        else if (msg instanceof ChatModeOut){
            
            //�����̈ړ��������I�Ɏ���
            
            ChatModeOut vec = (ChatModeOut) msg;
            
            CurrentArena = vec.getRoom().decode();
            
            JKaiUI.getChatManager().enterRoom(vec.getRoom().decode());
            
            manager.send( (I_OutMessage) msg);
        }
        
        // Engine -> UI -- JoinsChat
        // TODO: put in config wheather to receive this?
        
        else if (msg instanceof JoinsChat){
            
            JoinsChat vec = (JoinsChat) msg;
            
            // Notification
            JKaiUI.getChatManager().joinsRoom(vec.getUser().decode());
            
            
            // Change user icon. Do that in arena mode only, for now
            User user = new User();
            user.setName(vec.getUser().decode());
            
            MessengerModeListModel model = (MessengerModeListModel) JKaiUI.getArenaMode().getListModel();
            if (model.indexOf(user) != -1 ){
                user = (User) model.getElement(user);

                user.setChat(true);
                model.updateElement(user);
                //JKaiUI.getMainUI().getListModelArenaUsers().addElement(user.getName());
            } else {
                if (!JKaiUI.getMainUI().getListModelArenaUsers().contains(user.getName())) {
                    JKaiUI.getMainUI().getListModelChatUsers().addElement(user.getName());
                    JKaiUI.getMainUI().UpdateChatUsersQuantity();
                }
            }
        } 
        
        // Engine -> UI -- LeavesChat
        else if (msg instanceof LeavesChat) {

 //           System.out.println("LeavesChatstart");
            
            LeavesChat vec = (LeavesChat) msg;
            JKaiUI.getChatManager().leavesRoom(vec.getUser().decode());

            // Change user icon. Do that in arena mode only, for now
            User user = new User();
            user.setName(vec.getUser().decode());

            MessengerModeListModel model = (MessengerModeListModel) JKaiUI.getArenaMode().getListModel();

            if (model.indexOf(user) != -1) {
//                System.out.println("LeavesChat:"+((User)model.getElement(user)).getName());
                user = (User) model.getElement(user);

                user.setChat(false);
                model.updateElement(user);
                //JKaiUI.getMainUI().getListModelArenaUsers().removeElement(user.getName());
            } else {
                JKaiUI.getMainUI().getListModelChatUsers().removeElement(user.getName());
                JKaiUI.getMainUI().UpdateChatUsersQuantity();
            }
//            System.out.println("LeavesChatend");
        }
        
        // Engine -> UI -- Chat message       
        else if (msg instanceof ChatOut){
            
            manager.send( (I_OutMessage) msg);
            
            JKaiUI.getLogFileManager().println((ChatOut)msg, CurrentArena);
        }
        
        // Engine -> UI -- Arena Status
        
        else if (msg instanceof ArenaStatusOut){
            
            manager.send( (I_OutMessage) msg);
        }
        
        // Engine -> UI -- CreateVectorOut
        
        else if (msg instanceof CreateVectorOut){
            
            KaiVectorOutFlag = true;
            
            _logger.info("Creating room...");
            manager.send( (I_OutMessage) msg);
        }
        
        
        // UI -> Engine -- DettachEngineOut
        
        else if (msg instanceof DetachEngineOut){
            
            _logger.info("Dettaching...");
            manager.send( (I_OutMessage) msg);
            JKaiUI.disconnected();
        }
        
        
        // Engine -> UI -- DettachEngine
        
        else if (msg instanceof DetachEngine){
            
            _logger.warning("Remotely Dettached");
            JKaiUI.disconnected();
        }
        
        
        // UI -> Engine -- AvatarOut
        
        else if (msg instanceof AvatarOut){
            
            // just send it
            manager.send( (I_OutMessage) msg);
        }
        
        
        
        // Engine -> UI -- Avatar
        
        else if (msg instanceof Avatar){
            
            Avatar avatar = (Avatar) msg;
            
            // only makes sense if we have an url!!
            if ( avatar.getUrl().decode().length() == 0 ) {
                return;
            }
            
            User user = new User();
            user.setName(avatar.getUser().decode());
            
            
            ImageIcon icon = null;
            
            File cacheFolder = new File(JKaiUI.getConfig().getConfigString(AVATARCACHE) + File.separator + "users");
            if(!cacheFolder.exists()) {
                cacheFolder.mkdirs();
            }
            
            File iconLocation = new File(cacheFolder, user.getName().toLowerCase() + ".ii");
            
            // 86400000 milliseconds in a day
            if(iconLocation.exists() && (JKaiUI.getConfig().getConfigInt(CACHEDAYS) > 0)  && (iconLocation.lastModified() < System.currentTimeMillis() + (86400000 * JKaiUI.getConfig().getConfigInt(CACHEDAYS)))) {
                try {
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(iconLocation));
                    icon = (ImageIcon) ois.readObject();
                    ois.close();
                } catch(ClassNotFoundException cnfe) {
                    System.out.println("Avatar:"+cnfe);
                } catch(IOException ioe) {
                    System.out.println("Couldn't read avatar cache! Reason: " + ioe.getMessage());
                }
            }
            
            if(icon == null) {
                System.out.println("OLD");
                URL url = null;
                try{
                    url = new URL(avatar.getUrl().decode());
                } catch (MalformedURLException e){
                    System.out.println("Avatar:"+e);
                    _logger.log(Level.SEVERE, "Malformed image url for user {0}: {1}", new Object[]{avatar.getUser().decode(), e.getMessage()});
                }
                icon = new ImageIcon(url);
                
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(iconLocation));
                    oos.writeObject(icon);
                    oos.close();
                } catch(IOException ioe) {
                    System.out.println("Couldn't cache avatar! Reason: " + ioe.getMessage());
                }
            }

            MessengerModeListModel model = (MessengerModeListModel) JKaiUI.getMessengerMode().getListModel();
            user = (User) model.getElement(user);

            if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                user.setIcon(icon);
            }
          
            model.updateElement(user);
            
        }
        
        else if (msg instanceof Metrics) {            
            Metrics metrics = (Metrics) msg;
            
            MessengerModeListModel model = (MessengerModeListModel) JKaiUI.getDiagMode().getListModel();
            
            JKaiUI.setKaiEngineVersion(metrics.getVersion().toString());
            
            Diags diag = new Diags();
            
            diag.setName(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_DiagEngine"));
            diag.setIcon(Diags.ICON_DIAG_ENGINE);
            diag.setValue1(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_DiagVersion") + " " + metrics.getVersion().toString());
            diag.setValue2(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_DiagPlatform") + " " + metrics.getPlatform().toString());
            if (model.contains(diag)) {
                model.removeElement(diag);
            }
            model.add(0,diag);
            
            diag = new Diags();
            diag.setName(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_DiagHardware"));
            diag.setIcon(Diags.ICON_DIAG_HARDWARE);
            diag.setValue1(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_DiagNetCard") + " " + metrics.getNetCard().toString());
            diag.setValue2(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_DiagTechnology") + " " + metrics.getTechnology().toString());
            if (model.contains(diag)) {
                model.removeElement(diag);
            }
            model.add(0,diag);
            
            diag = new Diags();
            diag.setName(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_DiagNetwork"));
            diag.setIcon(Diags.ICON_DIAG_NETWORK);
            diag.setValue1(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_DiagIP") + " " + metrics.getIP().toString() + " " + java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_DiagPort") + " " + metrics.getPort().toString());
            // You can uncomment the line below and comment out the line above if you want the IP/Port to be seperated with a colon (ie 192.168.1.1:30000)
            //diag.setValue1(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_DiagIP") + " " + metrics.getIP() + ":" + metrics.getPort());
            diag.setValue2(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_DiagRechable") + " " + metrics.getReachable().toString());
            if (model.contains(diag)) {
                model.removeElement(diag);
            }
            model.add(0,diag);
            
            diag = new Diags();
            diag.setName(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_DiagOrbServer"));
            diag.setIcon(Diags.ICON_DIAG_ORBSERVER);
            diag.setValue1(metrics.getOrbServer().toString());
            if (model.contains(diag)) {
                model.removeElement(diag);
            }
            model.add(0,diag);
            
        } else if (msg instanceof LocalDevice) {
            LocalDevice locdev = (LocalDevice) msg;

            MessengerModeListModel model = (MessengerModeListModel) JKaiUI.getDiagMode().getListModel();

            Diags diag = new Diags();
            diag.setName(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_DiagMAC") + " " + locdev.getMac());
            diag.setConsoleIcon(locdev.getMac());
            diag.setValue1(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_DiagConfOK"));
            if (!model.contains(diag)) {
                model.addElement(diag);
            } else {
                model.updateElement(diag);
            }
        } else if (msg instanceof DhcpFailure) {
            DhcpFailure locdev = (DhcpFailure) msg;

            MessengerModeListModel model = (MessengerModeListModel) JKaiUI.getDiagMode().getListModel();

            Diags diag = new Diags();
            diag.setName(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_DiagMAC") + " " + locdev.getMac());
            diag.setConsoleIcon(locdev.getMac());
            diag.setValue1(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_DiagConfFailed"));
            diag.setValue2(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_DiagConfFailedDHCP"));

            if (!model.contains(diag)) {
                model.addElement(diag);
            } else {
                model.updateElement(diag);
            }
        } else if (msg instanceof UserProfile) {
            UserProfile uprof = (UserProfile) msg;
            InfoPanel infp = JKaiUI.getMainUI().getJPanelInfos();
            infp.setUser(uprof.GetUser());
            infp.setAge(uprof.GetAge());
            infp.setBandwidth(uprof.GetBandwidth());
            infp.setLocation(uprof.GetLocation());
            infp.setConsoles(uprof.GetConsoles());
            infp.setGames(uprof.GetGames());
            infp.showPanel();
        } else if( msg instanceof RemoteArenaDevice){
            JKaiUI.getLogFileManager().println(msg);
        } else if( msg instanceof CodePage){
        } else if( msg instanceof ArenaStatus){
        } else if( msg instanceof SessionKey){
        }
    }
}
