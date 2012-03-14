/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.jkaiui.manager;

import java.io.File;
import java.net.URL;
import javax.sound.sampled.*;
import pt.jkaiui.JKaiUI;
import static pt.jkaiui.core.KaiConfig.ConfigTag.*;

/**
 *
 * @author yuuakron
 */
public class SoundManager {

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
    
    private URL[] MessageSoundUrl;

    public SoundManager() {
        init();
    }
    
    public void init(){
        MessageSoundUrl = new URL[10];
        File soundfileholder = new File(JKaiUI.getConfig().getConfigSettingFolder() + "/sound");
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }
}
