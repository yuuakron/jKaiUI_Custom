/*
 * ChatManager.java
 *
 * Created on November 27, 2004, 9:05 PM
 */
package pt.jkaiui.manager;

import java.text.DateFormat;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import pt.jkaiui.JKaiUI;
import static pt.jkaiui.core.KaiConfig.ConfigTag.*;
import pt.jkaiui.core.*;
import pt.jkaiui.core.messages.*;
import static pt.jkaiui.manager.SoundManager.SoundKinds.*;
import pt.jkaiui.tools.log.ConfigLog;
import pt.jkaiui.ui.ChatPanel;
import pt.jkaiui.ui.modes.MessengerModeListModel;
import pt.jkaiui.ui.tools.Say;

/**
 *
 * @author pedro
 */
public class ChatManager {

    public static final String GENERAL_CHAT = "\1";
    private static Logger _logger;
    private Hashtable chatsTable;
    private final String[] COLORS = {"#CCCCCC", "#B7FFF1", "#FFE8A9", "#FF7568", "#FFB3CC", "#FFB7FF", "#81C182", "#C6FF5C", "#C8EFFF", "#CEEBFF", "#979EC1", "#BBBBBB", "#BEDEEA"};
    private final ImageIcon NOTIFY_ICON = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/notify.png"));
    private final ImageIcon UNNOTIFY_ICON = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/unnotify.png"));
//    private final URL MessageSoundUrl = getClass().getResource("/pt/jkaiui/ui/resources/sound/message.wav");
    private List<String> chathistory;
    private int chathistoryindex;

    /**
     * Creates a new instance of ChatManager
     */
    public ChatManager() {
        init();
    }

