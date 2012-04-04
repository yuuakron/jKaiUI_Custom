/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.jkaiui.manager;

import java.awt.Component;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractListModel;
import pt.jkaiui.JKaiUI;
import static pt.jkaiui.JKaiUI.Status.CONNECTED;
import static pt.jkaiui.JKaiUI.Status.DISCONNECTED;
import static pt.jkaiui.core.KaiConfig.ConfigTag.SystemFontSize;
import pt.jkaiui.core.*;
import pt.jkaiui.core.messages.*;
import pt.jkaiui.ui.ChatPanel;
import pt.jkaiui.ui.KaiSettingsPanel;
import pt.jkaiui.ui.MainUI;
import pt.jkaiui.ui.modes.MessengerModeListModel;

/**
 *
 * @author yuuakron
 */
//ユーティリティクラス
public class CommandManager {

    private CommandManager() {
    }

    public static String JKaiUIcommand(String text) {
        String[] tmp = text.split("\\s+");

//        Pattern p = Pattern.compile("^%(.*)");
//        Matcher m = p.matcher(text);
        if (tmp[0].matches("^%(.*)")) {
            //System.out.println(m.group(1));
            if (tmp[0].equalsIgnoreCase("%send")) {
                //コマンドの送信
                return JKaiUICommands(text.replaceFirst("%send\\s+", ""));
            } else if (tmp[0].equalsIgnoreCase("%ask")) {
                return text;
            } else {
                //コマンドを表示
//                ChatPanel panel = (ChatPanel) chatsTable.get(GENERAL_CHAT);
                ChatPanel panel = (ChatPanel) MainUI.getInstance().jTabbedPane.getSelectedComponent();

                String tmpstr = JKaiUICommands(text.replaceFirst("%", ""));

                if (tmpstr != null) {
                    panel.write("<div style=\"color:black; font-size:" + KaiConfig.getInstance().getConfigInt(SystemFontSize) + "px;\"> -- " + tmpstr + "</div>");
                }
                return null;
            }
        }
        //通常のテキスト
        return text;
    }

