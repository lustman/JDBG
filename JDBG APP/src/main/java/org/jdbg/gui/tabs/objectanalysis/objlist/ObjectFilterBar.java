package org.jdbg.gui.tabs.objectanalysis.objlist;

import org.jdbg.core.CoreInterface;
import org.jdbg.gui.tabs.objectanalysis.ObjectAnalysisClassTree;
import org.jdbg.logger.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ObjectFilterBar extends JPanel {

    JButton filterButton = new JButton("Filter");
    JTextField filterField = new JTextField();

    ObjectAnalysisClassTree tree;

    ObjectListPanel objectList;
    public ObjectFilterBar(ObjectAnalysisClassTree tree, ObjectListPanel list) {
        this.tree = tree;
        this.objectList = list;
        add(filterButton);
        add(filterField);
        setBackground(UIManager.getColor("TextArea.background"));

        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filter = filterField.getText();
                Logger.log("Filter: " + filter);

                if(tree.getActiveClass() != null) {

                    objectList.setTags(CoreInterface.getInstance().getObjectTags(tree.getActiveClass().getFullDir(), filter),
                            tree.getActiveClass().getFullDir(),
                            tree.getActiveClass().getName());
                } else {
                    Logger.log("No active class.");
                }


            }
        });

        filterField.setPreferredSize(new Dimension(120, 23));
        setMaximumSize(new Dimension(300, 100));
        revalidate();
    }
}
