/*
 * Manager.java
 *
 * Created on November 22, 2004, 5:50 PM
 */

package pt.jkaiui.manager;

import javax.swing.JOptionPane;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;
import pt.jkaiui.JKaiUI;
import pt.jkaiui.core.Arena;
import pt.jkaiui.core.KaiConfig;
import pt.jkaiui.core.KaiString;
import pt.jkaiui.core.messages.*;

import pt.jkaiui.tools.log.ConfigLog;
import static pt.jkaiui.core.KaiConfig.ConfigTag.*;

/**
 *
 * @author  pedro
 */
public class Manager {
    
    
    private static ActionExecuter executer;
    private Connection conn;
    private static Logger _logger;
    
    
    // String[] to find all I_InMessage interfaces.
    // I would rather do this dynamically, but I don't know how
    // to find all classes within a given package.
    // To update this list use:
    // egrep -c "implements .*I_InMessage" src/pt/jkaiui/core/messages/* | grep -v :0 | sed -e 's/src\///;s/.java:.*//;s/\//./g;s/\(.*\)/"\1",/'
    
    
    private static final String[] IN_MESSAGES = {
        "pt.jkaiui.core.messages.AddContact",
        "pt.jkaiui.core.messages.AdminPrivileges",
        "pt.jkaiui.core.messages.ArenaPing",
        "pt.jkaiui.core.messages.ArenaPM",
        "pt.jkaiui.core.messages.AttachEngine",
        "pt.jkaiui.core.messages.Avatar",
        "pt.jkaiui.core.messages.Chat",
        "pt.jkaiui.core.messages.Chat2",
        "pt.jkaiui.core.messages.ChatMode",
        "pt.jkaiui.core.messages.ClientNotLoggedIn",
        "pt.jkaiui.core.messages.ConnectedMessenger",
        "pt.jkaiui.core.messages.ContactOffline",
        "pt.jkaiui.core.messages.ContactOnline",
        "pt.jkaiui.core.messages.ContactPing",
        "pt.jkaiui.core.messages.DetachEngine",
        "pt.jkaiui.core.messages.EngineHere",
        "pt.jkaiui.core.messages.EngineInUse",
        "pt.jkaiui.core.messages.JoinsChat",
        "pt.jkaiui.core.messages.JoinsVector",
        "pt.jkaiui.core.messages.KaiVectorIn",
        "pt.jkaiui.core.messages.LeavesChat",
        "pt.jkaiui.core.messages.LeavesVector",
        "pt.jkaiui.core.messages.LoggedIn",
        "pt.jkaiui.core.messages.ModeratorPrivileges",
        "pt.jkaiui.core.messages.PM",
        "pt.jkaiui.core.messages.RemoveContact",
        "pt.jkaiui.core.messages.RemoveSubVector",
        "pt.jkaiui.core.messages.Status",
        "pt.jkaiui.core.messages.SubVector",
        "pt.jkaiui.core.messages.SubVectorUpdate",
        "pt.jkaiui.core.messages.UserData",
        "pt.jkaiui.core.messages.UserSubVector",
        "pt.jkaiui.core.messages.Metrics",
        "pt.jkaiui.core.messages.LocalDevice",
        "pt.jkaiui.core.messages.DhcpFailure",
        "pt.jkaiui.core.messages.UserProfile",
        "pt.jkaiui.core.messages.RemoteArenaDevice",
        "pt.jkaiui.core.messages.CodePage",
        "pt.jkaiui.core.messages.ArenaStatus",
        "pt.jkaiui.core.messages.SessionKey",
        "pt.jkaiui.core.messages.ConnectedArena"
    };
    
