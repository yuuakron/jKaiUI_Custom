/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.jkaiui.filelog;
        
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author yuu@akron
 */

public class Log {
    protected File logfile;
    protected PrintWriter logfilepw;
    protected Calendar logdate; 
    
    public Log() {
        logdate = Calendar.getInstance();
    }
    
    protected void init(){
        File LogHolder = new File(logfile.getParent());

//        System.out.println("log");
        
        try {
            if (!LogHolder.exists()) {
                LogHolder.mkdir();
            }
            if (!logfile.exists()) {
                logfile.createNewFile();
            }

            if (logfile.isFile() && logfile.canWrite()) {

                logfilepw = new PrintWriter(new BufferedWriter(new FileWriter(logfile, true)), true);
            }
        } catch (Exception e) {
            System.out.println("fileopen err:"+e);
        }
    }
    
    public void finalize(){
        logfilepw.close();
    }
    
    public void println(String s){
        if(!datecheck()){
            update();
        }
        logfilepw.println(now() + ";" + s);
    }
    
    public void println(Object printobject){
    }
    
    public void println(Object printobject, Object option){
    }
    
    protected String format(String str){
        str = str.replace("%Y", String.format("%02d", logdate.get(Calendar.YEAR)));
        str = str.replace("%M", String.format("%02d", logdate.get(Calendar.MONTH)+1));
        str = str.replace("%D", String.format("%02d", logdate.get(Calendar.DAY_OF_MONTH)));

        return str;
    }

    protected String now() {
        Date dat = new Date();
        String stime = DateFormat.getDateTimeInstance().format(dat);
        return stime;
    }
    
    protected boolean datecheck() {
        Calendar nowdate = Calendar.getInstance();
        if ((nowdate.get(Calendar.YEAR) == logdate.get(Calendar.YEAR))
                && (nowdate.get(Calendar.MONTH) == logdate.get(Calendar.MONTH))
                && (nowdate.get(Calendar.DAY_OF_MONTH) == logdate.get(Calendar.DAY_OF_MONTH))) {
            return true;
        }
        return false;
    }

    protected void update() {
        logdate = Calendar.getInstance();
        finalize();
        init();
    }
}