    public static String JKaiUIAskcommand(InMessage msg) {
        String[] tmp = msg.getMessage().split("\\s+");

        if (tmp[0].matches("^%(.*)")) {
            if (tmp[0].equalsIgnoreCase("%ask")) {
                //display ask command receive info
                ChatPanel panel = (ChatPanel) MainUI.getInstance().jTabbedPane.getSelectedComponent();
                panel.write("<div style=\"color:black; font-size:" + KaiConfig.getInstance().getConfigInt(SystemFontSize) + "px;\"> -- " + " Received an ask comamnd from " + msg.getUser() + "  Command:" + msg.getMessage() + "</div>");

                //コマンドの送信
                String sendtext = JKaiUIAskCommands(msg.getMessage().replaceFirst("%ask\\s+", ""));

                if (sendtext != null) {
                    /*
                     * OutMessage chat = new OutMessage();
                     *
                     * if (msg.getType() == ChatMessage.PUBLIC_MESSAGE) {
                     * chat.setType(ChatMessage.PUBLIC_MESSAGE); } else if
                     * (msg.getType() == ChatMessage.PRIVATE_MESSAGE) {
                     * chat.setType(ChatMessage.PRIVATE_MESSAGE); }
                     *
                     * chat.setMessage(sendtext); chat.setUser(msg.getUser());
                     *
                     * this.processOutMessage(chat);
                     */
                    if (msg.getType() == ChatMessage.PUBLIC_MESSAGE) {

                        ChatOut chat = new ChatOut();
                        chat.setMessage(new KaiString(msg.getMessage()));

                        ActionExecuter.execute(chat);

                    } else if (msg.getType() == ChatMessage.PRIVATE_MESSAGE) {

                        User user = msg.getUser();

                        PMOut pmOut = new PMOut();
                        ArenaPMOut arenaPmOut = new ArenaPMOut();

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

                    panel.write("<div style=\"color:black; font-size:" + KaiConfig.getInstance().getConfigInt(SystemFontSize) + "px;\"> -- " + "Replied to the " + msg.getUser() + "'s ask command   Command:" + msg.getMessage() + "</div>");

                }
                return null;
            }
        }
        //通常のテキスト
        return msg.getMessage();
    }

    private static boolean userIsFriend(User user) {

        MessengerModeListModel model = (MessengerModeListModel) JKaiUI.getMessengerMode().list.getModel();
        return model.contains(user);

    }

    private static String JKaiUIAskCommands(String text) {
        String[] tmp = text.split("\\s+");

        if ((tmp[0].equalsIgnoreCase("ver") == true) || (tmp[0].equalsIgnoreCase("diag") == true) || (tmp[0].equalsIgnoreCase("config") == true)) {
            return JKaiUICommands(text);
        }

        return null;
    }

    private static String JKaiUICommands(String text) {
        String[] tmp = text.split("\\s+");

        //show jkaiui version
        if (tmp[0].equalsIgnoreCase("ver")) {
            return JKaiUI.Info.getLongVersion();
        }

        //open setting
        if (tmp[0].equalsIgnoreCase("openconfig")) {
            MainUI.getInstance().jMenuItemSettings.doClick();
            return "open config";
        }

        //create private arena
        //create
        //create description　maxplayernum password
        //create description　maxplayernum　(no pass)
        //create description　(maxplayernum=4)　（no pass）
        if (tmp[0].equalsIgnoreCase("create")) {
            String description = "";
            String password = "";
            int maxPlayers;
            String maxPlayersString = "";

            if (tmp.length == 1) {
                //create
                JKaiUI.getArenaMode().clickcreateArenaButton();
            } else if (tmp.length == 2) {
                //create description　(maxplayernum=4)　（no pass）
                description = tmp[1];
            } else if (tmp.length == 3) {
                //create description　maxplayernum　(no pass)
                description = tmp[1];
                maxPlayersString = tmp[2];
            } else if (tmp.length == 4) {
                //create description　maxplayernum password
                description = tmp[1];
                maxPlayersString = tmp[2];
                password = tmp[3];
            }

            String error = null;

            if (!maxPlayersString.matches("^\\d{0,2}$")) {

                error = java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("MSG_InvalidMaxPlayers");
            }
            maxPlayers = Integer.parseInt(maxPlayersString.length() > 0 ? maxPlayersString : "4");

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

            ActionExecuter.execute(out);

            return "create arena";
        }

        //change messangermode
        if (tmp[0].equalsIgnoreCase("messengermode")) {
            MainUI.getInstance().jButtonMessengerMode.doClick();
            return "change messengermode";
        }

        //change arenamode
        if (tmp[0].equalsIgnoreCase("arenamode")) {
            MainUI.getInstance().jButtonArenaMode.doClick();
            return "change arenamode";
        }

        //change diagmode
        if (tmp[0].equalsIgnoreCase("diagmode")) {
            MainUI.getInstance().jButtonDiagMode.doClick();
            return "change diagmode";
        }

        //connect to kaiengine
        if (tmp[0].equalsIgnoreCase("connect")) {
            if (JKaiUI.getStatus() == DISCONNECTED) {
                MainUI.getInstance().clickConnectDisconnectButton();
            }
            return "connect to kaiengine";
        }

        //disconnect to kaiengine
        if (tmp[0].equalsIgnoreCase("disconnect")) {
            if (JKaiUI.getStatus() == CONNECTED) {
                MainUI.getInstance().clickConnectDisconnectButton();
            }
            return "disconnect from kaiengine";
        }

        //exit jkaiui
        if (tmp[0].equalsIgnoreCase("exit")) {
            MainUI.getInstance().windowClosing(null);
            return "exit jKaiUI";
        }

        //lock chat window
        if (tmp[0].equalsIgnoreCase("chatlock")) {
            ChatPanel tmppanel = (ChatPanel) MainUI.getInstance().jTabbedPane.getSelectedComponent();
            tmppanel.checklockchat.setSelected(true);
            return "lock chat window";
        }

        //unlock chat window
        if (tmp[0].equalsIgnoreCase("chatunlock")) {
            ChatPanel tmppanel = (ChatPanel) MainUI.getInstance().jTabbedPane.getSelectedComponent();
            tmppanel.checklockchat.setSelected(false);
            return "unlock chat window";
        }

        //reset chat window
        if (tmp[0].equalsIgnoreCase("chatreset")) {
            ChatPanel tmppanel = (ChatPanel) MainUI.getInstance().jTabbedPane.getSelectedComponent();
            tmppanel.buttonReset.doClick();
            return "reset chat window";
        }

        //open logwindow
        if (tmp[0].equalsIgnoreCase("logwindow")) {
            MainUI.getInstance().jMenuItemLog.doClick();
            return "show/hide logwindow";
        }

        //bookmark #1
        if (tmp[0].equalsIgnoreCase("bookmark")) {

            Pattern p2 = Pattern.compile("^#([0-9]+)");
            Matcher m2 = p2.matcher(tmp[1]);

            Arena arena;
            if (m2.matches()) {
                if (Integer.parseInt(m2.group(1)) > 0) {
                    Vector BookmarkVector = MainUI.getInstance().bookmarkVector;
                    arena = (Arena) BookmarkVector.elementAt(Integer.parseInt(m2.group(1)) - 1);

                    if (JKaiUI.getCURRENT_MODE() != JKaiUI.Mode.ARENA_MODE) {
                        MainUI.getInstance().jButtonArenaMode.doClick();
                    }
                    Manager.enterArena(arena);
                }
            }
            return null;
        }

        //pm フレ#1
        if (tmp[0].equalsIgnoreCase("pm")) {

            Pattern p2 = Pattern.compile("^#([0-9]+)");
            Matcher m2 = p2.matcher(tmp[1]);

            if (m2.matches()) {
                MessengerModeListModel listmodel = (MessengerModeListModel) JKaiUI.getMessengerMode().getListModel();

                if (Integer.parseInt(m2.group(1)) > 0) {
                    User user = (User) listmodel.getElementAt(Integer.parseInt(m2.group(1)) - 1);
                    if (user.isOnline()) {
                        ChatManager.getInstance().openChat(user);
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
                    User user = (User) listmodel.getElementAt(Integer.parseInt(m2.group(1)) - 1);
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
                                    ChatManager.getInstance().openChat(user);
                                }
                            }
                        }
                    }
                }
            }
            return null;
        }

        //help コマンド一覧　コマンド説明
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
                            str.append(user.getUser());
                            str.append(" ");
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
                            str.append(user.getUser());
                            str.append(" ");
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
                            str.append(arena.getVector());
                            str.append(":");
                            str.append(arena.getDescription());
                            str.append(" ");
                        }
                    }

                    return str.toString();
                }

                if (tmp[1].equalsIgnoreCase("bookmarks")) {

                    Vector BookmarkVector = MainUI.getInstance().bookmarkVector;
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
                    AbstractListModel model = (AbstractListModel) MainUI.getInstance().PhraseList.getModel();

                    if (model.getSize() > Integer.parseInt(m2.group(1)) - 1) {
                        return (String) model.getElementAt(Integer.parseInt(m2.group(1)) - 1);
                    }
                }
            }
            return null;
        }

        //emoti #1
        if (tmp[0].equalsIgnoreCase("emoti")) {

            Pattern p2 = Pattern.compile("^#([0-9]+)");
            Matcher m2 = p2.matcher(tmp[1]);

            String emot;

            if (m2.matches()) {
                if (Integer.parseInt(m2.group(1)) > 0) {

                    emot = (EmoticonManager.getInstance().getEmotIconList().get(Integer.parseInt(m2.group(1)) - 1)).split(",")[0];
                    ChatPanel tmppane = (ChatPanel) MainUI.getInstance().jTabbedPane.getSelectedComponent();
                    try {
                        tmppane.jTextFieldInput.getDocument().insertString(tmppane.jTextFieldInput.getCaretPosition(), emot, null);
                    } catch (Exception e) {
                        System.out.println("hyperlink emoticon:" + e);
                    }
                }
            }
            return null;
        }

        //setconfig コマンド名　設定値
        if (tmp[0].equalsIgnoreCase("setconfig")) {
            if (tmp.length > 2) {
                try {
                    KaiConfig.ConfigTag tag = KaiConfig.ConfigTag.valueOf(tmp[1]);
                } catch (Exception e) {
                    System.out.println("error nothing config:" + e);
                }
                try {
                    String str = text.replaceFirst(tmp[0] + "\\s+", "").replaceFirst(tmp[1] + "\\s+", "");//3番目以降の値を取り出す
                    KaiConfig.getInstance().loadtoFileConfig(tmp[1] + ":" + str);
                    KaiConfig.getInstance().saveConfig();

                    Component[] children = MainUI.getInstance().jTabbedPane.getComponents();
                    for (int i = 0; i < children.length; i++) {
                        if (children[i] instanceof KaiSettingsPanel) {
                            KaiSettingsPanel pane = (KaiSettingsPanel) children[i];

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

            String texttmp = text.replaceFirst(tmp[0] + "\\s+", "");//2番目以降の値を取り出す

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
            vecOut.setVector(new KaiString(JKaiUI.getARENA() + "/" + texttmp));
            ActionExecuter.execute(vecOut);
            return null;
        }

        //move arena
        //use full path (cant use relative path )
        if (tmp[0].equalsIgnoreCase("gofull")) {
            KaiVectorOut vecOut = new KaiVectorOut();
            vecOut.setVector(new KaiString(text.replaceFirst(tmp[0] + "\\s+", "")));
            ActionExecuter.execute(vecOut);
            return null;
        }

        //show diag info
        if (tmp[0].equalsIgnoreCase("diag")) {
            return copydiaginfo(tmp[1]);
        }

        //show config info
        if (tmp[0].equalsIgnoreCase("config")) {
            return KaiConfig.getInstance().copyconfig(tmp[1]);
        }

        return text;
    }

    private static String copydiaginfo(String kinds) {
        MessengerModeListModel model = (MessengerModeListModel) JKaiUI.getDiagMode().getListModel();
        String textbuf = "";

        if (kinds.equalsIgnoreCase("all")) {
            StringBuilder strbuf = new StringBuilder("Diags infomation \n\n");//保存する設定情報

            strbuf.append(copydiaginfo("server"));
            strbuf.append(copydiaginfo("network"));
            strbuf.append(copydiaginfo("hardware"));
            strbuf.append(copydiaginfo("engine"));

            textbuf = strbuf.toString();
        }
        if (kinds.equalsIgnoreCase("server")) {
            Diags diags = (Diags) model.get(0);
            textbuf = "OrbServer: " + diags.getValue1();
        }
        if (kinds.equalsIgnoreCase("network")) {
            Diags diags = (Diags) model.get(1);
//                    textbuf = new String("Network: " + diags.getValue1() +" "+ diags.getValue2());
            textbuf = "Network: " + diags.getValue2();

        }
//                if (m.group(2).equalsIgnoreCase("ipport")) {
//                    Diags diags = (Diags) model.get(1);
//                    textbuf = new String("Network: " + diags.getValue1());
//                }
        if (kinds.equalsIgnoreCase("reachable")) {
            Diags diags = (Diags) model.get(1);
            textbuf = "Network: " + diags.getValue2();
        }
        if (kinds.equalsIgnoreCase("hardware")) {
            Diags diags = (Diags) model.get(2);
            textbuf = "Hardware: " + diags.getValue1() + " " + diags.getValue2();
        }
        if (kinds.equalsIgnoreCase("nic")) {
            Diags diags = (Diags) model.get(2);
            textbuf = "Hardware: " + diags.getValue1();
        }
        if (kinds.equalsIgnoreCase("lib")) {
            Diags diags = (Diags) model.get(2);
            textbuf = "Hardware: " + diags.getValue2();
        }
        if (kinds.equalsIgnoreCase("engine")) {
            Diags diags = (Diags) model.get(3);
            textbuf = "Engine: " + diags.getValue1() + " " + diags.getValue2();
        }
        if (kinds.equalsIgnoreCase("ver")) {
            Diags diags = (Diags) model.get(3);
            textbuf = "Engine: " + diags.getValue1();
        }
        if (kinds.equalsIgnoreCase("platform")) {
            Diags diags = (Diags) model.get(3);
            textbuf = "Engine: " + diags.getValue2();
        }
        return textbuf;
    }
}
