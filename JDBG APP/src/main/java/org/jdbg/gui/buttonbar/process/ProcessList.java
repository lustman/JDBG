package org.jdbg.gui.buttonbar.process;

import org.jdbg.core.Util;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Comparator;

public class ProcessList extends JList<Process> {

    public ProcessList() {
        DefaultListModel<Process> model = new DefaultListModel<>();
        List<Util.ProcessData> data = Util.getRunningProcesses();

        data.sort(Comparator.comparingLong(d -> d.processID));

        for(Util.ProcessData d : data) {
            Process p =new Process(d.processName, d.processID);
            model.addElement(p);
        }

        setModel(model);
        setFont(new Font("Monospaced", Font.PLAIN, 12)); // DON'T TOUCH IT!
    }
}
