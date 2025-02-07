package org.jdbg.core.bytecode.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.util.TraceMethodVisitor;

import java.util.ArrayList;
import java.util.List;

public class Bytecoder {

    List<BytecodeMethod> methods = new ArrayList<>();
    List<BytecodeField> fields = new ArrayList<>();

    public Bytecoder(byte[] klassBytes) {

        OffsetClassReader reader = new OffsetClassReader(klassBytes);
        OffsetClassNode node = new OffsetClassNode(reader);
        reader.accept(node, ClassReader.SKIP_FRAMES);

        for(MethodNode m : node.methods) {
            OffsetMethodNode method = (OffsetMethodNode) m;
            BytecodeMethod theMethod = new BytecodeMethod();
            theMethod.instructions = method.instructions;
            theMethod.offsets = method.getOffsets();
            theMethod.textFormat = buildText(m.instructions);
            theMethod.name = method.name;
            theMethod.signature = method.signature;

            System.out.println(theMethod.instructions.size() + " - " + theMethod.offsets.size() + " - " + theMethod.textFormat.size());
            if(theMethod.instructions.size() != theMethod.textFormat.size()) {
                System.out.println(method.name);
            }
            methods.add(theMethod);
        }

        for(FieldNode field : node.fields) {
            BytecodeField theField = new BytecodeField();
            theField.modifiers = field.access;
            theField.name = field.name;
            theField.signature = field.signature;
            fields.add(theField);
        }
    }

    List<String> buildText(InsnList instructions) {
        JdbgTextifier jdbgTextifier = new JdbgTextifier();
        TraceMethodVisitor traceMethodVisitor = new TraceMethodVisitor(jdbgTextifier);

        for(AbstractInsnNode insn : instructions) {
            insn.accept(traceMethodVisitor);
        }

        List<?> textLines = jdbgTextifier.getText();
        List<String> text = new ArrayList<>();
        int minDist = 99999;

        for(Object line : textLines) {
            String strLine = line.toString();
            strLine = strLine.stripTrailing();


            text.add(strLine);

            int count = 0;
            for(char c : strLine.toCharArray()) {
                if(c==' ') {
                    count++;
                } else {
                    break;
                }
            }

            minDist = Math.min(minDist, count);
        }
        List<String> newText = new ArrayList<>();

        for(String t : text) {
            newText.add(t.substring(minDist));
        }

        return newText;
    }

    public List<BytecodeMethod> getMethods() {
        return methods;
    }

    public List<BytecodeField> getFields() {
        return fields;
    }
}
