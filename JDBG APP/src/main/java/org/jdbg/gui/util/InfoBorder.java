package org.jdbg.gui.util;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class InfoBorder extends TitledBorder {

    public InfoBorder(String title) {
        super(title);
        Border b = BorderFactory.createMatteBorder(1, 0, 0, 0, UIManager.getColor("Button.borderColor"));
        setTitleFont(new Font(getTitleFont().getName(), getTitleFont().getStyle(), getTitleFont().getSize()+4));
        setTitleJustification(LEFT);
        setBorder(b);
    }
}
