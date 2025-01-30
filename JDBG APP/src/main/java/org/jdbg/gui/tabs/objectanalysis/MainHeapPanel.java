package org.jdbg.gui.tabs.objectanalysis;

import org.jdbg.core.CoreInterface;
import org.jdbg.gui.tabs.objectanalysis.objinfo.ObjectInfo;
import org.jdbg.gui.tabs.objectanalysis.objlist.ObjectListPanel;
import org.jdbg.gui.tabs.objectanalysis.objlist.TagItem;

import javax.swing.*;
import java.awt.*;

public class MainHeapPanel extends JPanel {

    ObjectListPanel list;
    ObjectInfo objectInfo;
    public MainHeapPanel(ObjectAnalysisClassTree tree) {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        list = new ObjectListPanel(tree);
        add(list, gbc);


        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.BOTH;
        g.gridwidth = GridBagConstraints.REMAINDER;
        g.gridheight = GridBagConstraints.REMAINDER;


        g.weightx = 1;
        g.weighty = 1;
        g.anchor = GridBagConstraints.SOUTHWEST;


        objectInfo = new ObjectInfo();
        add(new JScrollPane(objectInfo, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), g);
    }

    public void initObjectInfo(TagItem tagItem) {
        CoreInterface.getInstance().getReferences(tagItem);
        CoreInterface.getInstance().getFields(tagItem);


    }

    public ObjectListPanel getObjectList() {
        return list;
    }
}
