package pt.jkaiui.tools.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class HTMLFormatterLite extends Formatter {

    private Date dat = new Date();
    //private final static String format = "{0,date} {0,time}";
    private final static String format = "{0,time}";
    private MessageFormat formatter;
    private Object args[] = new Object[1];
    // Line separator string.  This is the value of the line.separator
    // property at the moment that the SimpleFormatter was created.
    private String lineSeparator = "<br>";

    /**
     * Format the given LogRecord.
     *
     * @param record the log record to be formatted.
     * @return a formatted log record
     */
    @Override
    public synchronized String format(LogRecord record) {
        StringBuilder sb = new StringBuilder();
        // Minimize memory allocations here.

        // -----------------------------------------------------------------------
        // DATE
        // -----------------------------------------------------------------------
        dat.setTime(record.getMillis());
        args[0] = dat;
        StringBuffer text = new StringBuffer();
        if (formatter == null) {
            formatter = new MessageFormat(format);
        }
        formatter.format(args, text, null);
        sb.append("<font size=\"-2\"><b>");
        sb.append("<font color=\"black\"><b>");
        sb.append(text);
        sb.append("</b></font>");
        sb.append(" ");

        // -----------------------------------------------------------------------
        // LEVEL
        // -----------------------------------------------------------------------
        if (record.getLevel() == Level.FINEST) {
            sb.append("<font color=\"black\">");
        } else if (record.getLevel() == Level.FINER) {
            sb.append("<font color=\"black\">");
        } else if (record.getLevel() == Level.FINE) {
            sb.append("<font color=\"black\">");
        } else if (record.getLevel() == Level.CONFIG) {
            sb.append("<font color=\"green\">");
        } else if (record.getLevel() == Level.INFO) {
            sb.append("<font color=\"blue\">");
        } else if (record.getLevel() == Level.WARNING) {
            sb.append("<font color=\"red\">");
        } else {
            if (record.getLevel() == Level.SEVERE) {
                sb.append("<font color=\"#8800FF\">");
            } else {
                sb.append("<font color=\"black\">");
            }
        }

        // -----------------------------------------------------------------------
        // MESSAGE
        // -----------------------------------------------------------------------
        String message = formatMessage(record);

        sb.append(message);
        sb.append("</font>");
        sb.append("</font>");
//      sb.append(lineSeparator);
        if (record.getThrown() != null) {
            try {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                record.getThrown().printStackTrace(pw);
                pw.close();
                sb.append(sw.toString());
            } catch (Exception ex) {
                System.out.println("HTMLFormatterLite:" + ex);
            }
        }

        return sb.toString();
    }
}
