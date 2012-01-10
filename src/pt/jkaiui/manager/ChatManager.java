/*
 * ChatManager.java
 *
 * Created on November 27, 2004, 9:05 PM
 */
package pt.jkaiui.manager;

import java.text.DateFormat;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.URL;
import java.io.File;
import javax.swing.AbstractListModel;

import pt.jkaiui.JKaiUI;
import pt.jkaiui.core.ChatMessage;
import pt.jkaiui.core.InMessage;
import pt.jkaiui.core.KaiString;
import pt.jkaiui.core.OutMessage;
import pt.jkaiui.core.User;
import pt.jkaiui.core.Arena;
import pt.jkaiui.core.messages.ArenaPMOut;
import pt.jkaiui.core.messages.ChatOut;
import pt.jkaiui.core.messages.PMOut;
import pt.jkaiui.core.messages.ContactOnline;
import pt.jkaiui.core.messages.ContactOffline;
import pt.jkaiui.tools.log.ConfigLog;
import pt.jkaiui.ui.ChatPanel;
import pt.jkaiui.ui.modes.MessengerModeListModel;
import pt.jkaiui.core.Diags;
import pt.jkaiui.core.messages.KaiVectorOut;
import static pt.jkaiui.core.KaiConfig.ConfigTag.*;
import pt.jkaiui.core.KaiConfig.ConfigTag;
import pt.jkaiui.core.messages.CreateVectorOut;
import pt.jkaiui.ui.KaiSettingsPanel;
import java.awt.Component;

/**
 *
 * @author  pedro
 */
public class ChatManager {

    public static final String GENERAL_CHAT = "\1";
    private static Logger _logger;
    private Hashtable chatsTable;
    private String url;
    private final String[] COLORS = {"#CCCCCC", "#B7FFF1", "#FFE8A9", "#FF7568", "#FFB3CC", "#FFB7FF", "#81C182", "#C6FF5C", "#C8EFFF", "#CEEBFF", "#979EC1", "#BBBBBB", "#BEDEEA"};
    private final ImageIcon NOTIFY_ICON = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/notify.png"));
    private final ImageIcon UNNOTIFY_ICON = new ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/unnotify.png"));
//    private final URL MessageSoundUrl = getClass().getResource("/pt/jkaiui/ui/resources/sound/message.wav");
    private URL[] MessageSoundUrl;
    private LinkedList chathistory;
    private int chathistoryindex;
    private ArrayList EmotIcons;

    public enum SoundKinds {

        Chat,
        PMOpen,
        FriendPM,
        FriendChat,
        FriendOnline,
        ArenaPM,
        ModeratorChat,
        Send
    }

    /** Creates a new instance of ChatManager */
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

        // Load local info!
        String className = this.getClass().getName().replace('.', '/');
        String classPath = this.getClass().getResource("/" + className + ".class").toString();
        String urls[] = classPath.split(className + ".class");
        url = urls[0].substring(0, urls[0].length() - 1);

        //_logger.fine("URL (this should point to jar file:" + url);
        initSoundFile();

