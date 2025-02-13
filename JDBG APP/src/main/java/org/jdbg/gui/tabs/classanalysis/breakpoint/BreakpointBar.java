package org.jdbg.gui.tabs.classanalysis.breakpoint;

import org.jdbg.Util;

import javax.swing.*;
import java.awt.*;

public class BreakpointBar extends JPanel {

    public BreakpointBar() {
        int HEIGHT = 33;
        setBackground(UIManager.getColor("TextArea.background"));
        setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, UIManager.getColor("Button.borderColor")));
        setPreferredSize(new Dimension(10000, HEIGHT));
        setMaximumSize(new Dimension(10000, HEIGHT));
        setMinimumSize(new Dimension(0, HEIGHT));

        setLayout(new FlowLayout(FlowLayout.LEFT));

        Icon icon = Util.getIcon("assets/icons/arrow-big-right-lines-deactivated.png", 16, 16);
        add(new JTextArea("Breakpoints"));

        JButton add = new JButton();
        add.setIcon(icon);
        add(add);
        add(new JButton("Manage Breakpoints"));
    }
}
