package org.jdbg.gui.log.logs.impl;

import org.jdbg.gui.log.logs.Log;

public class LogJDBG extends Log {

    static Log log;

    public LogJDBG() {
        log = this;
    }

    public static Log getInstance() {
        return log;
    }
    @Override
    public void addLog(String text) {
        setText(getText() + '\n' + text);
    }
}
