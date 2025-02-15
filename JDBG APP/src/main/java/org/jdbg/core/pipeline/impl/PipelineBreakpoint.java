package org.jdbg.core.pipeline.impl;

import com.sun.jna.platform.win32.Kernel32;
import org.jdbg.core.pipeline.Pipeline;
import org.jdbg.gui.log.logs.impl.LogAgent;
import org.jdbg.logger.Logger;

public class PipelineBreakpoint extends Pipeline {

    public PipelineBreakpoint() {
        super("\\\\.\\pipe\\jdbg_breakpoint");

    }
    public void start() {
        // await it

        Logger.log("Awaiting breakpoint pipe");
        Kernel32.INSTANCE.ConnectNamedPipe(pipeHandle, null);

        Logger.log("Client connected to breakpoint pipe");
        while(true) {
            byte[] bytes = readMessage();
            Logger.log("Got a Breakpoint msg");
            LogAgent.getInstance().addLog(new String(bytes));
        }
    }
}
