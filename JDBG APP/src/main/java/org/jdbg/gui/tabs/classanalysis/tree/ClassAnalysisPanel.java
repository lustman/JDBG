package org.jdbg.gui.tabs.classanalysis.tree;

import org.jdbg.gui.tabs.ClassTreeNode;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ClassAnalysisPanel extends JPanel {



    ClassAnalysisTree tree;

    public ClassAnalysisPanel(ClassAnalysisTree tree) {
        this.tree = tree;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JScrollPane pane = new JScrollPane(tree, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,  JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pane.setFocusable(false);
        add(pane);

        JPanel filter = new JPanel();
        filter.setLayout(new BorderLayout());
        filter.setBorder(new EmptyBorder(3, 3, 3, 3));
        JTextField field = new JTextField();
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                for(ClassTreeNode node : tree.getLeaves()) {
                    if(node.getFullDir().contains(field.getText() + e.getKeyChar())) {
                        ( (ClassTreeNode)node.getParent()).add(node);
                    } else {
                        ( (ClassTreeNode)node.getParent()).removeFromVisible(node);
                    }
                }

                tree.reload();

            }
        });



        filter.setMaximumSize(new Dimension(100000, 100));
        filter.add(field);

        add(filter);

    }


}
