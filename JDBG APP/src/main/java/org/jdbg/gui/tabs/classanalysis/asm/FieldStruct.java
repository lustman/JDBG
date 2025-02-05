package org.jdbg.gui.tabs.classanalysis.asm;

public class FieldStruct {

    public FieldStruct(int modifiers, String name, String descriptor, String signature, Object value) {
        this.modifiers = modifiers;
        this.name = name;
        this.descriptor = descriptor;
        this.signature = signature;
        this.value = value;
    }

    public int modifiers;
    public String name;
    public String descriptor;
    public String signature;
    public Object value;
}
