package pt.jkaiui.tools.log;

import java.io.*;
import java.text.*;
import java.util.Date;
import java.util.logging.*;


public class HTMLFormatter extends Formatter {

    Date dat = new Date();
    private final static String format = "{0,date} {0,time}";
    private MessageFormat formatter;

    private Object args[] = new Object[1];

    // Line separator string.  This is the value of the line.separator
    // property at the moment that the SimpleFormatter was created.
    private String lineSeparator = "<br>";

    /**
     * Format the given LogRecord.
     * @param record the log record to be formatted.
     * @return a formatted log record
     */
    public synchronized String format(LogRecord record) {
      StringBuffer sb = new StringBuffer();
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
      sb.append("<font color=\"black\"><b>" + text + "</b></font>");
      sb.append(" ");

      // -----------------------------------------------------------------------
      // CLASS NAME
      // -----------------------------------------------------------------------
      if (record.getSourceClassName() != null) {
        sb.append("<font color=\"#0000FF\"><b>" + record.getSourceClassName() + "</b></font>");
      } else {
        sb.append("<font color=\"#0000FF\"><b>" + record.getLoggerName() + "</b></font>");
      }

      // -----------------------------------------------------------------------
      // CLASS METHOD
      // -----------------------------------------------------------------------
      if (record.getSourceMethodName() != null) {
        sb.append(" ");
        sb.append("<font color=\"#0000AA\"><b>" + record.getSourceMethodName() + "</b></font>");
      }

      sb.append(lineSeparator);

      // -----------------------------------------------------------------------
      // LEVEL
      // -----------------------------------------------------------------------
      if (record.getLevel() == Level.FINEST) {
        sb.append("<font color=\"black\">");
      } else
      if (record.getLevel() == Level.FINER) {
        sb.append("<font color=\"black\">");
      } else
      if (record.getLevel() == Level.FINE) {
        sb.append("<font color=\"black\">");
      } else
      if (record.getLevel() == Level.CONFIG) {
        sb.append("<font color=\"green\">");
      } else
      if (record.getLevel() == Level.INFO) {
        sb.append("<font color=\"blue\">");
      } else
      if (record.getLevel() == Level.WARNING) {
        sb.append("<font color=\"red\">");
      } else {
      if (record.getLevel() == Level.SEVERE) {
        sb.append("<font color=\"#8800FF\">");
      } else
        sb.append("<font color=\"black\">");
      }

      // -----------------------------------------------------------------------
      // MESSAGE
      // -----------------------------------------------------------------------
      String message = formatMessage(record);

      sb.append(message);
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
            System.out.println("HTMLFormatter:"+ex);
        }
      }


      return sb.toString();
    }
}
