/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.jkaiui.ui.tools;

/**
 *
 * @author yuuakron
 */
public class Say{
    public static void speakString(String speakstr) {
        new CommandExecuter().doCommand("say -v Kyoko " + speakstr);
    }
}