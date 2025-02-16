package org.jdbg.core.pipeline.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jna.platform.win32.Kernel32;
import org.jdbg.core.pipeline.Pipeline;
import org.jdbg.core.pipeline.response.SubGraphData;
import org.jdbg.gui.log.logs.impl.LogAgent;
import org.jdbg.logger.Logger;

import java.util.List;
import java.util.Map;

public class PipelineBreakpoint extends Pipeline {

    static class StackTraceElement {
        String signature;

        String klassSignature;
        String methodName;
        String methodSignature;
        int location;
    }

    static class BreakpointResponse {
        String klassSignature;
        String methodName;
        String methodSignature;
        List<StackTraceElement> stackTrace;

    }

    public static PipelineBreakpoint instance;
    public PipelineBreakpoint() {
        super("\\\\.\\pipe\\jdbg_breakpoint");
        instance = this;
    }
    public void start() {
        // await it

        Logger.log("Awaiting breakpoint pipe");

        if(!Kernel32.INSTANCE.ConnectNamedPipe(pipeHandle, null)) {
            Logger.log("Failed to connect to breakpoint pipe.");
        } else {
            Logger.log("Client connected to breakpoint pipe.");
        }

        while(true) {
            try {
                byte[] bytes = readMessage();
                Logger.log("Test");

                //ObjectMapper mapper = new ObjectMapper();
                //BreakpointResponse data = mapper.readValue(new String(bytes), new TypeReference<>() {});

                //Logger.log("Got a Breakpoint msg: " + new String(bytes));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static PipelineBreakpoint getInstance() {
        return instance;
    }
}
