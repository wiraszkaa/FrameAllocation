package Algorithms.Utils;

import Algorithms.Frames.Frames;
import Algorithms.Frames.LRU;
import javax.swing.*;
import java.awt.*;

public class Utils {

    public static int manageFrames(Process p, int amount) {
        Frames frames = p.frames;
        LRU lru = new LRU();
        int finish = p.currentReference + amount;
        int faults = 0;

        for (int i = p.currentReference; i < Math.min(finish, p.references.size()); i++) {
            int reference = p.references.get(i);
            p.addReference(i);
            if (!frames.contains(reference)) {
                faults++;
                if (!frames.add(reference)) {
                    lru.handleSwap(p);
                }
            }
        }
        p.currentReference++;

        return faults;
    }

    public static int manageFrames(Process p) {
        return manageFrames(p, p.references.size() - p.currentReference);
    }

    public static void show(JPanel panel, String title) {
        JFrame frame = new JFrame();
        JScrollPane scrollBar = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        frame.add(scrollBar);

        frame.setPreferredSize(new Dimension(1000, 500));
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
//        label.setFont(new Font("TimesRoman", Font.PLAIN, 100));

        return label;
    }
}
