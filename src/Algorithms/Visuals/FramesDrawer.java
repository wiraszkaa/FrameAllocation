package Algorithms.Visuals;

import Algorithms.Utils.Utils;

import javax.swing.*;

public class FramesDrawer {
    JPanel panel;
    String title;

    public FramesDrawer(String name) {
        panel = new JPanel();
        title = name;

        panel.add(Utils.createLabel(title));
    }

    public void addFrames(int[] frames) {
        FramesCanvas fc = new FramesCanvas(frames);
        panel.add(fc);
    }

    public void addFrames(int[] frames, int[] changeArray) {
        FramesCanvas fc = new FramesCanvas(frames);
        fc.showChange(changeArray);
        panel.add(fc);
    }

    public JPanel getPanel() {
        return panel;
    }

    public void show() {
        Utils.show(panel, title);
    }
}
