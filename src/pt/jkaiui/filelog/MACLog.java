package pt.jkaiui.filelog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pt.jkaiui.JKaiUI;
import static pt.jkaiui.core.KaiConfig.ConfigTag.MACLogFile;
import static pt.jkaiui.core.KaiConfig.ConfigTag.MACLogPattern;
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

    HashSet macset;

    public MACLog() {
        this.init();
    }

    @Override
    protected void init() {
        logfile = new File(format(JKaiUI.getConfig().getConfigFile(MACLogFile)));
        macset = new HashSet();
        super.init();
        readlog();
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
            String pattern = JKaiUI.getConfig().getConfigString(MACLogPattern);
            pattern = pattern.replace("%N", mac.getUser().decode());
            pattern = pattern.replace("%A", mac.getMac().decode());
            logfilepw.println(pattern);
        }
    }

    private boolean contains(RemoteArenaDevice mac) {
        return macset.contains(mac.getUser().decode() + ";" + mac.getMac().decode());
    }

    private void add(RemoteArenaDevice mac) {
        macset.add(mac.getUser().decode() + ";" + mac.getMac().decode());
    }

    private void readlog() {
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
            //ファイル書き込みパターンの解析
            Pattern[] p = {Pattern.compile("%N"), Pattern.compile("%A")};
            Matcher m;

            TreeMap tm = new TreeMap();

            for (int i = 0; i < p.length; i++) {
                m = p[i].matcher(JKaiUI.getConfig().getConfigString(MACLogPattern));
                if (m.find()) {
                    tm.put(new Integer(m.start()), new Integer(i));
                }
            }

            //ユーザー名とアドレスが何番目か計算
            int usernum = -1, macnum = -1;
            Integer keytmp = new Integer(-1);
            for (int i = 0; i < tm.size(); i++) {
                Integer tmp = (Integer) tm.higherEntry(keytmp).getValue();
                keytmp = (Integer) tm.higherKey(keytmp);

                if (tmp.equals(new Integer(0))) {//tmp.intValue() == 0
                    usernum = i;
                }
                if (tmp.equals(new Integer(1))) {//tmp.intValue() == 1
                    macnum = i;
                }
            }

            String ps = JKaiUI.getConfig().getConfigString(MACLogPattern);
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
