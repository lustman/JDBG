package org.jdbg.gui.tabs.objectanalysis.objinfo;

import javax.swing.*;
import java.awt.*;

public class ObjectStringInfo extends JTextArea {

    public ObjectStringInfo(String info) {
        super(info);
        setPreferredSize(new Dimension(200, 20));
        setEditable(false);
    }
}
