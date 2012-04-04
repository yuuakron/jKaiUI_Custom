/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.jkaiui.filelog;

/**
 *
 * @author yuu@akron
 */
public class AllLog extends Log {

    public AllLog(String file, String pattern) {
        super.init(file, pattern);
    }

    @Override
    public void println(String s) {
        if (!datecheck()) {
            update();
        }

        String p = pattern.replace("%T", now());
        p = p.replace("%M", s);

        logfilepw.println(p);
    }
}