        chathistory = new LinkedList();
        chathistoryindex = 0;
        initEmotIcon();
    }

    public void initSoundFile() {
        MessageSoundUrl = new URL[10];
        File soundfileholder = new File("./sound/");
//        System.out.println("sf:" + soundfileholder.getPath());
        try {
            if (!soundfileholder.exists()) {
                MessageSoundUrl[SoundKinds.Chat.ordinal()] = getClass().getResource("/pt/jkaiui/ui/resources/sound/message.wav");
                MessageSoundUrl[SoundKinds.PMOpen.ordinal()] = getClass().getResource("/pt/jkaiui/ui/resources/sound/message.wav");
                MessageSoundUrl[SoundKinds.FriendPM.ordinal()] = getClass().getResource("/pt/jkaiui/ui/resources/sound/message.wav");
                MessageSoundUrl[SoundKinds.FriendChat.ordinal()] = getClass().getResource("/pt/jkaiui/ui/resources/sound/message.wav");
                MessageSoundUrl[SoundKinds.FriendOnline.ordinal()] = getClass().getResource("/pt/jkaiui/ui/resources/sound/message.wav");
                MessageSoundUrl[SoundKinds.ArenaPM.ordinal()] = getClass().getResource("/pt/jkaiui/ui/resources/sound/message.wav");
                MessageSoundUrl[SoundKinds.ModeratorChat.ordinal()] = getClass().getResource("/pt/jkaiui/ui/resources/sound/message.wav");
                MessageSoundUrl[SoundKinds.Send.ordinal()] = getClass().getResource("/pt/jkaiui/ui/resources/sound/message.wav");
            } else {
                if (JKaiUI.getConfig().getConfigString(ChatSoundFile).equals("default")) {
                    MessageSoundUrl[SoundKinds.Chat.ordinal()] = getClass().getResource("/pt/jkaiui/ui/resources/sound/message.wav");
                } else {
                    MessageSoundUrl[SoundKinds.Chat.ordinal()] = new URL("file:" + soundfileholder.getPath() + "/" + JKaiUI.getConfig().getConfigString(ChatSoundFile));
                }

                if (JKaiUI.getConfig().getConfigString(PMOpenSoundFile).equals("default")) {
                    MessageSoundUrl[SoundKinds.PMOpen.ordinal()] = getClass().getResource("/pt/jkaiui/ui/resources/sound/message.wav");
                } else {
                    MessageSoundUrl[SoundKinds.PMOpen.ordinal()] = new URL("file:" + soundfileholder.getPath() + "/" + JKaiUI.getConfig().getConfigString(PMOpenSoundFile));
                }

                if (JKaiUI.getConfig().getConfigString(FriendPMSoundFile).equals("default")) {
                    MessageSoundUrl[SoundKinds.FriendPM.ordinal()] = getClass().getResource("/pt/jkaiui/ui/resources/sound/message.wav");
                } else {
                    MessageSoundUrl[SoundKinds.FriendPM.ordinal()] = new URL("file:" + soundfileholder.getPath() + "/" + JKaiUI.getConfig().getConfigString(FriendPMSoundFile));
                }

                if (JKaiUI.getConfig().getConfigString(FriendChatSoundFile).equals("default")) {
                    MessageSoundUrl[SoundKinds.FriendChat.ordinal()] = getClass().getResource("/pt/jkaiui/ui/resources/sound/message.wav");
                } else {
                    MessageSoundUrl[SoundKinds.FriendChat.ordinal()] = new URL("file:" + soundfileholder.getPath() + "/" + JKaiUI.getConfig().getConfigString(FriendChatSoundFile));
                }

                if (JKaiUI.getConfig().getConfigString(FriendOnlineSoundFile).equals("default")) {
                    MessageSoundUrl[SoundKinds.FriendOnline.ordinal()] = getClass().getResource("/pt/jkaiui/ui/resources/sound/message.wav");
                } else {
                    MessageSoundUrl[SoundKinds.FriendOnline.ordinal()] = new URL("file:" + soundfileholder.getPath() + "/" + JKaiUI.getConfig().getConfigString(FriendOnlineSoundFile));
                }
                if (JKaiUI.getConfig().getConfigString(ArenaPMSoundFile).equals("default")) {
                    MessageSoundUrl[SoundKinds.ArenaPM.ordinal()] = getClass().getResource("/pt/jkaiui/ui/resources/sound/message.wav");
                } else {
                    MessageSoundUrl[SoundKinds.ArenaPM.ordinal()] = new URL("file:" + soundfileholder.getPath() + "/" + JKaiUI.getConfig().getConfigString(ArenaPMSoundFile));
                }

                if (JKaiUI.getConfig().getConfigString(ModeratorChatSoundFile).equals("default")) {
                    MessageSoundUrl[SoundKinds.ModeratorChat.ordinal()] = getClass().getResource("/pt/jkaiui/ui/resources/sound/message.wav");
                } else {
                    MessageSoundUrl[SoundKinds.ModeratorChat.ordinal()] = new URL("file:" + soundfileholder.getPath() + "/" + JKaiUI.getConfig().getConfigString(ModeratorChatSoundFile));
                }

                if (JKaiUI.getConfig().getConfigString(SendSoundFile).equals("default")) {
                    MessageSoundUrl[SoundKinds.Send.ordinal()] = getClass().getResource("/pt/jkaiui/ui/resources/sound/message.wav");
                } else {
                    MessageSoundUrl[SoundKinds.Send.ordinal()] = new URL("file:" + soundfileholder.getPath() + "/" + JKaiUI.getConfig().getConfigString(SendSoundFile));
                }
            }

        } catch (Exception e) {
            System.out.println("SoundFile Open Error" + e);
        }
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
            String texttmp = JKaiUIAskcommand(msg);
            if (texttmp == null) {
                return;
            }
//            msg.setMessage(texttmp);
        }

        if (msg.getType() == ChatMessage.PUBLIC_MESSAGE) {

            // get general arena
            panel = (ChatPanel) chatsTable.get(GENERAL_CHAT);

            //�`���b�g�ŉ���炷
            if (JKaiUI.getConfig().getConfigBoolean(PLAYMESSAGESOUND) && !msg.getUser().getUser().equalsIgnoreCase("kai orbital mesh")
                    && (JKaiUI.getConfig().getConfigBoolean(ChatSound))) {
                playMessageSound(SoundKinds.Chat);
            }
            //�t�����h�̔������ɉ����Ȃ炷�悤�ɕύX
            if (JKaiUI.getConfig().getConfigBoolean(PLAYMESSAGESOUND)
                    && (JKaiUI.getConfig().getConfigBoolean(FriendChatSound))
                    && (userIsFriend(msg.getUser()))) {
                playMessageSound(SoundKinds.FriendChat);
            }
            //���f�̔������ɉ����Ȃ炷�悤�ɕύX
            if (JKaiUI.getConfig().getConfigBoolean(PLAYMESSAGESOUND)
                    && (JKaiUI.getConfig().getConfigBoolean(ModeratorChatSound))
                    && (msg.getUser().isModerator())) {
                playMessageSound(SoundKinds.ModeratorChat);
            }

        } else if (msg.getType() == ChatMessage.PRIVATE_MESSAGE) {

            panel = getOrCreatePanel(msg.getUser().getUser());

            //PM�������Ƃ������Ȃ炷�悤�ɕύX
            if (JKaiUI.getConfig().getConfigBoolean(PLAYMESSAGESOUND)
                    && (JKaiUI.getConfig().getConfigBoolean(FriendPMSound))
                    && (userIsFriend(msg.getUser()))) {
                playMessageSound(SoundKinds.FriendPM);
            }
            //ArenaPM�������Ƃ������Ȃ炷�悤�ɕύX
            if (JKaiUI.getConfig().getConfigBoolean(PLAYMESSAGESOUND)
                    && (JKaiUI.getConfig().getConfigBoolean(ArenaPMSound))
                    && (!userIsFriend(msg.getUser()))) {
                playMessageSound(SoundKinds.ArenaPM);
            }
            /*            
            //��M�̏ꍇ�͂��ł��@�\on
            //PM��M�̏ꍇ
            try {
            msg.setMessage(HtmlUnicodedecode(msg.getMessage(), 10));
            } catch (Exception e) {
            System.out.println("prossessInMessage:" + e);
            }
             * 
             */
        }

        if (panel == null) {
            _logger.severe("Could not find tab to write message into");
            return;
        }

        panel.write(formatInMessage(msg));
