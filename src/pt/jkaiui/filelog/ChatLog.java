/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.jkaiui.filelog;

import pt.jkaiui.core.messages.*;

/**
 *
 * @author yuu@akron
 */
public class ChatLog extends Log {

    private String xtag;
    
    public ChatLog(String file, String pattern, String xtag) {
        this.xtag = xtag;
        super.init(file, pattern);
    }

    @Override
    public void println(Object chat, Object CurrentArena) {
        if (!datecheck()) {
            update();
        }

        String p = pattern.replace("%T", now());
        if (chat instanceof Chat) {
            Chat chattmp = (Chat) chat;
            p = p.replace("%K", chat.getClass().getName());
            p = p.replace("%R", (String) CurrentArena);
            p = p.replace("%S", chattmp.getUser().decode());
            p = p.replace("%M", chattmp.getMessage().decode());
        }
        if (chat instanceof Chat2) {
            Chat2 chattmp = (Chat2) chat;
            p = p.replace("%K", chat.getClass().getName());
            p = p.replace("%R", (String) CurrentArena);
            p = p.replace("%S", chattmp.getUser().decode());
            p = p.replace("%M", chattmp.getMessage().decode());
        }
        if (chat instanceof ChatOut) {
            ChatOut chattmp = (ChatOut) chat;
            p = p.replace("%K", chat.getClass().getName());
            p = p.replace("%R", (String) CurrentArena);
            p = p.replace("%S", xtag);
            p = p.replace("%M", chattmp.getMessage().decode());
        }
        logfilepw.println(p);
    }

    @Override
    public void println(Object chat) {
        if (!datecheck()) {
            update();
        }

        String p = pattern.replace("%T", now());
        if (chat instanceof PM) {
            PM chattmp = (PM) chat;
            p = p.replace("%K", chat.getClass().getName());
            p = p.replace("%R", xtag);
            p = p.replace("%S", chattmp.getUser().decode());
            p = p.replace("%M", chattmp.getMessage().decode());
        }
        if (chat instanceof PMOut) {
            PMOut chattmp = (PMOut) chat;
            p = p.replace("%K", chat.getClass().getName());
            p = p.replace("%R", chattmp.getUser().decode());
            p = p.replace("%S", xtag);
            p = p.replace("%M", chattmp.getMessage().decode());
        }
        if (chat instanceof ArenaPM) {
            ArenaPM chattmp = (ArenaPM) chat;
            p = p.replace("%K", chat.getClass().getName());
            p = p.replace("%R", xtag);
            p = p.replace("%S", chattmp.getUser().decode());
            p = p.replace("%M", chattmp.getMessage().decode());
        }
        if (chat instanceof ArenaPMOut) {
            ArenaPMOut chattmp = (ArenaPMOut) chat;
            p = p.replace("%K", chat.getClass().getName());
            p = p.replace("%R", chattmp.getUser().decode());
            p = p.replace("%S", xtag);
            p = p.replace("%M", chattmp.getMessage().decode());
        }
        logfilepw.println(p);
    }
}