    private void init() {

        _logger = ConfigLog.getLogger(this.getClass().getName());

        ChatPanel chat = new ChatPanel();
        chat.setName(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_GeneralChat"));
        chat.setClosable(false);
        chat.addToMainUI();

        // We need to enable then disable to init the icon
        disableIcon(chat);


        // Putting generic chat in "null" position
        chatsTable = new Hashtable();
        chatsTable.put(GENERAL_CHAT, chat);

//        String settingpath = new File(url).getParent();
//        _logger.fine("path:" + url);

        chathistory = new LinkedList<String>();
        chathistoryindex = 0;
    }

    public void processMessage(ChatMessage msg) {

        if (msg instanceof InMessage) {
            processInMessage((InMessage) msg);
        } else if (msg instanceof OutMessage) {
            processOutMessage((OutMessage) msg);
        }

    }

    private void processInMessage(InMessage msg) {

        ChatPanel panel = null;

        if (JKaiUI.getConfig().getConfigBoolean(AskCommand)) {
            String texttmp = CommandManager.JKaiUIAskcommand(msg);
            if (texttmp == null) {
                return;
            }
//            msg.setMessage(texttmp);
        }

        if (msg.getType() == ChatMessage.PUBLIC_MESSAGE) {

            // get general arena
            panel = (ChatPanel) chatsTable.get(GENERAL_CHAT);

            //チャットで音を鳴らす
            if (JKaiUI.getConfig().getConfigBoolean(PLAYMESSAGESOUND) && !msg.getUser().getUser().equalsIgnoreCase("kai orbital mesh")
                    && (JKaiUI.getConfig().getConfigBoolean(ChatSound))) {
                JKaiUI.getSoundManager().playMessageSound(Chat);
            }
            //フレンドの発言時に音をならすように変更
            if (JKaiUI.getConfig().getConfigBoolean(PLAYMESSAGESOUND)
                    && (JKaiUI.getConfig().getConfigBoolean(FriendChatSound))
                    && (userIsFriend(msg.getUser()))) {
                JKaiUI.getSoundManager().playMessageSound(FriendChat);
            }
            //モデの発言時に音をならすように変更
            if (JKaiUI.getConfig().getConfigBoolean(PLAYMESSAGESOUND)
                    && (JKaiUI.getConfig().getConfigBoolean(ModeratorChatSound))
                    && (msg.getUser().isModerator())) {
                JKaiUI.getSoundManager().playMessageSound(ModeratorChat);
            }

        } else if (msg.getType() == ChatMessage.PRIVATE_MESSAGE) {

            panel = getOrCreatePanel(msg.getUser().getUser());

            //PMがきたとき音をならすように変更
            if (JKaiUI.getConfig().getConfigBoolean(PLAYMESSAGESOUND)
                    && (JKaiUI.getConfig().getConfigBoolean(FriendPMSound))
                    && (userIsFriend(msg.getUser()))) {
                JKaiUI.getSoundManager().playMessageSound(FriendPM);
            }
            //ArenaPMがきたとき音をならすように変更
            if (JKaiUI.getConfig().getConfigBoolean(PLAYMESSAGESOUND)
                    && (JKaiUI.getConfig().getConfigBoolean(ArenaPMSound))
                    && (!userIsFriend(msg.getUser()))) {
                JKaiUI.getSoundManager().playMessageSound(ArenaPM);
            }
        }

        if (panel == null) {
            _logger.severe("Could not find tab to write message into");
            return;
        }

        panel.write(formatInMessage(msg));
//        panel.write(msg.getMessage());
        Say.speakString(msg.getMessage());

        // Notify if not selected
        enableIconIfSelected(panel);

    }

    private String formatInMessage(InMessage msg) {

        return formatMessage(msg.getUser().getUser(), getColor(msg.getUser().getUser()), encodeMessage(msg.getMessage()));

    }

    private void processOutMessage(OutMessage msg) {

        addchathistory(msg);

        if (JKaiUI.getConfig().getConfigBoolean(CUICommand)) {
            String texttmp = CommandManager.JKaiUIcommand(msg.getMessage());
            if (texttmp == null) {
                return;
            }
            msg.setMessage(texttmp);
        }

        //チャット送信時に音を鳴らす
        if (JKaiUI.getConfig().getConfigBoolean(PLAYMESSAGESOUND)
                && (JKaiUI.getConfig().getConfigBoolean(SendSound))) {
            JKaiUI.getSoundManager().playMessageSound(Send);
        }

        ChatPanel panel = null;

        if (msg.getType() == ChatMessage.PUBLIC_MESSAGE) {

            // get general arena
            panel = (ChatPanel) chatsTable.get(GENERAL_CHAT);

            ChatOut chat = new ChatOut();
            chat.setMessage(new KaiString(msg.getMessage()));

            ActionExecuter.execute(chat);

        } else if (msg.getType() == ChatMessage.PRIVATE_MESSAGE) {

            User user = msg.getUser();

            PMOut pmOut = new PMOut();
            ArenaPMOut arenaPmOut = new ArenaPMOut();

            panel = getOrCreatePanel(msg.getUser().getUser());

            if (!userIsFriend(user)) {
                // ArenaPM
                arenaPmOut.setUser(new KaiString(msg.getUser().getUser()));
                arenaPmOut.setMessage(new KaiString(msg.getMessage()));

                ActionExecuter.execute(arenaPmOut);

            } else {
                // PM
                pmOut.setUser(new KaiString(msg.getUser().getUser()));
                pmOut.setMessage(new KaiString(msg.getMessage()));

                ActionExecuter.execute(pmOut);
            }
        }

        if (panel == null) {
            _logger.severe("Could not find tab to write message into");
            return;
        }

        panel.write(formatOutMessage(msg));
        Say.speakString(msg.getMessage());
    }

    private String formatOutMessage(OutMessage msg) {

        return formatMessage(JKaiUI.getConfig().getConfigString(TAG), "#ffaaaa", encodeMessage(msg.getMessage()));
    }

    private String formatMessage(String user, String color, String msg) {

        String msgtmp = msg.replaceAll("\n", "<br>");

        String wordbreak = "";
        if (JKaiUI.getConfig().getConfigBoolean(ChatWrap)) {
            wordbreak = "word-break:break-all;";
        }

//        String msgtmp = msg;

        Pattern convURLLinkPtn = Pattern.compile("(http://|https://){1}[\\w\\.\\-/:\\#\\?\\=\\&\\;\\%\\~\\+]+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = convURLLinkPtn.matcher(msgtmp);
        msgtmp = matcher.replaceAll("<a href=\"$0\">$0</a>");

        //userにリンクを追加　リンクをクリックするとチャット入力画面に出せる
        //https://sites.google.com/site/yuuakron/dummy/はダミーアドレス
        String userstr = "<a href=\"https://sites.google.com/site/yuuakron/dummy/" + user + "\">" + user + "</a>";

        String out = "";
        switch (JKaiUI.getConfig().getConfigInt(ChatDisplayStyle)) {
            case 0:
                //JKaiUI
                out = "<table style=\"padding:2px;width:100%;font-family:Dialog;" + wordbreak + "font-size:" + JKaiUI.getConfig().getConfigInt(ChatFontSize) + "px\"><tr style=\"background-color:" + color + "\"><td>" + userstr + "</td><td align=\"right\">";
                if (JKaiUI.getConfig().getConfigBoolean(SHOWTIMESTAMPS)) {
                    Date dat = new Date();
                    String stime = DateFormat.getTimeInstance().format(dat);
                    out += stime;
                }
                out += "</td></tr><tr><td colspan=2>" + msgtmp + "</td></tr></table>";
                break;
            case 2:
                //WebUI風
                out = "<table style=\"width:100%;font-family:Dialog;" + wordbreak + "font-size:" + JKaiUI.getConfig().getConfigInt(ChatFontSize) + "px\"><tr>";
                if (JKaiUI.getConfig().getConfigBoolean(SHOWTIMESTAMPS)) {
                    Date dat = new Date();
                    String stime = DateFormat.getTimeInstance().format(dat);
                    out += "<td style=\"background-color:" + color + ";width:" + (JKaiUI.getConfig().getConfigInt(ChatFontSize) * 7) + "\">" + stime + " </td>";
                }
                out += "<td style=\"background-color:" + color + ";width:" + (JKaiUI.getConfig().getConfigInt(ChatFontSize) * 14) + "\">" + userstr + "</td>";
                out += "<td>" + msgtmp + "</td></tr></table>";
                break;
            case 1:
                //GUI風
                out = "<span style=\"font-family:Dialog;background-color:" + color + ";font-size:" + JKaiUI.getConfig().getConfigInt(ChatFontSize) + "px\">";
                if (JKaiUI.getConfig().getConfigBoolean(SHOWTIMESTAMPS)) {
                    Date dat = new Date();
                    String stime = DateFormat.getTimeInstance().format(dat);
                    out += stime + " ";
                }
                out += userstr + "</span>";
                out += "<span style=\"font-family:Dialog;" + wordbreak + "font-size:" + JKaiUI.getConfig().getConfigInt(ChatFontSize) + "px\"> : " + msgtmp + "</span>";
                break;
            default:
                break;
        }

//        System.out.println(out);

        return out;
    }

    private String encodeMessage(String s) {
        String str = s;

        str = str.replace("&", "&amp;");
        str = str.replace(">", "&gt;");
        str = str.replace("<", "&lt;");

        str = EmoticonManager.getInstance().encodeEmotIcon(str);

        return str;
    }

    private String getColor(String s) {
        if (JKaiUI.getConfig().getConfigBoolean(ColorBackground)) {

            char[] array = s.toCharArray();

            int sum = 0;

            for (int i = 0; i < array.length; i++) {

                sum += array[i];

            }

            String color = COLORS[sum % COLORS.length];
            //_logger.fine("Color: " + color );
            return color;
        } else {
            return "transparent";
        }
    }

    private ChatPanel getOrCreatePanel(String name) {


        ChatPanel panel;

        if (chatsTable.get(name) == null) {

            //PMパネルを開いた場合に音を鳴らすように変更
            if (JKaiUI.getConfig().getConfigBoolean(PLAYMESSAGESOUND)
                    && (JKaiUI.getConfig().getConfigBoolean(PMOpenSound))) {
                JKaiUI.getSoundManager().playMessageSound(PMOpen);
            }

            panel = new ChatPanel();
            panel.setName(name);
            panel.addToMainUI();
            panel.setVisibleComboSendEncoding(true);
            enableIcon(panel);

            chatsTable.put(name, panel);
        } else if (((ChatPanel) chatsTable.get(name)).containMainUI()) {
            panel = (ChatPanel) chatsTable.get(name);
        } else {
            //PMパネルを開いた場合に音を鳴らすように変更
            if (JKaiUI.getConfig().getConfigBoolean(PLAYMESSAGESOUND)
                    && (JKaiUI.getConfig().getConfigBoolean(PMOpenSound))) {
                JKaiUI.getSoundManager().playMessageSound(PMOpen);
            }

            panel = (ChatPanel) chatsTable.get(name);

            panel.addToMainUI();
        }

        return panel;
    }

    public void closeChat(ChatPanel chat) {

        // Remove from hash

//        chatsTable.remove(chat.getName());

        chat.removeFromMainUI();

    }

    public void openChat(User user) {

        getOrCreatePanel(user.getUser());

    }

    public void enterRoom(String arena) {

        // Notify that user has entered the arena
        ChatPanel panel = (ChatPanel) chatsTable.get(GENERAL_CHAT);

        panel.write("<div style=\"color:black; font-size:" + JKaiUI.getConfig().getConfigInt(SystemFontSize) + "px;\"> -- " + java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("MSG_CurrentArena") + ": " + arena + "</div>");
        JKaiUI.getMainUI().setTitle("JKaiUI: " + arena);

    }

    public void joinsRoom(String user) {

        // Notify that user has entered the arena
        ChatPanel panel = (ChatPanel) chatsTable.get(GENERAL_CHAT);

        //panel.write("<font size=\"-1\" color=\"#cccccc\">   --&gt; "+ java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("MSG_JoinsChat") +": " + user + "</font>" );
        //_logger.config("  --&gt; "+ java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("MSG_JoinsChat") +": " + user );

    }

    public void leavesRoom(String user) {

        // Notify that user has entered the arena
        ChatPanel panel = (ChatPanel) chatsTable.get(GENERAL_CHAT);

        //panel.write("<font size=\"-1\" color=\"#cccccc\">   &lt;-- "+ java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("MSG_LeavesChat") +": " + user + "</font>" );
        //_logger.config("  &lt;-- "+ java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("MSG_LeavesChat") +": " + user );


    }

    private boolean userIsFriend(User user) {

        MessengerModeListModel model = (MessengerModeListModel) JKaiUI.getMessengerMode().list.getModel();
        return model.contains(user);

    }

    public void enableIcon(ChatPanel panel) {

        JTabbedPane pane = JKaiUI.getMainUI().jTabbedPane;
        pane.setIconAt(pane.indexOfComponent(panel), NOTIFY_ICON);

    }

    public void disableIcon(ChatPanel panel) {

        JTabbedPane pane = JKaiUI.getMainUI().jTabbedPane;
        pane.setIconAt(pane.indexOfComponent(panel), UNNOTIFY_ICON);

    }

    public void enableIconIfSelected(ChatPanel panel) {

        JTabbedPane pane = JKaiUI.getMainUI().jTabbedPane;
        if (!pane.getSelectedComponent().equals(panel)) {
            enableIcon(panel);
        }

    }

    public void loginFriend(ContactOnline friend) {
        // Notify that user has entered the arena
        Object[] panels = chatsTable.values().toArray();
//        ChatPanel panel = (ChatPanel) chatsTable.get(GENERAL_CHAT);
        for (int i = 0; i < panels.length; i++) {
            ChatPanel panel = (ChatPanel) panels[i];

            panel.write("<div style=\"color:black; font-size:" + JKaiUI.getConfig().getConfigInt(SystemFontSize) + "px;\"> -- " + friend.getUser().decode() + " is logged in" + "</div>");
        }
        //フレンドのログイン時に音を鳴らす
        if (JKaiUI.getConfig().getConfigBoolean(PLAYMESSAGESOUND)
                && (JKaiUI.getConfig().getConfigBoolean(FriendOnlineSound))) {
            JKaiUI.getSoundManager().playMessageSound(FriendOnline);
        }
    }

    public void logoutFriend(ContactOffline friend) {

        // Notify that user has entered the arena
//        ChatPanel panel = (ChatPanel) chatsTable.get(GENERAL_CHAT);

//        panel.write("<div style=\"color:black; font-size:" + JKaiUI.getConfig().getConfigInt(SystemFontSize) + "px;\"> -- " + friend.getUser().decode() + " is logged out" + "</div>");

        Object[] panels = chatsTable.values().toArray();
//        ChatPanel panel = (ChatPanel) chatsTable.get(GENERAL_CHAT);
        for (int i = 0; i < panels.length; i++) {
            ChatPanel panel = (ChatPanel) panels[i];

            panel.write("<div style=\"color:black; font-size:" + JKaiUI.getConfig().getConfigInt(SystemFontSize) + "px;\"> -- " + friend.getUser().decode() + " is logged out" + "</div>");
        }
    }

    public String getSelectedEncoding(String username) {
        return this.getOrCreatePanel(username).getSelectedCoding();
    }

    public void setInputFieldFontSize(int size) {
        Enumeration elems = chatsTable.elements();

        while (elems.hasMoreElements()) {
            ((ChatPanel) elems.nextElement()).jTextFieldInput.setFont(new java.awt.Font("Dialog", 0, size));
        }
    }

    private void addchathistory(OutMessage msg) {

//        if(chathistory.size() <= 0){
//            chathistoryiter = chathistory.listIterator();
//        }

        chathistory.add(msg.getMessage());
        if (chathistory.size() > JKaiUI.getConfig().getConfigInt(MaxChatHistory)) {
            chathistory.remove(0);
        }
        chathistoryindex = chathistory.size() - 1;
    }

    public synchronized String nextchathistory() {
        if (chathistory.size() > 0) {
            if (chathistory.size() > chathistoryindex && 0 <= chathistoryindex) {
                chathistoryindex++;
                return chathistory.get(chathistoryindex - 1);
            } else if (chathistoryindex == -1) {
                chathistoryindex++;
            } else {
//                chathistoryindex--;
                return "";
            }
        }
        return null;
    }

    public synchronized String previouschathistory() {
        if (chathistory.size() > 0) {
            if (0 <= chathistoryindex && chathistory.size() > chathistoryindex) {
                chathistoryindex--;
                return chathistory.get(chathistoryindex + 1);//現在値を送信
            } else if (chathistory.size() == chathistoryindex) {
                chathistoryindex--;
            } else {
//                chathistoryindex++;
                return "";
            }
        }
        return null;
    }
}
