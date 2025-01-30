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
        addLog("Process JVM log", new LogProcess());

    }

    private void addLog(String name, Log log) {
        addTab(name, new JScrollPane(log, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,  JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
    }
}
