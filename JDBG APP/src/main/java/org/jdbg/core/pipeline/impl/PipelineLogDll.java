package org.jdbg.core.pipeline.impl;

import com.sun.jna.platform.win32.Kernel32;
import org.jdbg.core.pipeline.Pipeline;
import org.jdbg.gui.log.logs.impl.LogAgent;
import org.jdbg.logger.Logger;

import java.util.List;

public class PipelineLogDll extends Pipeline {

    static PipelineLogDll instance;

    public PipelineLogDll() {
        super("\\\\.\\pipe\\jdbg_log");
    }
    public void start() {
        // await it
        Kernel32.INSTANCE.ConnectNamedPipe(pipeHandle, null);

        Logger.log("Client connected to log pipe");
        while(true) {
            byte[] bytes = readMessage();
            Logger.log("Got a log msg");
            LogAgent.getInstance().addLog(new String(bytes));
        }
    }

    public static void init() {
        instance = new PipelineLogDll();
    }

    public static PipelineLogDll getInstance() {
        return instance;
    }
}
