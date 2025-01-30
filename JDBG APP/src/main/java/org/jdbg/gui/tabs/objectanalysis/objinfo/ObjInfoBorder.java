package org.jdbg.gui.tabs.objectanalysis.objinfo;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ObjInfoBorder extends TitledBorder {

    public ObjInfoBorder(String title) {
        super(title);
        Border b = BorderFactory.createMatteBorder(1, 0, 0, 0, UIManager.getColor("Button.borderColor"));
        setTitleFont(new Font(getTitleFont().getName(), getTitleFont().getStyle(), getTitleFont().getSize()+4));
        setTitleJustification(LEFT);
        setBorder(b);
    }
}
