/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.jkaiui.filelog;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pt.jkaiui.core.messages.SubVector;
import pt.jkaiui.core.messages.UserSubVector;

/**
 *
 * @author yuu@akron
 */
public class RoomLog  extends Log{

    private Set<String> roomset;
    
    public RoomLog(String file, String pattern) {
        roomset = new HashSet<String>();
        super.init(file, pattern);
    }
    
    @Override
    public void println(Object room) {
        if (!datecheck()) {
            update();
        }

        if (!this.contains(room)) {
            this.add(room);

            String p = "";
            
            if (room instanceof UserSubVector) {
                UserSubVector roomtmp = (UserSubVector) room;
                p = pattern.replace("%V", roomtmp.getVector().decode());
                p = p.replace("%C", Integer.toString(roomtmp.getCount()));
                p = p.replace("%S", Integer.toString(roomtmp.getSubs()));
                if (roomtmp.isPass()) {
                    p = p.replace("%P", "1");
                } else {
                    p = p.replace("%P", "0");
                }
                p = p.replace("%M", Integer.toString(roomtmp.getMaxPlayers()));
                try {
                    //default
                    p = p.replace("%D", new String(URLDecoder.decode(roomtmp.getDescription().decode(), "utf-8").getBytes("Shift-JIS"), "Shift-JIS"));
                } catch (Exception e) {
                    System.out.println("RoomLog println error:"+e);
                }
            }
            if (room instanceof SubVector) {
                SubVector roomtmp = (SubVector) room;
                p = pattern.replace("%V", roomtmp.getVector().decode());
                p = p.replace("%C", Integer.toString(roomtmp.getCount()));
                p = p.replace("%S", Integer.toString(roomtmp.getSubs()));
                if (roomtmp.isPass()) {
                    p = p.replace("%P", "1");
                } else {
                    p = p.replace("%P", "0");
                }
                p = p.replace("%M", Integer.toString(roomtmp.getMaxPlayers()));
                p = p.replace("%D", "");

            }
            logfilepw.println(p);
        }
    }
    
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
 
    @Override
    protected void readlog() {
        try {            
            Pattern[] p = {Pattern.compile("%V"), Pattern.compile("%C"), Pattern.compile("%S"), Pattern.compile("%P"), Pattern.compile("%M"), Pattern.compile("%D")};
            Matcher m;
            
            NavigableMap<Integer, Integer> tm = new TreeMap<Integer, Integer>();
            
            for(int i=0; i < p.length; i++){
                m = p[i].matcher(pattern);
                if (m.find()) {
                    tm.put(new Integer(m.start()), new Integer(i));
                }
            }
            
            int vectnum=-1, descnum=-1;
            Integer keytmp = new Integer(-1);
            for (int i = 0; i < tm.size(); i++) {
                Integer tmp = tm.higherEntry(keytmp).getValue();
                keytmp = tm.higherKey(keytmp);
                
                if(tmp.equals(new Integer(0))){//tmp.intValue() == 0
                    vectnum = i;
                }
                if(tmp.equals(new Integer(5))){//tmp.intValue() == 5
                    descnum = i;
                }
            }
            
            String ps = pattern;
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
