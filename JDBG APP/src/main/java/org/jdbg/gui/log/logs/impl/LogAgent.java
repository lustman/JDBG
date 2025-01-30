package org.jdbg.gui.log.logs.impl;

import org.jdbg.gui.log.logs.Log;

public class LogAgent extends Log {

    static Log log;

    public LogAgent() {
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
