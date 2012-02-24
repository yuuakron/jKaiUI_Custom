/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.jkaiui.filelog;

import java.io.File;
import pt.jkaiui.JKaiUI;
import static pt.jkaiui.core.KaiConfig.ConfigTag.AllLogFile;
import static pt.jkaiui.core.KaiConfig.ConfigTag.AllLogPattern;

/**
 *
 * @author yuu@akron
 */
public class AllLog extends Log{
    public AllLog(){
        this.init();
    }
    
    protected void init(){  
        
//        System.out.println("alllog");
        
        logfile = new File(format(JKaiUI.getConfig().getConfigFile(AllLogFile)));
        super.init();
    }
    
    @Override
    public void println(String s){
        if(!datecheck()){
            update();
        }
                
        String pattern =  JKaiUI.getConfig().getConfigString(AllLogPattern);
        pattern = pattern.replace("%T", now());
        pattern = pattern.replace("%M", s);
        
        logfilepw.println(pattern);
    }
}
