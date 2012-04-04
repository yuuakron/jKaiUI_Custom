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

public abstract class Log {
    protected File logfile;
    protected PrintWriter logfilepw;
    protected String pattern;
    private Calendar logdate = Calendar.getInstance();
    private String filepath;
    
    protected Log() {
    }
    
    protected void init(String file, String pattern) {
        filepath = file;
        logfile = new File(format(filepath));
        this.pattern = pattern;
        
        File LogHolder = new File(logfile.getParent());
        
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
        readlog();
    }
    
    @Override
    public void finalize(){
        this.close();
    }
    
    public void close(){
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
    
    protected void readlog(){
    }
    
    protected String format(String str){
        
        String tmp = str.replace("%Y", String.format("%02d", logdate.get(Calendar.YEAR)));
        tmp = tmp.replace("%M", String.format("%02d", logdate.get(Calendar.MONTH)+1));
        tmp = tmp.replace("%D", String.format("%02d", logdate.get(Calendar.DAY_OF_MONTH)));

        return tmp;
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
        close();
        init(filepath, pattern);
    }
}
