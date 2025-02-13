package org.jdbg.gui.log;

import org.jdbg.gui.log.logs.Log;
import org.jdbg.gui.log.logs.impl.LogAgent;
import org.jdbg.gui.log.logs.impl.LogJDBG;
import org.jdbg.gui.log.logs.impl.LogProcess;

import javax.swing.*;

public class LogPane extends JTabbedPane {

    public LogPane(){
        addLog("JDBG log", new LogJDBG());
        addLog("Agent log", new LogAgent());
    }

    private void addLog(String name, Log log) {

        JScrollPane s = new JScrollPane(log, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,  JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        s.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, UIManager.getColor("Button.borderColor")));


        addTab(name, s);
    }
}
