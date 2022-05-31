package Algorithms.Visuals;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class FramesCanvas extends JComponent {
    int[] frames;

    int[] changeArray;

    public FramesCanvas(int[] frames) {
        this.frames = frames;
        setPreferredSize(new Dimension(80, frames.length * 50 + 100));
    }

    public void showChange(int[] changeArray) {
        this.changeArray = changeArray;
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if(changeArray != null) {
            g2d.drawString(
                    changeArray.length > 1 ?
                            String.format("%s/%s", changeArray[0], changeArray[1]) :
                            String.valueOf(changeArray[0]),
                    30,
                    10);
        }

        for(int i = 0; i < frames.length; i++) {
            if(changeArray != null) {
                if(changeArray.length > 1) {
                    if(changeArray[1] == frames[i]) {
                        Rectangle2D.Double r = new Rectangle2D.Double(15, i * 50 + 20, 50, 50);
                        g2d.setColor(Color.YELLOW);
                        g2d.fill(r);
                    }
                }
            }
            Rectangle2D.Double r = new Rectangle2D.Double(15, i * 50 + 20, 50, 50);
            g2d.setColor(Color.BLACK);
            g2d.draw(r);

            g2d.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            g2d.drawString(frames[i] == -1 ? "" : String.valueOf(frames[i]), 20, i * 50 + 60);
        }
    }
}
