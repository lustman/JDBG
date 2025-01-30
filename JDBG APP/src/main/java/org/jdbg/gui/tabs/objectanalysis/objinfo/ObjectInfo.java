package org.jdbg.gui.tabs.objectanalysis.objinfo;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ObjectInfo extends JPanel {


    public ObjectInfo() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new ObjInfoBorder("Misc"));
        panel.add(new ObjectStringInfo("hello"));
        panel.add(new ObjectStringInfo("hello"));

        panel.setMaximumSize(new Dimension(10000, 500));
        add(panel);

        JTable table = new JTable(new String[][] {{"1", "2", "3"}, {"1", "2", "3"}, {"1", "2", "3"}}, new String[] {"Signature", "Name", "Value"});
        JScrollPane scrollPane = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(new ObjInfoBorder("Fields"));
        table.setBackground(getBackground());

        add(scrollPane);

    }
}
