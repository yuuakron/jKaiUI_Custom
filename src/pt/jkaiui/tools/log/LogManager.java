package pt.jkaiui.tools.log;

import java.io.ByteArrayOutputStream;
import java.util.logging.*;
import javax.swing.JEditorPane;

public class LogManager {
    
    /**
     * Instanciates a new anonymous logger
     * @return the new logger
     */
    public static Logger createLogger() {
        Logger _newLogger = Logger.getAnonymousLogger();
        
        // Set the minimum logging level of the logger
        _newLogger.setLevel(Level.ALL);
        
        return _newLogger;
    }
    
    /**
     * Instanciates a new class logger
     * @param className class associated to the new logger
     * @return the new logger
     */
    public static Logger createLogger(String className) {
        Logger _newLogger = Logger.getLogger(className);
        
        // Set the minimum logging level of the logger
        _newLogger.setLevel(Level.ALL);
        
        return _newLogger;
        
    }
    
    
    /**
     * Instanciates a new Memory logger handler
     * @param _buffer memory buffer used to keep the log messages
     * @param size size of the memory used to keep the log messages
     * @param level logger handler level
     * @param formatter logger handler formatter
     * @return the new handler
     * @throws Exception error while creating the handler
     */
    public static Handler createLoggerMemoryHandler(ByteArrayOutputStream _buffer,
    int size, Level level, Formatter formatter) throws Exception {
        
        // Create a stream handler
        StreamHandler _loggerStreamHandler = new StreamHandler(_buffer, formatter);
        
        // Create a memory handler
        MemoryHandler _loggerMemHandler = new MemoryHandler(_loggerStreamHandler, size, level);
        
        // Set the filter
        _loggerMemHandler.setFilter(new MyFilter(level));
        
        // Set the formatter
        _loggerMemHandler.setFormatter(formatter);
        
        return _loggerMemHandler;
    }
    

    /**
     * Instanciates a new User Inteface logger handler
     * @param editorPane UI used to show the log messages
     * @param size size of the memory used to keep the log messages
     * @param level logger handler level
     * @param formatter logger handler formatter
     * @return the new handler
     * @throws Exception error while creating the handler
     */
    public static Handler createLoggerUIEditorPaneHandler( JEditorPane editorPane,
    int size, Level level, Formatter formatter) throws Exception{
        
        // Create an output stream
        ByteArrayOutputStream _buffer = new ByteArrayOutputStream();
        
        // Create a stream handler
        StreamHandler _loggerStreamHandler = new StreamHandler(_buffer, formatter);
        
        // Create a UI handler
        UIEditorPaneHandler _loggerUIHandler = new UIEditorPaneHandler( editorPane,
        _loggerStreamHandler, size, level);
        
        // Set the filter
        _loggerUIHandler.setFilter(new MyFilter(level));
        
        // Set the formatter
        _loggerUIHandler.setFormatter(formatter);
        
        return _loggerUIHandler;
    }
    
}

/* -----------
   LOG STUFF:
   -----------
   http://javaalmanac.com/egs/java.util.logging/LogFile.html
   http://java.sun.com/j2se/1.4/docs/guide/util/logging/overview.html
 
   The log level of a logger controls the severity of messages that it will log.
   In particular, a log record whose severity is greater than or equal to the logger's log level is logged.
   A log level can be null, in which case the level is inherited from the logger's parent.
 
   The levels in descending order are:
      SEVERE (highest value)
      WARNING
      INFO
      CONFIG
      FINE
      FINER
      FINEST (lowest value)
   In addition there is a level OFF that can be used to turn off logging,
   and a level ALL that can be used to enable logging of all messages.
 */