//        panel.write(msg.getMessage());

        // Notify if not selected
        enableIconIfSelected(panel);

    }

    private String formatInMessage(InMessage msg) {

        return formatMessage(msg.getUser().getUser(), getColor(msg.getUser().getUser()), encodeMessage(msg.getMessage()));

    }

    private void processOutMessage(OutMessage msg) {

        addchathistory(msg);

        if (JKaiUI.getConfig().getConfigBoolean(CUICommand)) {
            String texttmp = JKaiUIcommand(msg.getMessage());
            if (texttmp == null) {
                return;
            }
            msg.setMessage(texttmp);
        }

        //�`���b�g���M���ɉ���炷
        if (JKaiUI.getConfig().getConfigBoolean(PLAYMESSAGESOUND)
                && (JKaiUI.getConfig().getConfigBoolean(SendSound))) {
            playMessageSound(SoundKinds.Send);
        }

        ChatPanel panel = null;

        if (msg.getType() == ChatMessage.PUBLIC_MESSAGE) {

            // get general arena
            panel = (ChatPanel) chatsTable.get(GENERAL_CHAT);

            ChatOut chat = new ChatOut();
            chat.setMessage(new KaiString(msg.getMessage()));

            JKaiUI.getManager().getExecuter().execute(chat);

        } else if (msg.getType() == ChatMessage.PRIVATE_MESSAGE) {

            User user = msg.getUser();

            PMOut pmOut = new PMOut();
            ArenaPMOut arenaPmOut = new ArenaPMOut();
            /*
            if (JKaiUI.getConfig().isHtmlUnicode()) {
            try {
            //PM�̏ꍇ�AHTML�G���R�[�h������
            msg.setMessage(HtmlUnicodeencode(msg.getMessage(), 10));
            } catch (Exception e) {
            System.out.println("processOutMessage:"+e);
            }
            }
             */
            panel = getOrCreatePanel(msg.getUser().getUser());

            if (!userIsFriend(user)) {
                // ArenaPM
                arenaPmOut.setUser(new KaiString(msg.getUser().getUser()));
                arenaPmOut.setMessage(new KaiString(msg.getMessage()));

                JKaiUI.getManager().getExecuter().execute(arenaPmOut);

            } else {
                // PM
                pmOut.setUser(new KaiString(msg.getUser().getUser()));
                pmOut.setMessage(new KaiString(msg.getMessage()));

                JKaiUI.getManager().getExecuter().execute(pmOut);
            }
            /*
            if (JKaiUI.getConfig().isHtmlUnicode()) {
            try {
            //PM�̏ꍇ�AHTML�f�R�[�h������
            msg.setMessage(HtmlUnicodedecode(msg.getMessage(), 10));
            } catch (Exception e) {
            System.out.println("processOutMessage:" + e);
            }
            }
             * 
             */
        }

        if (panel == null) {
            _logger.severe("Could not find tab to write message into");
            return;
        }

        panel.write(formatOutMessage(msg));
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
      
        /*
        //html��� ��肠��    
        Pattern p = Pattern.compile("(http|https):([^\\x00-\\x20()\"<>\\x7F-\\xFF])*", Pattern.CASE_INSENSITIVE);

        Matcher m = p.matcher(msgtmp);
//      System.out.println(msgtmp);
        while (m.find()) {
//            System.out.println(m.group());
            String tmp = m.group();
            String tmp2 = m.group().replaceAll("\\?", "\\\\?");
            msgtmp = msgtmp.replaceFirst(tmp2, "<a href=\"" + tmp + "\">" + tmp + "</a>");//���Ƃ��瓯���h���C���̃A�h���X���o�Ă����ꍇ�ɂ��܂������Ȃ�
        }
//      System.out.println(msgtmp);
         * 
         */

        //user�Ƀ����N��ǉ��@�����N���N���b�N����ƃ`���b�g���͉�ʂɏo����
        //https://sites.google.com/site/yuuakron/dummy/�̓_�~�[�A�h���X
        user = "<a href=\"https://sites.google.com/site/yuuakron/dummy/" + user + "\">" + user + "</a>";

        String out = "";
        switch (JKaiUI.getConfig().getConfigInt(ChatDisplayStyle)) {
            case 0:
                //JKaiUI
                out = "<table style=\"padding:2px;width:100%;font-family:Dialog;" + wordbreak + "font-size:" + JKaiUI.getConfig().getConfigInt(ChatFontSize) + "px\"><tr style=\"background-color:" + color + "\"><td>" + user + "</td><td align=\"right\">";
                if (JKaiUI.getConfig().getConfigBoolean(SHOWTIMESTAMPS)) {
                    Date dat = new Date();
                    String stime = DateFormat.getTimeInstance().format(dat);
                    out += stime;
                }
                out += "</td></tr><tr><td colspan=2>" + msgtmp + "</td></tr></table>";
                break;
            case 2:
                //WebUI��
                out = "<table style=\"width:100%;font-family:Dialog;" + wordbreak + "font-size:" + JKaiUI.getConfig().getConfigInt(ChatFontSize) + "px\"><tr>";
                if (JKaiUI.getConfig().getConfigBoolean(SHOWTIMESTAMPS)) {
                    Date dat = new Date();
                    String stime = DateFormat.getTimeInstance().format(dat);
                    out += "<td style=\"background-color:" + color + ";width:" + (JKaiUI.getConfig().getConfigInt(ChatFontSize) * 7) + "\">" + stime + " </td>";
                }
                out += "<td style=\"background-color:" + color + ";width:" + (JKaiUI.getConfig().getConfigInt(ChatFontSize) * 14) + "\">" + user + "</td>";
                out += "<td>" + msgtmp + "</td></tr></table>";
                break;
            case 1:
                //GUI��
                out = "<span style=\"font-family:Dialog;background-color:" + color + ";font-size:" + JKaiUI.getConfig().getConfigInt(ChatFontSize) + "px\">";
                if (JKaiUI.getConfig().getConfigBoolean(SHOWTIMESTAMPS)) {
                    Date dat = new Date();
                    String stime = DateFormat.getTimeInstance().format(dat);
                    out += stime + " ";
                }
                out += user + "</span>";
                out += "<span style=\"font-family:Dialog;" + wordbreak + "font-size:" + JKaiUI.getConfig().getConfigInt(ChatFontSize) + "px\"> : " + msgtmp + "</span>";
                break;
            default:
                break;
        }

//        System.out.println(out);

        return out;
    }

    private void initEmotIcon() {
        EmotIcons = new ArrayList();
        ArrayList tmp = new ArrayList(70);
        
        _logger.severe("url icon:"+url);
        
        //WebUI���璊�o        
        tmp.add(":arrr:,file:./emoticons/arrr.gif");
        tmp.add(":awe:,file:./emoticons/awe.gif");
        tmp.add(":blink:,file:./emoticons/blink.gif");
        tmp.add(":boogie:,file:./emoticons/boogie.gif");
        tmp.add(":bored:,file:./emoticons/bored.gif");
        tmp.add(":censored:,file:./emoticons/censored.gif");
        tmp.add(":cheers:,file:./emoticons/cheers.gif");
        tmp.add(":cheerup:,file:./emoticons/cheerup.gif");
        tmp.add(":cool:,file:./emoticons/cool.gif");
        tmp.add(":cry:,file:./emoticons/cry.gif");
        tmp.add(":dunno:,file:./emoticons/dunno.gif");
        tmp.add(":fu:,file:./emoticons/fu.gif");
        tmp.add(":hahano:,file:./emoticons/hahano.gif");
        tmp.add(":hmm:,file:./emoticons/hmm.gif");
        tmp.add(":huh:,file:./emoticons/huh.gif");
        tmp.add(":ignore:,file:./emoticons/ignore.gif");
        tmp.add(":lick:,file:./emoticons/lick.gif");
        tmp.add(":lol:,file:./emoticons/lol.gif");
        tmp.add(":loser:,file:./emoticons/loser.gif");
        tmp.add(":nana:,file:./emoticons/nana.gif");
        tmp.add(":ninja:,file:./emoticons/ninja.gif");
        tmp.add(":ohmy:,file:./emoticons/ohmy.gif");
        tmp.add(":poke:,file:./emoticons/poke.gif");
        tmp.add(":psycho:,file:./emoticons/psycho.gif");
        tmp.add(":razz:,file:./emoticons/razz.gif");
        tmp.add(":roll:,file:./emoticons/roll.gif");
        tmp.add(":sad:,file:./emoticons/sad.gif");
        tmp.add(":scared:,file:./emoticons/scared.gif");
        tmp.add(":shout:,file:./emoticons/shout.gif");
        tmp.add(":thumbsup:,file:./emoticons/thumbsup.gif");
        tmp.add(":unsure:,file:./emoticons/unsure.gif");
        tmp.add(":yahoo:,file:./emoticons/yahoo.gif");
        tmp.add(":yammer:,file:./emoticons/yammer.gif");
        tmp.add(":yawn:,file:./emoticons/yawn.gif");
        tmp.add(":zzz:,file:./emoticons/zzz.gif");
        tmp.add(":p,file:./emoticons/tongue.gif");
        tmp.add(":P,file:./emoticons/tongue.gif");
        tmp.add(":),file:./emoticons/smile.gif");
        tmp.add(";),file:./emoticons/wink.gif");
        tmp.add(":|,file:./emoticons/mellow.gif");
        tmp.add(":k,file:./emoticons/mkay.gif");
        tmp.add(":D,file:./emoticons/lol.gif");
        tmp.add(":(,file:./emoticons/sad.gif");
        tmp.add("8),file:./emoticons/cool.gif");
        tmp.add(":s,file:./emoticons/confused.gif");
        tmp.add(":S,file:./emoticons/confused.gif");

        for (int i = 0; i < tmp.size(); i++) {
            if (checkEmotIconFile((String) tmp.get(i))) {
                EmotIcons.add(tmp.get(i));
            }
        }
        
        //JKaiUI original �I���W�i���͍Ō�ɏ�������K�v����
        EmotIcons.add(":)," + url + "/pt/jkaiui/ui/resources/emoticons/smile.png");
        EmotIcons.add(":(," + url + "/pt/jkaiui/ui/resources/emoticons/sad.png");
        EmotIcons.add(":*," + url + "/pt/jkaiui/ui/resources/emoticons/kiss.png");
        EmotIcons.add(":p," + url + "/pt/jkaiui/ui/resources/emoticons/tongue.png");
        EmotIcons.add(":@," + url + "/pt/jkaiui/ui/resources/emoticons/angry.png");
        EmotIcons.add(":[," + url + "/pt/jkaiui/ui/resources/emoticons/bat.png");
        EmotIcons.add("(B)," + url + "/pt/jkaiui/ui/resources/emoticons/beer.png");
        EmotIcons.add("(^)," + url + "/pt/jkaiui/ui/resources/emoticons/cake.png");
        EmotIcons.add("(o)," + url + "/pt/jkaiui/ui/resources/emoticons/clock.png");
        EmotIcons.add("(D)," + url + "/pt/jkaiui/ui/resources/emoticons/cocktail.png");
        EmotIcons.add(":|," + url + "/pt/jkaiui/ui/resources/emoticons/confused.png");
        EmotIcons.add(":'(," + url + "/pt/jkaiui/ui/resources/emoticons/cry.png");
        EmotIcons.add("(D)," + url + "/pt/jkaiui/ui/resources/emoticons/cocktail.png");
        EmotIcons.add("(C)," + url + "/pt/jkaiui/ui/resources/emoticons/cup.png");
        EmotIcons.add(":$," + url + "/pt/jkaiui/ui/resources/emoticons/embarassed.png");
        EmotIcons.add("(~)," + url + "/pt/jkaiui/ui/resources/emoticons/film.png");
        EmotIcons.add("(I)," + url + "/pt/jkaiui/ui/resources/emoticons/lightbulb.png");
        EmotIcons.add("(8)," + url + "/pt/jkaiui/ui/resources/emoticons/note.png");
        EmotIcons.add(":o," + url + "/pt/jkaiui/ui/resources/emoticons/omg.png");
        EmotIcons.add("(g)," + url + "/pt/jkaiui/ui/resources/emoticons/present.png");
        EmotIcons.add("(W)," + url + "/pt/jkaiui/ui/resources/emoticons/rose.png");
        EmotIcons.add("8)," + url + "/pt/jkaiui/ui/resources/emoticons/shade.png");
        EmotIcons.add("(*)," + url + "/pt/jkaiui/ui/resources/emoticons/star.png");
        EmotIcons.add(":D," + url + "/pt/jkaiui/ui/resources/emoticons/teeth.png");
        EmotIcons.add("(Y)," + url + "/pt/jkaiui/ui/resources/emoticons/thumbs_up.png");
        EmotIcons.add("(N)," + url + "/pt/jkaiui/ui/resources/emoticons/thumbs_down.png");
        EmotIcons.add("(U)," + url + "/pt/jkaiui/ui/resources/emoticons/unlove.png");
        EmotIcons.add(";)," + url + "/pt/jkaiui/ui/resources/emoticons/wink.png");
    }

    public ArrayList getEmotIconList() {
        return EmotIcons;
    }

    private boolean checkEmotIconFile(String emoticonstring) {

        String[] tmp = emoticonstring.split(",");

        File emoticonfile = new File(tmp[1].replaceFirst("file:", ""));
        if (emoticonfile.exists() && emoticonfile.canRead()) {
            return true;
        }
        return false;
    }

    private String encodeMessage(String s) {

        s = s.replace("&", "&amp;");
        s = s.replace(">", "&gt;");
        s = s.replace("<", "&lt;");

        //s = s.replace(":)","<img src=\"jar:file:/home/pedro/tex/kai/jkaiui/dist/jKaiUI.jar!/pt/jkaiui/ui/resources/emoticons/smile.png\">");

        s = encodeEmotIcon(s);

//        System.out.println(s);

        return s;

    }

    private String encodeEmotIcon(String s) {
        for (int i = 0; i < EmotIcons.size(); i++) {

            String[] tmp = ((String) EmotIcons.get(i)).split(",");
//            System.out.println(tmp[0] + ":" + tmp[1]);
            s = s.replace(tmp[0], encodeImgTag(tmp[1]));

        }

        return s;
    }

    private String encodeImgTag(String s) {

        s = "<img src=\"" + s + "\">";

        return s;
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


        ChatPanel panel = null;

        if (chatsTable.get(name) == null) {

            //PM�p�l�����J�����ꍇ�ɉ���炷�悤�ɕύX
            if (JKaiUI.getConfig().getConfigBoolean(PLAYMESSAGESOUND)
                    && (JKaiUI.getConfig().getConfigBoolean(PMOpenSound))) {
                playMessageSound(SoundKinds.PMOpen);
            }

            panel = new ChatPanel();
            panel.setName(name);
            panel.addToMainUI();
            panel.setVisibleComboSendEncoding(true);
            enableIcon(panel);

            chatsTable.put(name, panel);
        } else if (((ChatPanel)chatsTable.get(name)).containMainUI()) {
            panel = (ChatPanel) chatsTable.get(name);
        } else {
            //PM�p�l�����J�����ꍇ�ɉ���炷�悤�ɕύX
            if (JKaiUI.getConfig().getConfigBoolean(PLAYMESSAGESOUND)
                    && (JKaiUI.getConfig().getConfigBoolean(PMOpenSound))) {
                playMessageSound(SoundKinds.PMOpen);
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

    public void playMessageSound(SoundKinds sound) {

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(MessageSoundUrl[sound.ordinal()]);
            AudioFormat format = ais.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat(),
                    ((int) ais.getFrameLength() * format.getFrameSize()));
            Clip cl = (Clip) AudioSystem.getLine(info);
            cl.open(ais);
            cl.start();
        } catch (Exception e) {
            System.out.println("playMessageSound:" + e.getMessage());
        }
    }

    public void loginFriend(ContactOnline friend) {
/*
        MessengerModeListModel listmodel = (MessengerModeListModel) JKaiUI.getMessengerMode().getListModel();

        //�ŏ��͕\���A�Q��ڈȍ~�̓G�X�^��ԂȂ�\�����Ȃ�
        for (int i = 0; i < listmodel.size(); i++) {
            Object obj = listmodel.getElementAt(i);
            if (obj instanceof User) {
                User user = (User) obj;
                if (user.getName().equals(friend.getUser().decode())) {
                    System.out.println(user.getPing());
                    if (user.getPing() == 0) {
                        user.setPing(-1);
                    }else if(user.getPing() < 0){
                        return;
                    }
                }
            }
        }
*/
        // Notify that user has entered the arena
        Object[] panels = chatsTable.values().toArray();
//        ChatPanel panel = (ChatPanel) chatsTable.get(GENERAL_CHAT);
        for(int i=0;i < panels.length;i++){
            ChatPanel panel = (ChatPanel)panels[i];

            panel.write("<div style=\"color:black; font-size:" + JKaiUI.getConfig().getConfigInt(SystemFontSize) + "px;\"> -- " + friend.getUser().decode() + " is logged in" + "</div>");
        }
        //�t�����h�̃��O�C�����ɉ���炷
        if (JKaiUI.getConfig().getConfigBoolean(PLAYMESSAGESOUND)
                && (JKaiUI.getConfig().getConfigBoolean(FriendOnlineSound))) {
            playMessageSound(ChatManager.SoundKinds.FriendOnline);
        }
    }

    public void logoutFriend(ContactOffline friend) {

        // Notify that user has entered the arena
//        ChatPanel panel = (ChatPanel) chatsTable.get(GENERAL_CHAT);

//        panel.write("<div style=\"color:black; font-size:" + JKaiUI.getConfig().getConfigInt(SystemFontSize) + "px;\"> -- " + friend.getUser().decode() + " is logged out" + "</div>");

/*        
        MessengerModeListModel listmodel = (MessengerModeListModel) JKaiUI.getMessengerMode().getListModel();

        //�ŏ��͕\���A�Q��ڈȍ~�̓G�X�^��ԂȂ�\�����Ȃ�
        for (int i = 0; i < listmodel.size(); i++) {
            Object obj = listmodel.getElementAt(i);
            if (obj instanceof User) {
                User user = (User) obj;
                if(user.getName().equals(friend.getUser().decode())){
                    System.out.println(user.getPing());
                    if (user.getPing() == 0) {
                        user.setPing(-1);
                    }else if(user.getPing() < 0){
                        return;
                    }
                }
            }
        }
*/        
        Object[] panels = chatsTable.values().toArray();
//        ChatPanel panel = (ChatPanel) chatsTable.get(GENERAL_CHAT);
        for(int i=0;i < panels.length;i++){
            ChatPanel panel = (ChatPanel)panels[i];
            
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
            chathistory.removeFirst();
        }
        chathistoryindex = chathistory.size() - 1;
    }

    public synchronized String nextchathistory() {
        if (chathistory.size() > 0) {
            if (chathistory.size() > chathistoryindex && 0 <= chathistoryindex) {
                chathistoryindex++;
                return (String) chathistory.get(chathistoryindex - 1);
            }else if(chathistoryindex == -1){
                chathistoryindex++;
            }else {
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
                return (String) chathistory.get(chathistoryindex + 1);//���ݒl�𑗐M
            }else if(chathistory.size() == chathistoryindex){ 
                chathistoryindex--;
            }else {
//                chathistoryindex++;
                return "";
            }
        }
        return null;
    }

    private String JKaiUIcommand(String text) {
        String[] tmp = text.split("\\s+");
        
//        Pattern p = Pattern.compile("^%(.*)");
//        Matcher m = p.matcher(text);
        if (tmp[0].matches("^%(.*)")) {
            //System.out.println(m.group(1));
            if (tmp[0].equalsIgnoreCase("%send")) {
                //�R�}���h�̑��M
                return JKaiUICommands(text.replaceFirst("%send\\s+", ""));
            }else if(tmp[0].equalsIgnoreCase("%ask")){
                return text;
            }else {
                //�R�}���h��\��
//                ChatPanel panel = (ChatPanel) chatsTable.get(GENERAL_CHAT);
                ChatPanel panel = (ChatPanel)JKaiUI.getMainUI().jTabbedPane.getSelectedComponent();

                String tmpstr = JKaiUICommands(text.replaceFirst("%", ""));

                if (tmpstr != null) {
                    panel.write("<div style=\"color:black; font-size:" + JKaiUI.getConfig().getConfigInt(SystemFontSize) + "px;\"> -- " + tmpstr + "</div>");
                }
                return null;
            }
        }
        //�ʏ�̃e�L�X�g
        return text;
    }

    private String JKaiUIAskcommand(InMessage msg) {
        String[] tmp = msg.getMessage().split("\\s+");
        
        if (tmp[0].matches("^%(.*)")) {
            if (tmp[0].equalsIgnoreCase("%ask")) {
                //display ask command receive info
                ChatPanel panel = (ChatPanel)JKaiUI.getMainUI().jTabbedPane.getSelectedComponent();
                panel.write("<div style=\"color:black; font-size:" + JKaiUI.getConfig().getConfigInt(SystemFontSize) + "px;\"> -- " + " Received an ask comamnd from "+ msg.getUser() +"  Command:" + msg.getMessage() + "</div>");
                
                //�R�}���h�̑��M
                String sendtext = JKaiUIAskCommands(msg.getMessage().replaceFirst("%ask\\s+", ""));
                
                if (sendtext != null) {

                    OutMessage chat = new OutMessage();

                    if (msg.getType() == ChatMessage.PUBLIC_MESSAGE) {
                        chat.setType(ChatMessage.PUBLIC_MESSAGE);
                    } else if (msg.getType() == ChatMessage.PRIVATE_MESSAGE) {
                        chat.setType(ChatMessage.PRIVATE_MESSAGE);
                    }

                    chat.setMessage(sendtext);
                    chat.setUser(msg.getUser());

                    this.processOutMessage(chat);
                    
                    panel.write("<div style=\"color:black; font-size:" + JKaiUI.getConfig().getConfigInt(SystemFontSize) + "px;\"> -- " + "Replied to the "+ msg.getUser() +"'s ask command   Command:" + msg.getMessage() + "</div>");

                }
                return null;
            }
        }
        //�ʏ�̃e�L�X�g
        return msg.getMessage();
    }
    
    private String JKaiUIAskCommands(String text) {
        String[] tmp = text.split("\\s+");

        if ((tmp[0].equalsIgnoreCase("ver") == true) || (tmp[0].equalsIgnoreCase("diag") == true) || (tmp[0].equalsIgnoreCase("config") == true)) {
            return JKaiUICommands(text);
        }
        
        return null;
    }
    
    private String JKaiUICommands(String text) {
        String[] tmp = text.split("\\s+");

        //show jkaiui version
        if (tmp[0].equalsIgnoreCase("ver")) {
            return JKaiUI.getVersion();
        }
        
        //open setting
        if (tmp[0].equalsIgnoreCase("openconfig")) {
            JKaiUI.getMainUI().jMenuItemSettings.doClick();
            return "open config";
        }
        
        //create private arena
        //create
        //create description�@maxplayernum password
        //create description�@maxplayernum�@(no pass)
        //create description�@(maxplayernum=4)�@�ino pass�j
        if (tmp[0].equalsIgnoreCase("create")) {
            String description = "";
            String password = "";
            int maxPlayers = 4;
            String maxPlayersString = "";

            if (tmp.length == 1) {
                //create
                JKaiUI.getArenaMode().clickcreateArenaButton();
            } else if (tmp.length == 2) {
                //create description�@(maxplayernum=4)�@�ino pass�j
                description = tmp[1];
            } else if (tmp.length == 3) {
                //create description�@maxplayernum�@(no pass)
                description = tmp[1];
                maxPlayersString = tmp[2];
            } else if (tmp.length == 4) {
                //create description�@maxplayernum password
                description = tmp[1];
                maxPlayersString = tmp[2];
                password = tmp[3];
            }

            String error = null;

            if (!maxPlayersString.matches("^\\d{0,2}$")) {

                error = java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("MSG_InvalidMaxPlayers");
            }
            maxPlayers = Integer.parseInt(maxPlayersString.length() > 0 ? maxPlayersString : "0");

            if (!description.matches("^.{0,20}$")) {

                error = java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("MSG_InvalidDescription");
            }

            if (!password.matches("^.{0,8}$")) {

                error = java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("MSG_InvalidPassword");
            }

            if (error != null) {
                return error;
            }

            CreateVectorOut out = new CreateVectorOut();
            out.setDescription(new KaiString(description));
            out.setPassword(new KaiString(password));
            out.setMaxPlayers(maxPlayers);

            JKaiUI.getManager().getExecuter().execute(out);

            return "create arena";
        }

        //change messangermode
        if (tmp[0].equalsIgnoreCase("messengermode")) {
            JKaiUI.getMainUI().jButtonMessengerMode.doClick();
            return "change messengermode";
        }

        //change arenamode
        if (tmp[0].equalsIgnoreCase("arenamode")) {
            JKaiUI.getMainUI().jButtonArenaMode.doClick();
            return "change arenamode";
        }

        //change diagmode
        if (tmp[0].equalsIgnoreCase("diagmode")) {
            JKaiUI.getMainUI().jButtonDiagMode.doClick();
            return "change diagmode";
        }

        //connect to kaiengine
        if (tmp[0].equalsIgnoreCase("connect")) {
            if (JKaiUI.status == JKaiUI.DISCONNECTED) {
                JKaiUI.getMainUI().clickConnectDisconnectButton();
            }
            return "connect to kaiengine";
        }

        //disconnect to kaiengine
        if (tmp[0].equalsIgnoreCase("disconnect")) {
            if (JKaiUI.status == JKaiUI.CONNECTED) {
                JKaiUI.getMainUI().clickConnectDisconnectButton();
            }
            return "disconnect from kaiengine";
        }

        //exit jkaiui
        if (tmp[0].equalsIgnoreCase("exit")) {
            JKaiUI.getMainUI().windowClosing(null);
            return "exit jKaiUI";
        }

        //lock chat window
        if (tmp[0].equalsIgnoreCase("chatlock")) {
            ChatPanel tmppanel = (ChatPanel) JKaiUI.getMainUI().jTabbedPane.getSelectedComponent();
            tmppanel.checklockchat.setSelected(true);
            return "lock chat window";
        }

        //unlock chat window
        if (tmp[0].equalsIgnoreCase("chatunlock")) {
            ChatPanel tmppanel = (ChatPanel) JKaiUI.getMainUI().jTabbedPane.getSelectedComponent();
            tmppanel.checklockchat.setSelected(false);
            return "unlock chat window";
        }

        //reset chat window
        if (tmp[0].equalsIgnoreCase("chatreset")) {
            ChatPanel tmppanel = (ChatPanel) JKaiUI.getMainUI().jTabbedPane.getSelectedComponent();
            tmppanel.buttonReset.doClick();
            return "reset chat window";
        }

        //open logwindow
        if (tmp[0].equalsIgnoreCase("logwindow")) {
            JKaiUI.getMainUI().jMenuItemLog.doClick();
            return "show/hide logwindow";
        }

        //bookmark #1
        if (tmp[0].equalsIgnoreCase("bookmark")) {

            Pattern p2 = Pattern.compile("^#([0-9]+)");
            Matcher m2 = p2.matcher(tmp[1]);

            Arena arena = new Arena();
            if (m2.matches()) {
                if (Integer.parseInt(m2.group(1)) > 0) {
                    Vector BookmarkVector = JKaiUI.getMainUI().bookmarkVector;
                    arena = (Arena) BookmarkVector.elementAt(Integer.parseInt(m2.group(1)) - 1);

                    if (JKaiUI.CURRENT_MODE != JKaiUI.ARENA_MODE) {
                        JKaiUI.getMainUI().jButtonArenaMode.doClick();
                    }
                    JKaiUI.getManager().enterArena(arena);
                }
            }
            return null;
        }

        //pm �t��#1
        if (tmp[0].equalsIgnoreCase("pm")) {

            Pattern p2 = Pattern.compile("^#([0-9]+)");
            Matcher m2 = p2.matcher(tmp[1]);

            if (m2.matches()) {
                MessengerModeListModel listmodel = (MessengerModeListModel) JKaiUI.getMessengerMode().getListModel();

                if (Integer.parseInt(m2.group(1)) > 0) {
                    User user = (User) listmodel.getElementAt(Integer.parseInt(m2.group(1))-1);
                    if (user.isOnline()) {
                        JKaiUI.getChatManager().openChat(user);
                    }
                }
            }
            return null;
        }
        
        //followfriend #1
        if (tmp[0].equalsIgnoreCase("follow")) {

            Pattern p2 = Pattern.compile("^#([0-9]+)");
            Matcher m2 = p2.matcher(tmp[1]);

            if (m2.matches()) {
                MessengerModeListModel listmodel = (MessengerModeListModel) JKaiUI.getMessengerMode().getListModel();

                if (Integer.parseInt(m2.group(1)) > 0) {
                    User user = (User) listmodel.getElementAt(Integer.parseInt(m2.group(1))-1);
                    if (user.isOnline()) {
                        KaiVectorOut vecOut = new KaiVectorOut();
                        vecOut.setVector(new KaiString(user.getCurrentArena()));
                        ActionExecuter.execute(vecOut);
                    }
                }
            }
            return null;
        }
        
        //arenapm #1
        if (tmp[0].equalsIgnoreCase("arenapm")) {

            Pattern p2 = Pattern.compile("^#([0-9]+)");
            Matcher m2 = p2.matcher(tmp[1]);

            if (m2.matches()) {
                MessengerModeListModel listmodel = (MessengerModeListModel) JKaiUI.getArenaMode().getListModel();

                if (Integer.parseInt(m2.group(1)) > 0) {
                    int count = 0; 
                    for (int i = 0; i < listmodel.size(); i++) {
                        Object obj = listmodel.getElementAt(i);
                        if (obj instanceof User) {
                            count++;
                            
                            if (count == Integer.parseInt(m2.group(1))) {
                                User user = (User) obj;
                                if (user.isOnline()) {
                                    JKaiUI.getChatManager().openChat(user);
                                }
                            }
                        }
                    }
                }
            }
            return null;
        }
        
        //help �R�}���h�ꗗ�@�R�}���h����
        if (tmp[0].equalsIgnoreCase("help")) {
            String commands = "send ver help show phrase emoti setconfig config diag go gofull arenapm follow pm bookmark "
                    + "chatreset chatlock cahtunlock logwindow exit connect disconnect messengermode arenamode diagmode "
                    + "create openconifg ask";
            
            return commands;
        }
        
        //show kinds
        if (tmp[0].equalsIgnoreCase("show")) {
            if (tmp.length > 1) {
                if (tmp[1].equalsIgnoreCase("friends")) {
                    MessengerModeListModel listmodel = (MessengerModeListModel) JKaiUI.getMessengerMode().getListModel();
                    
                    StringBuilder str = new StringBuilder();

                    for (int i = 0; i < listmodel.size(); i++) {
                        Object obj = listmodel.getElementAt(i);
                        if (obj instanceof User) {
                            User user = (User) obj;
                            str.append(user.getUser() + " ");
                        }
                    }

                    return str.toString();
                }
                if (tmp[1].equalsIgnoreCase("users")) {
                    MessengerModeListModel listmodel = (MessengerModeListModel) JKaiUI.getArenaMode().getListModel();
                    StringBuilder str = new StringBuilder();
                     
                    for (int i = 0; i < listmodel.size(); i++) {
                        Object obj = listmodel.getElementAt(i);
                        if (obj instanceof User) {
                            User user = (User) obj;
                            str.append(user.getUser()+" ");
                        }
                    }
                    
                    return str.toString();
                }
                if (tmp[1].equalsIgnoreCase("rooms")) {
                    MessengerModeListModel listmodel = (MessengerModeListModel) JKaiUI.getArenaMode().getListModel();
                    StringBuilder str = new StringBuilder();

                    for (int i = 0; i < listmodel.size(); i++) {
                        Object obj = listmodel.getElementAt(i);
                        if (obj instanceof Arena) {
                            Arena arena = (Arena) obj;
                            str.append(arena.getVector() + ":" + arena.getDescription() + " ");
                        }
                    }

                    return str.toString();
                }

                if (tmp[1].equalsIgnoreCase("bookmarks")) {

                    Vector BookmarkVector = JKaiUI.getMainUI().bookmarkVector;
                    return BookmarkVector.toString();
                }
            }
            return null;
        }
        
        //phrase #1
        if (tmp[0].equalsIgnoreCase("phrase")) {

            Pattern p2 = Pattern.compile("^#([0-9]+)");
            Matcher m2 = p2.matcher(tmp[1]);

            if (m2.matches()) {
                if (Integer.parseInt(m2.group(1)) > 0) {
                    AbstractListModel model = (AbstractListModel)JKaiUI.getMainUI().PhraseList.getModel();
                    
                    if (model.getSize() > Integer.parseInt(m2.group(1))-1) {
                        return (String)model.getElementAt(Integer.parseInt(m2.group(1))-1);
                    }
                }
            }
            return null;
        }
                
        //emoti #1
        if (tmp[0].equalsIgnoreCase("emoti")) {

            Pattern p2 = Pattern.compile("^#([0-9]+)");
            Matcher m2 = p2.matcher(tmp[1]);

            String emot = "";
            
            if (m2.matches()) {
                if (Integer.parseInt(m2.group(1)) > 0) {
              
                    emot = ((String) EmotIcons.get(Integer.parseInt(m2.group(1)) - 1)).split(",")[0];
                    ChatPanel tmppane = (ChatPanel)JKaiUI.getMainUI().jTabbedPane.getSelectedComponent();
                    try {
                        tmppane.jTextFieldInput.getDocument().insertString(tmppane.jTextFieldInput.getCaretPosition(), emot, null);
                    } catch (Exception e) {
                        System.out.println("hyperlink emoticon:" + e);
                    }
                }
            }
            return null;
        }

        //setconfig �R�}���h���@�ݒ�l
        if (tmp[0].equalsIgnoreCase("setconfig")) {
            if (tmp.length > 2) {
                try {
                    ConfigTag tag = ConfigTag.valueOf(tmp[1]);
                } catch (Exception e) {
                    System.out.println("error nothing config:" + e);
                }
                try {
                    String str = text.replaceFirst(tmp[0]+"\\s+", "").replaceFirst(tmp[1]+"\\s+", "");//3�Ԗڈȍ~�̒l�����o��
                    JKaiUI.getConfig().loadtoFileConfig(tmp[1] + ":" + str);
                    JKaiUI.getConfig().saveConfig();

                    Component[] children = JKaiUI.getMainUI().jTabbedPane.getComponents();
                    for (int i = 0; i < children.length; i++) {
                        if (children[i] instanceof KaiSettingsPanel) {
                            KaiSettingsPanel pane = (KaiSettingsPanel)children[i];
                            
                            pane.resetValues();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("error config parameter:" + e);
                }
            }
            return null;
        }
        
        //move arena
        //#0 gotoparent
        //#num go to #num room
        //use relative path (cant use full path )
        if (tmp[0].equalsIgnoreCase("go")) {

            Pattern p2 = Pattern.compile("^#([0-9]+)");
            Matcher m2 = p2.matcher(tmp[1]);

            String texttmp = text.replaceFirst(tmp[0]+"\\s+", "");//2�Ԗڈȍ~�̒l�����o��
            
            if (tmp[1].equalsIgnoreCase("parent")) {
                JKaiUI.getArenaMode().clickgoBackButton();

                return null;
            } else if (m2.matches()) {
                if (Integer.parseInt(m2.group(1)) == 0) {
                    JKaiUI.getArenaMode().clickgoBackButton();

                    return null;
                } else {
                    Object tmpobj = ((MessengerModeListModel) JKaiUI.getArenaMode().getListModel()).get(Integer.parseInt(m2.group(1)) - 1);
                    if (tmpobj instanceof Arena) {
                        texttmp = ((Arena) tmpobj).getName();
                    }
                }
            }

            KaiVectorOut vecOut = new KaiVectorOut();
            vecOut.setVector(new KaiString(JKaiUI.ARENA + "/" + texttmp));
            ActionExecuter.execute(vecOut);
            return null;
        }
        
        //move arena
        //use full path (cant use relative path )
        if (tmp[0].equalsIgnoreCase("gofull")) {
            KaiVectorOut vecOut = new KaiVectorOut();
            vecOut.setVector(new KaiString(text.replaceFirst(tmp[0]+"\\s+", "")));
            ActionExecuter.execute(vecOut);
            return null;
        }

        //show diag info
        if (tmp[0].equalsIgnoreCase("diag")) {
            return copydiaginfo(tmp[1]);
        }

        //show config info
        if (tmp[0].equalsIgnoreCase("config")) {
            return JKaiUI.getConfig().copyconfig(tmp[1]);
        }

        return text;
    }
    
private String copydiaginfo(String kinds){
            MessengerModeListModel model = (MessengerModeListModel) JKaiUI.getDiagMode().getListModel();
            String textbuf = "";

            if (kinds.equalsIgnoreCase("all")) {
                StringBuffer strbuf = new StringBuffer("Diags infomation \n\n");//�ۑ�����ݒ���

                strbuf.append(copydiaginfo("server"));
                strbuf.append(copydiaginfo("network"));
                strbuf.append(copydiaginfo("hardware"));
                strbuf.append(copydiaginfo("engine"));
                
                textbuf = strbuf.toString();
            }
            if (kinds.equalsIgnoreCase("server")) {
                Diags diags = (Diags) model.get(0);
                textbuf = new String("OrbServer: " + diags.getValue1());
            }
            if (kinds.equalsIgnoreCase("network")) {
                Diags diags = (Diags) model.get(1);
//                    textbuf = new String("Network: " + diags.getValue1() +" "+ diags.getValue2());
                textbuf = new String("Network: " + diags.getValue2());

            }
//                if (m.group(2).equalsIgnoreCase("ipport")) {
//                    Diags diags = (Diags) model.get(1);
//                    textbuf = new String("Network: " + diags.getValue1());
//                }
            if (kinds.equalsIgnoreCase("reachable")) {
                Diags diags = (Diags) model.get(1);
                textbuf = new String("Network: " + diags.getValue2());
            }
            if (kinds.equalsIgnoreCase("hardware")) {
                Diags diags = (Diags) model.get(2);
                textbuf = new String("Hardware: " + diags.getValue1() + " " + diags.getValue2());
            }
            if (kinds.equalsIgnoreCase("nic")) {
                Diags diags = (Diags) model.get(2);
                textbuf = new String("Hardware: " + diags.getValue1());
            }
            if (kinds.equalsIgnoreCase("lib")) {
                Diags diags = (Diags) model.get(2);
                textbuf = new String("Hardware: " + diags.getValue2());
            }
            if (kinds.equalsIgnoreCase("engine")) {
                Diags diags = (Diags) model.get(3);
                textbuf = new String("Engine: " + diags.getValue1() + " " + diags.getValue2());
            }
            if (kinds.equalsIgnoreCase("ver")) {
                Diags diags = (Diags) model.get(3);
                textbuf = new String("Engine: " + diags.getValue1());
            }
            if (kinds.equalsIgnoreCase("platform")) {
                Diags diags = (Diags) model.get(3);
                textbuf = new String("Engine: " + diags.getValue2());
            }
            return textbuf; 
    }
}
