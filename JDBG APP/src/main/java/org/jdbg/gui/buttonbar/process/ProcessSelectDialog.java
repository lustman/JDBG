package org.jdbg.gui.buttonbar.process;

import org.jdbg.gui.buttonbar.process.buttons.AttachButton;

import javax.swing.*;
import java.awt.*;

public class ProcessSelectDialog extends JDialog {

    public ProcessSelectDialog() {
        setTitle("Select your process");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setSize(new Dimension(300, 500));

        setModal(true);
        requestFocus();

        ProcessList processList = new ProcessList();
        add(new JScrollPane(processList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,  JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));


        JPanel buttons = new JPanel();
        buttons.setMaximumSize(new Dimension(300, 100));
        buttons.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttons.add(new AttachButton(processList, this));


        add(buttons);
    }
}
