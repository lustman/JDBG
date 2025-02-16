package org.jdbg.logger;

import org.jdbg.gui.log.logs.impl.LogJDBG;

public class Logger {

    public static void log(String msg) {
        synchronized (LogJDBG.getInstance()) {
            LogJDBG.getInstance().addLog(msg);
        }
    }

    public static void dbg(String msg) {
        System.out.println(msg);
    }
}