    /** Creates a new instance of Manager */
    public Manager() {
        
        _logger = ConfigLog.getLogger(this.getClass().getName());
        
        executer = new ActionExecuter(this);
        
//        LogFileinit();        
    }
    
    
    public void connect(){
        
        KaiConfig config = JKaiUI.getConfig();
        
        _logger.fine("Discovering engine");
        if(config.getConfigBoolean(AUTOMATICALLYDETECTED)) {
            
            EngineFinder finder = new EngineFinder();
            
            // SPLASH SCREEN WOULD BE GOOD HERE!!!
            // Finding engines. Note: the argument given is the number of SECONDS to timeout
            finder.spawnEngineReceivers(2);
            
            Collection engines = finder.getEngines();
            if(engines.size() == 0) {
                boolean shouldRetry = JKaiUI.getMainUI().askRetry("MSG_NoEngineFound", "MSG_NoEngineFoundTitleMsg");
                if(shouldRetry) {
                    JKaiUI.connect();
                }
                return;
            } else if(engines.size() == 1) {
                Iterator it = engines.iterator();
                conn = new Connection((String)it.next(), 34522);
            } else {
                String[] engineArray = new String[engines.size()];
                
                // Fill the array using the Collection's iterator
                Iterator it = engines.iterator();
                int traverseIndex = 0;
                while(it.hasNext()) engineArray[traverseIndex++] = (String) it.next();
                
                String choice = JKaiUI.getMainUI().openEnginePrompt(engineArray);
                
                if(choice == null) return;
                
                conn = new Connection(choice, 34522);
            }
            
        } else {
            // User wants to manually specify a host and port
            
            conn = new Connection(config.getConfigString(HOST), config.getConfigInt(PORT));
        }
        
        executer.execute(new DiscoverEngine());
        
    }
    
    public void send(I_OutMessage message){

        if (message instanceof PMOut || message instanceof ArenaPMOut) {
            try {
                if (message instanceof PMOut) {
//                    conn.send(message.send().getBytes(JKaiUI.getChatManager().getSelectedEncoding(((PMOut) message).getUser().decode())));
                    conn.send(StringByteConverter.StringtoByteforPM(message.send(), ((PMOut) message).getUser().decode()));
                }
                if (message instanceof ArenaPMOut) {
//                    conn.send(message.send().getBytes(JKaiUI.getChatManager().getSelectedEncoding(((ArenaPMOut) message).getUser().decode())));
                    conn.send(StringByteConverter.StringtoByteforPM(message.send(), ((ArenaPMOut) message).getUser().decode()));
                }
            } catch (Exception e) {
                System.out.println("manager send:" + e);
            }
        } else {
            conn.send(StringByteConverter.StringtoByte(message.send()));
        }
        JKaiUI.getLogFileManager().println("All", "Send; " + message.send());
    }
    
    
    
    private Message parse(String s) throws UnsupportedMessageException {
        
        Message message = null;
        
        for( int i = 0; i < IN_MESSAGES.length; i++ ){
            
            try{
                I_InMessage parser = (I_InMessage) Class.forName(IN_MESSAGES[i]).newInstance();
                
                
                message = parser.parse(s);
                if (message != null)
                    return message;
            }
            
            catch (Exception e){
                System.out.println("Manager parse:"+e);
                _logger.severe("Could not find or initialize class " + IN_MESSAGES[i] + ": " + e.getMessage());
            }
        }
        
        // if we got here, no message was found
        throw new UnsupportedMessageException();
        
    }
    
    
    
    public void process(byte[] b){

        String s= StringByteConverter.BytetoString(b).trim();
//        String s = StringValidator.CheckandReturnCorrectString(b).trim();
        
        JKaiUI.getLogFileManager().println("All", "Receive; " + s);
        Message message = null;
     
        try {
            message = parse(s);
            if (message instanceof PM) {
                message = parse(StringByteConverter.BytetoStringforPM(b, ((PM) message).getUser().decode()).trim());
            }
            if (message instanceof ArenaPM) {
                message = parse(StringByteConverter.BytetoStringforPM(b, ((ArenaPM) message).getUser().decode()).trim());
            }
        }
        catch (UnsupportedMessageException e){
            System.out.println("Manager process:"+e);
            _logger.warning("Message not supported: " + s);
            
            
            return;            
        }
        
        // _logger.finest("Found message: " + message);
        try {
            executer.execute(message);
        } catch (Exception e) {
            System.out.println("Manage process:" + e);
        }
    }
    
    
    public static ActionExecuter getExecuter(){
        
        
        return executer;
    }
    
    public static void enterArena(Arena arena){
        
        // Check for pass
        
        String pass = "";
        if (arena.isPass())
            pass = JOptionPane.showInputDialog(null, java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_AskForPass"));
        
        
        KaiVectorOut vector = new KaiVectorOut();
        vector.setVector(new KaiString(arena.getVector()));
        vector.setPassword(new KaiString(pass));
        
        getExecuter().execute(vector);
        
    }
    
    
}
