package org.jdbg.core.attach.breakpoint;

import org.fife.ui.rtextarea.IconRowEvent;
import org.fife.ui.rtextarea.IconRowHeader;
import org.fife.ui.rtextarea.IconRowListener;
import org.jdbg.core.CoreInterface;
import org.jdbg.core.bytecode.asm.BytecodeMethod;
import org.jdbg.core.util.Pair;
import org.jdbg.logger.Logger;

import java.util.*;

public class BreakpointManager implements IconRowListener {

    static class Breakpoint {

        int methodIdx;
        int offset;

        public Breakpoint(int methodIdx, int offset) {
            this.offset = offset;
            this.methodIdx = methodIdx;
        }

        @Override
        public int hashCode() {
            return Objects.hash(offset, methodIdx);
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Breakpoint)) {
                return false;
            }

            return offset == ((Breakpoint) obj).offset && methodIdx == ((Breakpoint) obj).methodIdx;
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

        if(iconRowEvent.getLine()-1 >= activeMethod.getOffsets().size()) {
            Logger.log("Breakpoint not valid.");
            return;
        }
        int offset =  activeMethod.getOffsets().get(iconRowEvent.getLine()-1);

        Breakpoint b = new Breakpoint(activeMethod.getIndex(), offset);


        if(breakpoints.contains(b)) {
            Logger.log("Breakpoint already exists at same offset. Not adding new one.");
        } else {
            breakpoints.add(b);
            CoreInterface.getInstance().addBreakpoint(activeMethod.getParentClass(), activeMethod.getIndex(), offset);
        }
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
