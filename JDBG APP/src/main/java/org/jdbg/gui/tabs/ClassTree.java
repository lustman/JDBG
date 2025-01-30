package org.jdbg.gui.tabs;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class ClassTree extends JTree {


    ClassTreeNode prev = null;
    public ClassTree(ClassTreeNode node) {
        super(node);



        setUI(new BasicTreeUI());
        setFont(new Font(getFont().getName(), getFont().getStyle(), getFont().getSize()+2));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                ClassTreeNode node = (ClassTreeNode)getLastSelectedPathComponent();

                if(node!=null && node.isClass() && node != prev && SwingUtilities.isLeftMouseButton(e)) {
                    leftClick(node);
                    prev = node;
                }

                if(SwingUtilities.isRightMouseButton(e)) {
                    int r = getRowForLocation(e.getX(), e.getY());
                    ClassTreeNode rightClicked = (ClassTreeNode)getPathForRow(r).getLastPathComponent();
                    rightClick(rightClicked, e);


                }
            }
        });

    }

    protected abstract void rightClick(ClassTreeNode klass, MouseEvent e);

    protected abstract void leftClick(ClassTreeNode klass);


}
