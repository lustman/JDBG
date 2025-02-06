package org.jdbg.gui.tabs.classanalysis.asm;

import org.jdbg.Util;
import org.jdbg.gui.tabs.classanalysis.codepanel.CodePanel;
import org.jdbg.gui.tabs.classanalysis.tabbed.ThinTabbedPane;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceMethodVisitor;

import javax.swing.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class AsmTabbedPane extends ThinTabbedPane {


    public AsmTabbedPane() {
        setBackground(UIManager.getColor("TabbedPane.buttonHoverBackground"));
    }






    public void init(byte[] bytes) {
        removeAll();
        Icon fieldIcon = Util.getIcon("assets/icons/hexagon-letter-f.png", 15, 15);
        Icon methodIcon = Util.getIcon("assets/icons/hexagon-letter-m.png", 15, 15);

        ClassNode node = new ClassNode();
        ClassReader reader = new ClassReader(bytes);


        reader.accept(node, ClassReader.SKIP_FRAMES);


        for(FieldNode field : node.fields) {
            addTab(field.name, fieldIcon, new JPanel());
        }

        for(MethodNode method : node.methods) {
            Textifier textifier = new Textifier();
            TraceMethodVisitor traceMethodVisitor = new TraceMethodVisitor(textifier);

            for(AbstractInsnNode insn : method.instructions) {
                method.visitCode();
                insn.accept(traceMethodVisitor);
            }

            List<?> textLines = textifier.getText();
            StringBuilder text = new StringBuilder();
            for(Object line : textLines) {
                text.append(line.toString());
            }

            CodePanel c = new CodePanel();
            c.setText(text.toString());


            addTab(method.name, methodIcon, c);

        }


    }
}
