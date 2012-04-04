/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.jkaiui.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import pt.jkaiui.JKaiUI;
import pt.jkaiui.core.KaiConfig;

/**
 *
 * @author yuuakron
 */
//シングルトン
public class EmoticonManager {

    private List<String> EmotIcons;
    private static final EmoticonManager Instance = new EmoticonManager();
    
    public static EmoticonManager getInstance(){
        return Instance;
    }
    
    private EmoticonManager(){
        initEmotIcon();
    }
    
    private void initEmotIcon() {
        
        String className = this.getClass().getName().replace('.', '/');
        String classPath = this.getClass().getResource("/" + className + ".class").toString();
        String url = classPath.replace("/"+className + ".class", "");
        
        EmotIcons = new ArrayList<String>();
        List<String> tmp = new ArrayList<String>(50);

//        _logger.log(Level.SEVERE, "url icon:{0}", url);

        String settingfolder = KaiConfig.getInstance().getConfigSettingFolder() + "/";

        //WebUIから抽出        
        tmp.add(":arrr:,file:" + settingfolder + "emoticons/arrr.gif");
        tmp.add(":awe:,file:" + settingfolder + "emoticons/awe.gif");
        tmp.add(":blink:,file:" + settingfolder + "emoticons/blink.gif");
        tmp.add(":boogie:,file:" + settingfolder + "emoticons/boogie.gif");
        tmp.add(":bored:,file:" + settingfolder + "emoticons/bored.gif");
        tmp.add(":censored:,file:" + settingfolder + "emoticons/censored.gif");
        tmp.add(":cheers:,file:" + settingfolder + "emoticons/cheers.gif");
        tmp.add(":cheerup:,file:" + settingfolder + "emoticons/cheerup.gif");
        tmp.add(":cool:,file:" + settingfolder + "emoticons/cool.gif");
        tmp.add(":cry:,file:" + settingfolder + "emoticons/cry.gif");
        tmp.add(":dunno:,file:" + settingfolder + "emoticons/dunno.gif");
        tmp.add(":fu:,file:" + settingfolder + "emoticons/fu.gif");
        tmp.add(":hahano:,file:" + settingfolder + "emoticons/hahano.gif");
        tmp.add(":hmm:,file:" + settingfolder + "emoticons/hmm.gif");
        tmp.add(":huh:,file:" + settingfolder + "emoticons/huh.gif");
        tmp.add(":ignore:,file:" + settingfolder + "emoticons/ignore.gif");
        tmp.add(":lick:,file:" + settingfolder + "emoticons/lick.gif");
        tmp.add(":lol:,file:" + settingfolder + "emoticons/lol.gif");
        tmp.add(":loser:,file:" + settingfolder + "emoticons/loser.gif");
        tmp.add(":nana:,file:" + settingfolder + "emoticons/nana.gif");
        tmp.add(":ninja:,file:" + settingfolder + "emoticons/ninja.gif");
        tmp.add(":ohmy:,file:" + settingfolder + "emoticons/ohmy.gif");
        tmp.add(":poke:,file:" + settingfolder + "emoticons/poke.gif");
        tmp.add(":psycho:,file:" + settingfolder + "emoticons/psycho.gif");
        tmp.add(":razz:,file:" + settingfolder + "emoticons/razz.gif");
        tmp.add(":roll:,file:" + settingfolder + "emoticons/roll.gif");
        tmp.add(":sad:,file:" + settingfolder + "emoticons/sad.gif");
        tmp.add(":scared:,file:" + settingfolder + "emoticons/scared.gif");
        tmp.add(":shout:,file:" + settingfolder + "emoticons/shout.gif");
        tmp.add(":thumbsup:,file:" + settingfolder + "emoticons/thumbsup.gif");
        tmp.add(":unsure:,file:" + settingfolder + "emoticons/unsure.gif");
        tmp.add(":yahoo:,file:" + settingfolder + "emoticons/yahoo.gif");
        tmp.add(":yammer:,file:" + settingfolder + "emoticons/yammer.gif");
        tmp.add(":yawn:,file:" + settingfolder + "emoticons/yawn.gif");
        tmp.add(":zzz:,file:" + settingfolder + "emoticons/zzz.gif");
        tmp.add(":p,file:" + settingfolder + "emoticons/tongue.gif");
        tmp.add(":P,file:" + settingfolder + "emoticons/tongue.gif");
        tmp.add(":),file:" + settingfolder + "emoticons/smile.gif");
        tmp.add(";),file:" + settingfolder + "emoticons/wink.gif");
        tmp.add(":|,file:" + settingfolder + "emoticons/mellow.gif");
        tmp.add(":k,file:" + settingfolder + "emoticons/mkay.gif");
        tmp.add(":D,file:" + settingfolder + "emoticons/lol.gif");
        tmp.add(":(,file:" + settingfolder + "emoticons/sad.gif");
        tmp.add("8),file:" + settingfolder + "emoticons/cool.gif");
        tmp.add(":s,file:" + settingfolder + "emoticons/confused.gif");
        tmp.add(":S,file:" + settingfolder + "emoticons/confused.gif");

        for (int i = 0; i < tmp.size(); i++) {
            if (checkEmotIconFile(tmp.get(i))) {
                EmotIcons.add(tmp.get(i));
            }
        }

        //JKaiUI original オリジナルは最後に処理する必要あり
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

    public List<String> getEmotIconList() {
        return Collections.unmodifiableList(EmotIcons);
    }

    private boolean checkEmotIconFile(String emoticonstring) {

        String[] tmp = emoticonstring.split(",");

        File emoticonfile = new File(tmp[1].replaceFirst("file:", ""));
        if (emoticonfile.exists() && emoticonfile.canRead()) {
            return true;
        }
        return false;
    }

    public String encodeEmotIcon(String s) {
        String str = s;

        for (int i = 0; i < EmotIcons.size(); i++) {

            String[] tmp = EmotIcons.get(i).split(",");
            str = str.replace(tmp[0], encodeImgTag(tmp[1]));

        }

        return str;
    }

    private String encodeImgTag(String s) {

        String tmp = "<img src=\"" + s + "\">";

        return tmp;
    }
}
