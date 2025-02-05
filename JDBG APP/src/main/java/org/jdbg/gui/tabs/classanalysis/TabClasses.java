package org.jdbg.gui.tabs.classanalysis;

import org.jdbg.gui.tabs.classanalysis.asm.AsmTabbedPane;
import org.jdbg.gui.tabs.classanalysis.codepanel.CodePanel;
import org.jdbg.gui.tabs.ClassTreeNode;
import org.jdbg.gui.tabs.classanalysis.tabbed.factory.AsmTabbedFactory;
import org.jdbg.gui.tabs.classanalysis.tabbed.factory.BytecodeViewFactory;

import javax.swing.*;

public class TabClasses extends JSplitPane {

    JScrollPane hierarchy;
    BytecodeViewTabbedPane panel;
    public TabClasses(ClassTreeNode node) {
        super(JSplitPane.HORIZONTAL_SPLIT);
        setResizeWeight(0.15);



        ClassAnalysisTree tree = new ClassAnalysisTree(node);


        this.panel = (BytecodeViewTabbedPane)new BytecodeViewFactory(new CodePanel(), (AsmTabbedPane) new AsmTabbedFactory().makePane()).makePane();


        setFocusable(false);
        panel.setBackground(new JFrame().getBackground());
        JScrollPane pane = new JScrollPane(tree, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,  JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        pane.setFocusable(false);
        add(pane);
        add(panel);
    }

    public void init(String dec, byte[] klass) {
        panel.getDec().setText(dec);
        panel.getAsm().init(klass);
    }

}
