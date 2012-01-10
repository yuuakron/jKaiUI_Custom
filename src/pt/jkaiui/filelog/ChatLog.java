/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.jkaiui.filelog;

import pt.jkaiui.JKaiUI;
import java.io.File;
import pt.jkaiui.core.messages.*;
import static pt.jkaiui.core.KaiConfig.ConfigTag.*;

/**
 *
 * @author yuu@akron
 */
public class ChatLog extends Log {

    public ChatLog() {
        this.init();
    }

    protected void init() {
//        System.out.println("chatlog");
        logfile = new File(format(JKaiUI.getConfig().getConfigString(ChatLogFile)));
        super.init();
    }

    public void println(Object chat, Object CurrentArena) {
        if (!datecheck()) {
            update();
        }

        String pattern = JKaiUI.getConfig().getConfigString(ChatLogPattern);
        pattern = pattern.replace("%T", now());
        if (chat instanceof Chat) {
            Chat chattmp = (Chat) chat;
            pattern = pattern.replace("%K", "Chat");
            pattern = pattern.replace("%R", (String) CurrentArena);
            pattern = pattern.replace("%S", chattmp.getUser().decode());
            pattern = pattern.replace("%M", chattmp.getMessage().decode());
        }
        if (chat instanceof Chat2) {
            Chat2 chattmp = (Chat2) chat;
            pattern = pattern.replace("%K", "Chat2");
            pattern = pattern.replace("%R", (String) CurrentArena);
            pattern = pattern.replace("%S", chattmp.getUser().decode());
            pattern = pattern.replace("%M", chattmp.getMessage().decode());
        }
        if (chat instanceof ChatOut) {
            ChatOut chattmp = (ChatOut) chat;
            pattern = pattern.replace("%K", "ChatOut");
            pattern = pattern.replace("%R", (String) CurrentArena);
            pattern = pattern.replace("%S", JKaiUI.getConfig().getConfigString(TAG));
            pattern = pattern.replace("%M", chattmp.getMessage().decode());
        }
        logfilepw.println(pattern);
    }

    public void println(Object chat) {
        if (!datecheck()) {
            update();
        }

        String pattern = JKaiUI.getConfig().getConfigString(ChatLogPattern);
        pattern = pattern.replace("%T", now());
        if (chat instanceof PM) {
            PM chattmp = (PM) chat;
            pattern = pattern.replace("%K", "PM");
            pattern = pattern.replace("%R", JKaiUI.getConfig().getConfigString(TAG));
            pattern = pattern.replace("%S", chattmp.getUser().decode());
            pattern = pattern.replace("%M", chattmp.getMessage().decode());
        }
        if (chat instanceof PMOut) {
            PMOut chattmp = (PMOut) chat;
            pattern = pattern.replace("%K", "PMOut");
            pattern = pattern.replace("%R", chattmp.getUser().decode());
            pattern = pattern.replace("%S", JKaiUI.getConfig().getConfigString(TAG));
            pattern = pattern.replace("%M", chattmp.getMessage().decode());
        }
        if (chat instanceof ArenaPM) {
            ArenaPM chattmp = (ArenaPM) chat;
            pattern = pattern.replace("%K", "ArenaPM");
            pattern = pattern.replace("%R", JKaiUI.getConfig().getConfigString(TAG));
            pattern = pattern.replace("%S", chattmp.getUser().decode());
            pattern = pattern.replace("%M", chattmp.getMessage().decode());
        }
        if (chat instanceof ArenaPMOut) {
            ArenaPMOut chattmp = (ArenaPMOut) chat;
            pattern = pattern.replace("%K", "ArenaPMOut");
            pattern = pattern.replace("%R", chattmp.getUser().decode());
            pattern = pattern.replace("%S", JKaiUI.getConfig().getConfigString(TAG));
            pattern = pattern.replace("%M", chattmp.getMessage().decode());
        }
        logfilepw.println(pattern);
    }
}
