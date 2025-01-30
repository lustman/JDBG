package org.jdbg.gui.buttonbar.process;

import javax.swing.*;

public class Process extends JButton {

    String processName;
    public long id;
    public Process(String processName, long id) {
        this.processName = processName;
        this.id = id;
    }

    @Override
    public String toString() {
        String hex = Integer.toHexString((int)id);
        return "0".repeat(10 - hex.length()) + hex + "-" + processName;
    }


}
