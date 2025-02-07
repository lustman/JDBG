package org.jdbg.core.bytecode.asm;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;

import java.util.ArrayList;
import java.util.List;

public class BytecodeMethod {


    public InsnList instructions;
    public List<String> textFormat = new ArrayList<>();
    public List<Integer> offsets = new ArrayList<>();

    public String name;

    public String signature;


    public String getText() {
        StringBuilder text = new StringBuilder();

        for(String line : textFormat) {
            text.append(line);
            text.append('\n');
        }

        return text.toString();
    }
}
