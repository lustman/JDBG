package org.jdbg.gui.buttonbar.process;

import org.jdbg.core.Util;

import javax.swing.*;
import java.util.List;

public class ProcessList extends JList<Process> {

    public ProcessList() {
        DefaultListModel<Process> model = new DefaultListModel<>();
        List<Util.ProcessData> data = Util.getRunningProcesses();
        System.out.println("Hello");


        for(Util.ProcessData d : data) {
            Process p =new Process(d.processName, d.processID);
            model.addElement(p);
        }

        setModel(model);
    }
}
