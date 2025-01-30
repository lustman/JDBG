package org.jdbg.gui.tabs.classanalysis;

import org.jdbg.gui.tabs.classanalysis.codepanel.CodePanel;
import org.jdbg.gui.tabs.ClassTreeNode;

import javax.swing.*;

public class TabClasses extends JSplitPane {

    JScrollPane hierarchy;
    CodePanel panel;
    public TabClasses(ClassTreeNode node) {
        super(JSplitPane.HORIZONTAL_SPLIT);
        setResizeWeight(0.15);



        ClassAnalysisTree tree = new ClassAnalysisTree(node);


        this.panel = new CodePanel();


        setFocusable(false);
        panel.setBackground(new JFrame().getBackground());
        JScrollPane pane = new JScrollPane(tree, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,  JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        pane.setFocusable(false);
        add(pane);
        add(panel);
    }

    public void setCode(String s) {
        panel.setText(s);
    }

}
