package pt.jkaiui.filelog;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pt.jkaiui.core.messages.RemoteArenaDevice;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author yuu@akron
 */
public class MACLog extends Log {

    private Set<String> macset;

    public MACLog(String file, String pattern) {
        macset = new HashSet<String>();
        super.init(file, pattern);
    }

    @Override
    public void println(Object mac) {
        if (mac instanceof RemoteArenaDevice) {
            println((RemoteArenaDevice) mac);
        }
    }

    private void println(RemoteArenaDevice mac) {
        if (!datecheck()) {
            update();
        }

        if (!this.contains(mac)) {
            this.add(mac);
            String p = pattern.replace("%N", mac.getUser().decode());
            p = p.replace("%A", mac.getMac().decode());
            logfilepw.println(p);
        }
    }

    private boolean contains(RemoteArenaDevice mac) {
        return macset.contains(mac.getUser().decode() + ";" + mac.getMac().decode());
    }

    private void add(RemoteArenaDevice mac) {
        macset.add(mac.getUser().decode() + ";" + mac.getMac().decode());
    }

    @Override
    protected void readlog() {
        /*        try {
        BufferedReader logfilebr = new BufferedReader(new FileReader(logfile));
        String line;
        while ((line = logfilebr.readLine()) != null) {
        //System.out.println(line);
        macset.add(line);
        }
        logfilebr.close();
        } catch (Exception e) {
        System.out.println("MAClog:"+e);
        }
         */

        try {

            Pattern[] p = {Pattern.compile("%N"), Pattern.compile("%A")};
            Matcher m;

            NavigableMap<Integer, Integer> tm = new TreeMap<Integer, Integer>();

            for (int i = 0; i < p.length; i++) {
                m = p[i].matcher(pattern);
                if (m.find()) {
                    tm.put(new Integer(m.start()), new Integer(i));
                }
            }


            int usernum = -1, macnum = -1;
            Integer keytmp = new Integer(-1);
            for (int i = 0; i < tm.size(); i++) {
                Integer tmp = tm.higherEntry(keytmp).getValue();
                keytmp = tm.higherKey(keytmp);

                if (tmp.equals(new Integer(0))) {//tmp.intValue() == 0
                    usernum = i;
                }
                if (tmp.equals(new Integer(1))) {//tmp.intValue() == 1
                    macnum = i;
                }
            }

            String ps = pattern;
            ps = ps.replace("%N", "(.*)");
            ps = ps.replace("%A", "(.*)");
            Pattern rp = Pattern.compile(ps);

            BufferedReader logfilebr = new BufferedReader(new FileReader(logfile));

            String line;
            while ((line = logfilebr.readLine()) != null) {
                m = rp.matcher(line);
                if (m.matches()) {
                    macset.add(m.group(usernum + 1) + ";" + m.group(macnum + 1));
                }
            }

            logfilebr.close();
        } catch (Exception e) {
            System.out.println("MACLog:" + e);
        }
    }
}
