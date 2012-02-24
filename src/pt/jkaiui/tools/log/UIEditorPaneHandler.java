package pt.jkaiui.tools.log;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.MemoryHandler;
import java.util.logging.StreamHandler;
import javax.swing.JEditorPane;
import javax.swing.text.Document;

public class UIEditorPaneHandler extends MemoryHandler {

    private JEditorPane _editorPane;
    private long _maxSize;

    public UIEditorPaneHandler(JEditorPane editorPane,
            StreamHandler streamHandler, int size, Level level) {
        super(streamHandler, size, level);

        this._maxSize = 10000;
        this._editorPane = editorPane;
        //this._editorPane.setContentType("text/html");
        //this._editorPane.setFont(new java.awt.Font("Dialog", 0, 9));
    }

    /**
     * Appends a log message to a HTML EditorPane
     *
     * @param record log record
     * @see <a
     * href="http://forum.java.sun.com/thread.jsp?forum=57&thread=154197">
     * Append text to JEditorPane</a>
     */
    @Override
    public synchronized void publish(LogRecord record) {
        if (this.isLoggable(record)) {
            // Log it before checking the condition
            super.publish(record);

            // Dump buffered records
            push();

            String _content = this.getFormatter().format(record);

            if (this._editorPane.getText().length() > this._maxSize) {
                this._editorPane.setText("");
            }

            Document _doc = this._editorPane.getDocument();
            try {
                this._editorPane.getEditorKit().read(new java.io.StringReader(_content), _doc, _doc.getLength());
                this._editorPane.setCaretPosition(_doc.getLength());
            } catch (Exception ex) {
                // Cannot log the message => print the error to System.out
                System.out.println("UIEditorPaneHandler::Error while updating Log EditorPane: " + ex.getMessage());
            }

            // Scroll to bottom
            // Not needed... the scroll pane has a ChangeEvent() listener
//      this._editorPane.setSelectionStart(this._editorPane.getText().length());
//      this._editorPane.setSelectionEnd(this._editorPane.getText().length());
//      this._editorPane.scrollRectToVisible(new Rectangle(0,this._editorPane.getBounds(null).height,1,1));
        }
    }
}