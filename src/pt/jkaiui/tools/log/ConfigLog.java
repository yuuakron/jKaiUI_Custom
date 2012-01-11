package pt.jkaiui.tools.log;

import java.util.logging.*;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;

public class ConfigLog {
    
    
    // Default handlers
    private static Handler _defaultUserUIHandler;
    private static Handler _defaultSystemUIHandler;
    
    /**
     * Creates the application's default handlers
     * @param userLogEditorPane UI used to log the system log messages
     * @throws Exception error creating the log handlers
     */
    
    public static void createDefaulLoggerHandlers(JEditorPane userLogEditorPane ) throws Exception {
        try {
            
            
            String _suffix = "Data";
            
            _defaultUserUIHandler = LogManager.createLoggerUIEditorPaneHandler( userLogEditorPane, 100000, Level.FINEST, new HTMLFormatterLite());
            
            
        }
        catch (Exception ex) {
            throw new Exception("LogConfig: Error while creating default log handlers - " + ex.getMessage());
        }
    }
    
    /**
     * getDefaultSystemUIHandler
     * @return the handler
     */
    public static Handler getDefaultUserUIHandler() {
        return _defaultUserUIHandler;
    }
    
    
    public static Logger getLogger(String className) {
        Logger _logger;
        
        java.util.logging.LogManager manager = java.util.logging.LogManager.getLogManager();
        
        _logger = manager.getLogger(className);
        
        if (_logger == null){
            _logger = LogManager.createLogger(className);
        }
        
        _logger.addHandler(getDefaultUserUIHandler());
        
        _logger.setLevel(Level.ALL);
        return _logger;
    }
    
    
}
