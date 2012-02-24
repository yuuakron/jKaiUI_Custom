/*
 * InfoPanel.java
 *
 * Created on 12. Juni 2005, 05:00
 */

package pt.jkaiui.ui;
import java.awt.GridBagConstraints;
import java.util.ResourceBundle;
import javax.swing.SwingUtilities;
import pt.jkaiui.JKaiUI;
import pt.jkaiui.ui.tools.ScrollGames;

/**
 *
 * @author  jbi
 */
public class InfoPanel extends javax.swing.JPanel {
	
	private ResourceBundle resourceBundle;
	private ScrollGames ScrollGames1;
	
	/** Creates new form InfoPanel */
	public InfoPanel() {
		initComponents();
		ScrollGames1 = new ScrollGames();
		GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		add(ScrollGames1, gridBagConstraints);
	}
	
	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabelAge = new javax.swing.JLabel();
        jLabelLocation = new javax.swing.JLabel();
        jLabelBandwidth = new javax.swing.JLabel();
        jLabelInfoUser = new javax.swing.JLabel();
        jLabelConsoles = new javax.swing.JLabel();
        jLabelGames = new javax.swing.JLabel();
        jButtonClose = new javax.swing.JButton();
        jLabelInfoLocation = new javax.swing.JLabel();
        jLabelInfoConsoles = new javax.swing.JLabel();
        jLabelInfoAge = new javax.swing.JLabel();
        jLabelInfoBandwidth = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        setMaximumSize(new java.awt.Dimension(250, 2147483647));
        setMinimumSize(new java.awt.Dimension(250, 20));
        jLabelAge.setFont(new java.awt.Font("Dialog", 0, 10));
        jLabelAge.setText(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_UserProfileAge"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 7, 0, 4);
        add(jLabelAge, gridBagConstraints);

        jLabelLocation.setFont(new java.awt.Font("Dialog", 0, 10));
        jLabelLocation.setText(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_UserProfileLocation"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 7, 0, 4);
        add(jLabelLocation, gridBagConstraints);

        jLabelBandwidth.setFont(new java.awt.Font("Dialog", 0, 10));
        jLabelBandwidth.setText(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_UserProfileBandwidth"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 7, 0, 4);
        add(jLabelBandwidth, gridBagConstraints);

        jLabelInfoUser.setText("user");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 7, 0, 4);
        add(jLabelInfoUser, gridBagConstraints);

        jLabelConsoles.setFont(new java.awt.Font("Dialog", 0, 10));
        jLabelConsoles.setText("XBox/PS2/GCN:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 7, 0, 4);
        add(jLabelConsoles, gridBagConstraints);

        jLabelGames.setFont(new java.awt.Font("Dialog", 0, 10));
        jLabelGames.setText(java.util.ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_UserProfileGames"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 7, 0, 4);
        add(jLabelGames, gridBagConstraints);

        jButtonClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pt/jkaiui/ui/resources/remove.png")));
        jButtonClose.setBorder(null);
        jButtonClose.setBorderPainted(false);
        jButtonClose.setContentAreaFilled(false);
        jButtonClose.setFocusPainted(false);
        jButtonClose.setFocusable(false);
        jButtonClose.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jButtonClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MouseClicked(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        add(jButtonClose, gridBagConstraints);

        jLabelInfoLocation.setFont(new java.awt.Font("Dialog", 0, 10));
        jLabelInfoLocation.setText("location");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        add(jLabelInfoLocation, gridBagConstraints);

        jLabelInfoConsoles.setFont(new java.awt.Font("Dialog", 0, 10));
        jLabelInfoConsoles.setText("consoles");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        add(jLabelInfoConsoles, gridBagConstraints);

        jLabelInfoAge.setFont(new java.awt.Font("Dialog", 0, 10));
        jLabelInfoAge.setText("age");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        add(jLabelInfoAge, gridBagConstraints);

        jLabelInfoBandwidth.setFont(new java.awt.Font("Dialog", 0, 10));
        jLabelInfoBandwidth.setText("bandwidth");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        add(jLabelInfoBandwidth, gridBagConstraints);

    }
    // </editor-fold>//GEN-END:initComponents
	
	private void MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MouseClicked
		if (SwingUtilities.isLeftMouseButton(evt)) {
			hidePanel();
		}
	}//GEN-LAST:event_MouseClicked
	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JLabel jLabelAge;
    private javax.swing.JLabel jLabelBandwidth;
    private javax.swing.JLabel jLabelConsoles;
    private javax.swing.JLabel jLabelGames;
    private javax.swing.JLabel jLabelInfoAge;
    private javax.swing.JLabel jLabelInfoBandwidth;
    private javax.swing.JLabel jLabelInfoConsoles;
    private javax.swing.JLabel jLabelInfoLocation;
    private javax.swing.JLabel jLabelInfoUser;
    private javax.swing.JLabel jLabelLocation;
    // End of variables declaration//GEN-END:variables
	
	public void setUser(String s) {
                if(JKaiUI.MODERATORS.contains(s)) {
            s = "<html><body>" + s + " - <font color=\"red\">"
            + ResourceBundle.getBundle("pt/jkaiui/ui/Bundle").getString("LBL_Moderator") + "</font></body></html>";
        }
		jLabelInfoUser.setText(s);
	}
	
	public void setAge(String s) {
		jLabelInfoAge.setText(s);
	}
	
	public void setBandwidth(String s) {
		jLabelInfoBandwidth.setText(s);
	}
	
	public void setLocation(String s) {
		jLabelInfoLocation.setText(s);
	}
	
	public void setConsoles(String s) {
		jLabelInfoConsoles.setText(s);
	}
	
	public void setGames(String s) {
		ScrollGames1.setScrollText(s);
	}
	
	public void showPanel() {
		hidePanel();
		setVisible(true);
		ScrollGames1.start();
	}
	
	public void hidePanel() {
		ScrollGames1.stop();
		setVisible(false);
	}
	
}

