/*
 * JMyPanel.java
 *
 * Created on December 1, 2004, 9:00 PM
 */

package pt.jkaiui.ui.modes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JPanel;
import pt.jkaiui.JKaiUI;
import static pt.jkaiui.core.KaiConfig.ConfigTag.*;

/**
 *
 * @author  pedro
 */
public class KaiListPanel extends JPanel{
    
    
    
    Color	_theColor;
    Color	_theBack;
    
    private javax.swing.JLabel jLabelIcon;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelBase;
    private javax.swing.JLabel jLabelC1;
    private javax.swing.JLabel jLabelC2;
    private JPanel centerPanel;
    
    public KaiListPanel(){
        
        // Border
        setBorder(BorderFactory.createMatteBorder(1,0,0,0,new Color(220,220,220)));
        
        jLabelIcon = new javax.swing.JLabel();
        jLabelName = new javax.swing.JLabel();
        jLabelBase = new javax.swing.JLabel();
        jLabelC1 = new javax.swing.JLabel();
        jLabelC2 = new javax.swing.JLabel();
        
        BorderLayout layout = new BorderLayout();
        layout.setHgap(10);
        layout.setVgap(10);
        setLayout(layout);
        
        // Icon
        add(jLabelIcon, java.awt.BorderLayout.WEST);
        
        // Center Panel
        
        centerPanel = new JPanel();
//        centerPanel.setLayout(new GridLayout(5,1));


        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        centerPanel.setLayout(gridbag);

        centerPanel.setOpaque(false);

        add(centerPanel, BorderLayout.CENTER);

        // Formatting:
        jLabelName.setFont(new java.awt.Font("SansSerif", 0, JKaiUI.getConfig().getConfigInt(RoomFontSize)));
        jLabelBase.setFont(new java.awt.Font("SansSerif", 0, JKaiUI.getConfig().getConfigInt(RoomFontSize)));
        jLabelBase.setForeground(new Color(10, 10, 10));
        jLabelC1.setFont(new java.awt.Font("SansSerif", 0, JKaiUI.getConfig().getConfigInt(RoomFontSize)));
        jLabelC1.setForeground(new Color(10, 10, 10));
        jLabelC2.setFont(new java.awt.Font("SansSerif", 0, JKaiUI.getConfig().getConfigInt(RoomFontSize)));
        jLabelC2.setForeground(new Color(10, 10, 10));

        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 250;
        gridbag.setConstraints(jLabelName, c);
        centerPanel.add(jLabelName);

        c.gridx = 0;
        c.gridy = 1;
        c.ipadx = 250;
        gridbag.setConstraints(jLabelBase, c);
        centerPanel.add(jLabelBase);

        c.gridx = 0;
        c.gridy = 2;
        c.ipadx = 250;
        gridbag.setConstraints(jLabelC1, c);
        centerPanel.add(jLabelC1);

        //c.gridx = 0;
        //c.gridy = 4;
        //gridbag.setConstraints(filler, c);
        //centerPanel.add(filler);

//        centerPanel.add(jLabelC2);

        //centerPanel.add(rightPanel,BorderLayout.EAST);
        
    }
    public Insets getInsets(){
        
        return new Insets(0,3,0,3);
        
    }
    
    public KaiListPanel(Color theColor) {
        _theColor = theColor;
        _theBack = Color.white;
    }
    
    
    public void setColor(Color theColor){
        _theColor = theColor;
    }
    
    public void setBackground(boolean selected){
        
        if (selected){
	    _theBack = new Color(255,170,0);
        } else {
            _theBack = Color.white;
        }
        
        setBackground(_theBack);
        repaint();
    }
    
    public void setIcon(Icon icon){
        
        jLabelIcon.setIcon(icon);
    }
    
    public void setText(String text){
        
        jLabelName.setText(text);
//        setToolTipText(text);
    }
    
    public void setDescription(String text){
        
        jLabelBase.setText(text);
        setToolTipText(text);
        setToolTipText("<html><div style=\"font-size:"+JKaiUI.getConfig().getConfigInt(RoomFontSize)+"px\">" + text + "</div></html>");
    }
    
    public void setC1(String text){
        
        jLabelC1.setText(text);
    }
    
    public void setC2(String text){
        
        jLabelC2.setText(text);
    }
    
    public void setFontSize(int size) {
        jLabelName.setFont(new java.awt.Font("SansSerif", 0, JKaiUI.getConfig().getConfigInt(RoomFontSize)));
        jLabelBase.setFont(new java.awt.Font("SansSerif", 0, JKaiUI.getConfig().getConfigInt(RoomFontSize)));
        jLabelC1.setFont(new java.awt.Font("SansSerif", 0, JKaiUI.getConfig().getConfigInt(RoomFontSize)));
        jLabelC2.setFont(new java.awt.Font("SansSerif", 0, JKaiUI.getConfig().getConfigInt(RoomFontSize)));
    }
}
