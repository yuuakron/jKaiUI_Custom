/*
 * JScrollLabel.java
 *
 * Created on 27. Juni 2005, 00:26
 *
 */
package pt.jkaiui.ui.tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JLabel;

/**
 *
 * @author jbi
 */
public class ScrollGames extends JLabel implements Runnable {

    private Thread Task;
    private int TextWidth, TextHeight, LabWidth, LabHeight, PosX, PosY, Speed = 20;
    private String ScrollText;
    private boolean Running = false;
    private Image ScrollImage;
    private Graphics Gfx;
    private Color TextColor = new Color(0x000000);
    private FontMetrics FMetrics;

    /** Creates a new instance of JScrollLabel */
    public ScrollGames() {
        super(" ");
        setFont(new Font("Dialog", 0, 10));
        FMetrics = getFontMetrics(getFont());
    }

    public void setScrollText(String Text) {
        ScrollText = Text;
        TextHeight = FMetrics.getHeight();
        TextWidth = FMetrics.stringWidth(ScrollText);
    }

    public void start() {
        if (Task != null) {
            stop();
        }
        Task = new Thread(this);
        Running = true;
        Task.start();
    }

    public void stop() {
        if (Task != null) {
            Task.stop();
        }
    }

    public void run() {
        PosX = 0;
        repaint();

        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("ScrollGames:" + e);
        }

        if (TextWidth < LabWidth) {
            return;
        }

        try {
            while (Running) {
                PosX -= 1;
                if (PosX < 0 - TextWidth - 20) {
                    PosX = LabWidth;
                }
                repaint();
                Task.sleep(Speed);
            }
        } catch (Exception e) {
            System.out.println("ScrollGames:" + e);
        }

    }

    public void paint(Graphics g) {

        if (Gfx == null) {
            LabWidth = getSize().width;
            LabHeight = getSize().height;

            ScrollImage = createImage(LabWidth, LabHeight);
            Gfx = ScrollImage.getGraphics();

            PosY = (LabHeight + TextHeight / 2) / 2;
        }

        try {
            Gfx.setColor(getBackground());
            Gfx.fillRect(0, 0, LabWidth, LabHeight);

            Gfx.setFont(getFont());
            Gfx.setColor(TextColor);
            Gfx.drawString(ScrollText, PosX, PosY);
            g.drawImage(ScrollImage, 0, 0, this);
        } catch (Exception e) {
            System.out.println("ScrollGames:" + e);
        }
    }
}
