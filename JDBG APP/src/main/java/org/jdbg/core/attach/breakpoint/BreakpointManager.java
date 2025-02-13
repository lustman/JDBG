package org.jdbg.core.attach.breakpoint;

import org.fife.ui.rtextarea.IconRowEvent;
import org.fife.ui.rtextarea.IconRowHeader;
import org.fife.ui.rtextarea.IconRowListener;
import org.jdbg.core.bytecode.asm.BytecodeMethod;
import org.jdbg.core.util.Pair;

import java.util.*;

public class BreakpointManager implements IconRowListener {

    static class Breakpoint {
        int line;

        public Breakpoint(int line) {
            this.line = line;
        }

        @Override
        public int hashCode() {
            return line;
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Breakpoint)) {
                return false;
            }

            return line == ((Breakpoint) obj).line;
        }
    }

    protected BytecodeMethod activeMethod;

    private Map<String, Set<Breakpoint>> breakpointMap;


    int pid;
    public BreakpointManager(int pid) {
        this.pid = pid;
        this.breakpointMap = new HashMap<>();
    }


    @Override
    public void bookmarkAdded(IconRowEvent iconRowEvent) {
        Set<Breakpoint> breakpoints = breakpointMap.computeIfAbsent(activeMethod.getIdentifier(), (k) -> new HashSet<>());
        breakpoints.add(new Breakpoint(iconRowEvent.getLine()));
        System.out.println(breakpoints.size());
    }

    @Override
    public void bookmarkRemoved(IconRowEvent iconRowEvent) {
        Set<Breakpoint> breakpoints = breakpointMap.computeIfAbsent(activeMethod.getIdentifier(), (k) -> new HashSet<>());
        breakpoints.remove(new Breakpoint(iconRowEvent.getLine()));
        System.out.println(breakpoints.size());
    }


    public void setActiveMethod(BytecodeMethod activeMethod) {
        this.activeMethod = activeMethod;
    }
}
