package org.jdbg.core.bytecode.decompiler;

public abstract class ClassDecompiler {

    byte[] bytes;
    public ClassDecompiler(byte[] bytes) {
        this.bytes = bytes;
    }

    public abstract String decompile();


}
