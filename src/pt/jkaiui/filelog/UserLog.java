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
import java.util.*;
import static pt.jkaiui.core.KaiConfig.ConfigTag.*;
/**
 *
 * @author yuu@akron
 */
public class UserLog  extends Log{

    HashSet userset;
    
    public UserLog() {
        this.init();
    }
    
    protected void init() {
//        System.out.println("userlog");
        logfile = new File(format(JKaiUI.getConfig().getConfigString(UserLogFile)));
        userset = new HashSet();
        super.init();
        readlog();
    }
    
    public void println(Object user){
        if(user instanceof JoinsVector){
            println((JoinsVector)user);
        }
    }
    
    private void println(JoinsVector user) {
        if (!datecheck()) {
            update();
        }

        if (!this.contains(user)) {
            this.add(user);
            String pattern = JKaiUI.getConfig().getConfigString(UserLogPattern);
            pattern = pattern.replace("%N", user.getUser().decode());
            logfilepw.println(pattern);
        }
    }
    
    private boolean contains(JoinsVector user){
        return userset.contains(user.getUser().decode());
    }
    
    private void add(JoinsVector user){
        userset.add(user.getUser().decode());
    }
    
    private void readlog(){
        try {
            BufferedReader logfilebr = new BufferedReader(new FileReader(logfile));
            String line;
            while ((line = logfilebr.readLine()) != null) {
                //System.out.println(line);
                userset.add(line);
            }
            logfilebr.close();
        } catch (Exception e) {
            System.out.println("Userlog:"+e);
        }
    }
}
