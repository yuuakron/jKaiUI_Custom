/*
 * DiagMode.java
 *
 * Created on 8. Juni 2005, 03:19
 *
 */
package pt.jkaiui.ui.modes;

import java.awt.event.ActionEvent;
import java.util.Vector;
import javax.swing.JPopupMenu;
import javax.swing.ListModel;
import pt.jkaiui.core.KaiObject;
import pt.jkaiui.core.messages.Message;

/**
 *
 * @author jbi
 */
public class DiagMode extends MainMode {

    private static final long serialVersionUID = 123415;
    private MessengerModeListModel listModel;
    //private static final String NAME = "Diag Mode";
    private static final String NAME = java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_DiagMode");

    /**
     * Creates a new instance of DiagMode
     */
    public DiagMode() {
        this.listModel.Sorted = false;
        this.setModeName(NAME);
    }

    @Override
    public Vector getSpecialComponents() {
        return new Vector();
    }

    @Override
    protected JPopupMenu getPopupMenu(KaiObject obj) {
        return null;
    }

    @Override
    public ListModel getListModel() {
        if (listModel == null) {
            listModel = new MessengerModeListModel();
        }

        return listModel;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void processMessage(Message message) throws ModeException {
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
    }
}