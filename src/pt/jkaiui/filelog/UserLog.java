/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.jkaiui.filelog;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;
import pt.jkaiui.core.messages.JoinsVector;
/**
 *
 * @author yuu@akron
 */
public class UserLog  extends Log{

    private Set<String> userset;
    
    public UserLog(String file, String pattern) {
        userset = new HashSet<String>();
        super.init(file, pattern);
    }
    
    @Override
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
            String p = pattern.replace("%N", user.getUser().decode());
            logfilepw.println(p);
        }
    }
    
    private boolean contains(JoinsVector user){
        return userset.contains(user.getUser().decode());
    }
    
    private void add(JoinsVector user){
        userset.add(user.getUser().decode());
    }
    
    
    @Override
    protected void readlog(){
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
