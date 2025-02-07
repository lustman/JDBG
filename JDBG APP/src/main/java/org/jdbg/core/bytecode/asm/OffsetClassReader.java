package org.jdbg.core.bytecode.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.MethodNode;

public class OffsetClassReader extends ClassReader {


    int currentBytecodeOffset;
    OffsetClassReader(byte[] bytes) {
        super(bytes);
    }

    @Override
    protected void readBytecodeInstructionOffset(int bytecodeOffset) {
        this.currentBytecodeOffset = bytecodeOffset;
    }

    public int getCurrentBytecodeOffset() {
        return currentBytecodeOffset;
    }
}
