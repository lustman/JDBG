package org.jdbg.core.attach.breakpoint;

import org.fife.ui.rtextarea.IconRowEvent;
import org.fife.ui.rtextarea.IconRowHeader;
import org.fife.ui.rtextarea.IconRowListener;
import org.jdbg.core.CoreInterface;
import org.jdbg.core.bytecode.asm.BytecodeMethod;
import org.jdbg.core.util.Pair;

import java.util.*;

public class BreakpointManager implements IconRowListener {

    static class Breakpoint {

        int methodIdx;
        int line;

        public Breakpoint(int methodIdx, int line) {
            this.line = line;
            this.methodIdx = methodIdx;
        }

        @Override
        public int hashCode() {
            return (line * 31)^methodIdx;
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Breakpoint)) {
                return false;
            }

            return line == ((Breakpoint) obj).line && methodIdx == ((Breakpoint) obj).methodIdx;
        }
    }

    protected BytecodeMethod activeMethod;

    private final Map<String, Set<Breakpoint>> breakpointMap;


    int pid;
    public BreakpointManager(int pid) {
        this.pid = pid;
        this.breakpointMap = new HashMap<>();
    }


    @Override
    public void bookmarkAdded(IconRowEvent iconRowEvent) {
        Set<Breakpoint> breakpoints = breakpointMap.computeIfAbsent(activeMethod.getParentClass(), (k) -> new HashSet<>());
        breakpoints.add(new Breakpoint(activeMethod.getIndex(), iconRowEvent.getLine()));
        int offset =  activeMethod.getOffsets().get(iconRowEvent.getLine()-1);

        CoreInterface.getInstance().addBreakpoint(activeMethod.getParentClass(), activeMethod.getIndex(), offset);
    }

    @Override
    public void bookmarkRemoved(IconRowEvent iconRowEvent) {
        Set<Breakpoint> breakpoints = breakpointMap.computeIfAbsent(activeMethod.getParentClass(), (k) -> new HashSet<>());
        breakpoints.remove(new Breakpoint(activeMethod.getIndex(), iconRowEvent.getLine()));
    }










    public void setActiveMethod(BytecodeMethod activeMethod) {
        this.activeMethod = activeMethod;
    }
}
