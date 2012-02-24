/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.jkaiui.filelog;

import pt.jkaiui.JKaiUI;
import pt.jkaiui.core.messages.*;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static pt.jkaiui.core.KaiConfig.ConfigTag.*;

/**
 *
 * @author yuu@akron
 */
public class RoomLog  extends Log{

    HashSet roomset;
    
    public RoomLog() {
        this.init();
    }
    
    @Override
    protected void init(){
//        System.out.println("roomlog");
        logfile = new File(format(JKaiUI.getConfig().getConfigFile(RoomLogFile)));
        roomset = new HashSet();
        super.init();
        readlog();
    }
    
    @Override
    public void println(Object room) {
        if (!datecheck()) {
            update();
        }

        if (!this.contains(room)) {//�G���[�̏ꍇ��Object���Ӑ}�������̂ł͂Ȃ��ꍇ�͏������Ȃ�
            this.add(room);

            String pattern = JKaiUI.getConfig().getConfigString(RoomLogPattern);

            if (room instanceof UserSubVector) {
                UserSubVector roomtmp = (UserSubVector) room;
                pattern = pattern.replace("%V", roomtmp.getVector().decode());
                pattern = pattern.replace("%C", Integer.toString(roomtmp.getCount()));
                pattern = pattern.replace("%S", Integer.toString(roomtmp.getSubs()));
                if (roomtmp.isPass()) {
                    pattern = pattern.replace("%P", "1");
                } else {
                    pattern = pattern.replace("%P", "0");
                }
                pattern = pattern.replace("%M", Integer.toString(roomtmp.getMaxPlayers()));
                try {
                    //default�̕����Z�b�g�r���������|�i�h�r��p���ď�������
                    pattern = pattern.replace("%D", new String(URLDecoder.decode(roomtmp.getDescription().decode(), "utf-8").getBytes("Shift-JIS"), "Shift-JIS"));
                } catch (Exception e) {
                    System.out.println("RoomLog println error:"+e);
                }
            }
            if (room instanceof SubVector) {
                SubVector roomtmp = (SubVector) room;
                pattern = pattern.replace("%V", roomtmp.getVector().decode());
                pattern = pattern.replace("%C", Integer.toString(roomtmp.getCount()));
                pattern = pattern.replace("%S", Integer.toString(roomtmp.getSubs()));
                if (roomtmp.isPass()) {
                    pattern = pattern.replace("%P", "1");
                } else {
                    pattern = pattern.replace("%P", "0");
                }
                pattern = pattern.replace("%M", Integer.toString(roomtmp.getMaxPlayers()));
                pattern = pattern.replace("%D", "");

            }
            logfilepw.println(pattern);
        }
    }
    
    //�G���[�̏ꍇ��Object���Ӑ}�������̂ł͂Ȃ��ꍇ��true���������B����ɂ��Ӑ}���Ȃ��\����h��
    private boolean contains(Object room) {

        if (room instanceof UserSubVector) {
            UserSubVector roomtmp = (UserSubVector)room;
            try {
                String desc = URLDecoder.decode(roomtmp.getDescription().decode(), "utf-8");

                return roomset.contains(roomtmp.getVector().decode() + ";" + desc);

            } catch (Exception e) {
                System.out.println("RoomLog contains eroor:"+e);
                return true;
            }
        }
        if (room instanceof SubVector) {
            SubVector roomtmp = (SubVector)room;
            return roomset.contains(roomtmp.getVector().decode() + ";" + "");
        }
        return true;
    }

    private void add(Object room) {
        if (room instanceof UserSubVector) {
            UserSubVector roomtmp = (UserSubVector) room;
            try {
                String desc = URLDecoder.decode(roomtmp.getDescription().decode(), "utf-8");
                roomset.add(roomtmp.getVector().decode() + ";" + desc);
            } catch (Exception e) {
                System.out.println("RoomLog add error:"+e);
            }
        }
        if (room instanceof SubVector) {
            SubVector roomtmp = (SubVector) room;
            roomset.add(roomtmp.getVector().decode() + ";" + "");
        }
    }
 
    private void readlog() {
        try {            
            //�t�@�C���������݃p�^�[���̉��
            Pattern[] p = {Pattern.compile("%V"), Pattern.compile("%C"), Pattern.compile("%S"), Pattern.compile("%P"), Pattern.compile("%M"), Pattern.compile("%D")};
            Matcher m;
            
            TreeMap tm = new TreeMap();
            
            for(int i=0; i < p.length; i++){
                m = p[i].matcher(JKaiUI.getConfig().getConfigString(RoomLogPattern));
                if (m.find()) {
                    tm.put(new Integer(m.start()), new Integer(i));
                }
            }
            
            //�ꏊ�ƋL�q�����Ԗڂ��v�Z
            int vectnum=-1, descnum=-1;
            Integer keytmp = new Integer(-1);
            for (int i = 0; i < tm.size(); i++) {
                Integer tmp = (Integer)tm.higherEntry(keytmp).getValue();
                keytmp = (Integer)tm.higherKey(keytmp);
                
                if(tmp.equals(new Integer(0))){//tmp.intValue() == 0
                    vectnum = i;
                }
                if(tmp.equals(new Integer(5))){//tmp.intValue() == 5
                    descnum = i;
                }
            }
            
            String ps = JKaiUI.getConfig().getConfigString(RoomLogPattern);
            ps = ps.replace("%V", "(.*)" );
            ps = ps.replace("%C", "(.*)" );
            ps = ps.replace("%S", "(.*)" );
            ps = ps.replace("%P", "(.*)" );
            ps = ps.replace("%M", "(.*)" );
            ps = ps.replace("%D", "(.*)" );
            Pattern rp = Pattern.compile(ps);
            
            BufferedReader logfilebr = new BufferedReader(new FileReader(logfile));
            
            String line;
            while ((line = logfilebr.readLine()) != null) {
                m = rp.matcher(line);
                if (m.matches()) {
                    roomset.add(m.group(vectnum + 1)+";"+m.group(descnum + 1));
                }
            }
            
            logfilebr.close();
        } catch (Exception e) {
            System.out.println("RoomLog"+e);
        }
    }
}
