/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.jkaiui.manager;

import java.io.File;
import java.net.URL;
import javax.sound.sampled.*;
import pt.jkaiui.core.KaiConfig;
import static pt.jkaiui.core.KaiConfig.ConfigTag.*;

/**
 *
 * @author yuuakron
 */
public class SoundManager {

    private static final SoundManager INSTANCE = new SoundManager();
    
    public static SoundManager getInstance(){
        return INSTANCE;
    }
    
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

    private SoundManager() {
        init();
    }
    
    private void init(){
        MessageSoundUrl = new URL[10];
        File soundfileholder = new File(KaiConfig.getInstance().getConfigSettingFolder() + "/sound");
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
                if (KaiConfig.getInstance().getConfigString(ChatSoundFile).equals("default")) {
                    MessageSoundUrl[SoundKinds.Chat.ordinal()] = getClass().getResource("/pt/jkaiui/ui/resources/sound/message.wav");
                } else {
                    MessageSoundUrl[SoundKinds.Chat.ordinal()] = new URL("file:" + soundfileholder.getPath() + "/" + KaiConfig.getInstance().getConfigString(ChatSoundFile));
                }

                if (KaiConfig.getInstance().getConfigString(PMOpenSoundFile).equals("default")) {
                    MessageSoundUrl[SoundKinds.PMOpen.ordinal()] = getClass().getResource("/pt/jkaiui/ui/resources/sound/message.wav");
                } else {
                    MessageSoundUrl[SoundKinds.PMOpen.ordinal()] = new URL("file:" + soundfileholder.getPath() + "/" + KaiConfig.getInstance().getConfigString(PMOpenSoundFile));
                }

                if (KaiConfig.getInstance().getConfigString(FriendPMSoundFile).equals("default")) {
                    MessageSoundUrl[SoundKinds.FriendPM.ordinal()] = getClass().getResource("/pt/jkaiui/ui/resources/sound/message.wav");
                } else {
                    MessageSoundUrl[SoundKinds.FriendPM.ordinal()] = new URL("file:" + soundfileholder.getPath() + "/" + KaiConfig.getInstance().getConfigString(FriendPMSoundFile));
                }

                if (KaiConfig.getInstance().getConfigString(FriendChatSoundFile).equals("default")) {
                    MessageSoundUrl[SoundKinds.FriendChat.ordinal()] = getClass().getResource("/pt/jkaiui/ui/resources/sound/message.wav");
                } else {
                    MessageSoundUrl[SoundKinds.FriendChat.ordinal()] = new URL("file:" + soundfileholder.getPath() + "/" + KaiConfig.getInstance().getConfigString(FriendChatSoundFile));
                }

                if (KaiConfig.getInstance().getConfigString(FriendOnlineSoundFile).equals("default")) {
                    MessageSoundUrl[SoundKinds.FriendOnline.ordinal()] = getClass().getResource("/pt/jkaiui/ui/resources/sound/message.wav");
                } else {
                    MessageSoundUrl[SoundKinds.FriendOnline.ordinal()] = new URL("file:" + soundfileholder.getPath() + "/" + KaiConfig.getInstance().getConfigString(FriendOnlineSoundFile));
                }
                if (KaiConfig.getInstance().getConfigString(ArenaPMSoundFile).equals("default")) {
                    MessageSoundUrl[SoundKinds.ArenaPM.ordinal()] = getClass().getResource("/pt/jkaiui/ui/resources/sound/message.wav");
                } else {
                    MessageSoundUrl[SoundKinds.ArenaPM.ordinal()] = new URL("file:" + soundfileholder.getPath() + "/" + KaiConfig.getInstance().getConfigString(ArenaPMSoundFile));
                }

                if (KaiConfig.getInstance().getConfigString(ModeratorChatSoundFile).equals("default")) {
                    MessageSoundUrl[SoundKinds.ModeratorChat.ordinal()] = getClass().getResource("/pt/jkaiui/ui/resources/sound/message.wav");
                } else {
                    MessageSoundUrl[SoundKinds.ModeratorChat.ordinal()] = new URL("file:" + soundfileholder.getPath() + "/" + KaiConfig.getInstance().getConfigString(ModeratorChatSoundFile));
                }

                if (KaiConfig.getInstance().getConfigString(SendSoundFile).equals("default")) {
                    MessageSoundUrl[SoundKinds.Send.ordinal()] = getClass().getResource("/pt/jkaiui/ui/resources/sound/message.wav");
                } else {
                    MessageSoundUrl[SoundKinds.Send.ordinal()] = new URL("file:" + soundfileholder.getPath() + "/" + KaiConfig.getInstance().getConfigString(SendSoundFile));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void reset(){
        init();
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
