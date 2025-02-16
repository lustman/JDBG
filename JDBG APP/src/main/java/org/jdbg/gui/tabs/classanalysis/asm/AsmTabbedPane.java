package org.jdbg.gui.tabs.classanalysis.asm;

import org.fife.ui.rtextarea.GutterIconInfo;
import org.jdbg.Util;
import org.jdbg.core.attach.AttachManager;
import org.jdbg.core.attach.breakpoint.BreakpointManager;
import org.jdbg.core.bytecode.asm.BytecodeMethod;
import org.jdbg.core.bytecode.asm.Bytecoder;
import org.jdbg.gui.tabs.classanalysis.codepanel.AsmCodePanel;
import org.jdbg.gui.tabs.classanalysis.codepanel.CodePanel;
import org.jdbg.gui.tabs.classanalysis.tabbed.ThinTabbedPane;
import org.jdbg.logger.Logger;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceMethodVisitor;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.List;

public class AsmTabbedPane extends ThinTabbedPane {


    Map<Component, BytecodeMethod> methodMap = new HashMap<>();
    public AsmTabbedPane() {
        setBackground(UIManager.getColor("TabbedPane.buttonHoverBackground"));
    }

    public void init(String klass, byte[] bytes) {
        removeAll();
        Icon fieldIcon = Util.getIcon("assets/icons/hexagon-letter-f.png", 15, 15);
        Icon methodIcon = Util.getIcon("assets/icons/hexagon-letter-m.png", 15, 15);


        Bytecoder b = new Bytecoder(klass, bytes);


        for(BytecodeMethod method : b.getMethods()) {

            // such that breakpoint changes affect it.
            AttachManager.getInstance().getBreakpointManager().setActiveMethod(method);
            CodePanel c = new AsmCodePanel(klass, method.getIndex());
            c.setSyntax("java-bytecode");
            c.setText(method.getText());
            addTab(method.getName(), methodIcon, c);
            methodMap.put(c, method);
        }


        ChangeListener listener = new ChangeListener() {


            Component previousComponent;

            @Override
            public void stateChanged(ChangeEvent e) {
                if (e.getSource() instanceof JTabbedPane) {
                    if (getSelectedComponent() == null || getSelectedComponent() == previousComponent) {
                        return;
                    }
                    onChange(getSelectedComponent());
                }
            }
        };

        addChangeListener(listener);
        listener.stateChanged(new ChangeEvent(this));
    }


    void onChange(Component c) {
        BytecodeMethod method = methodMap.get(c);
        if(method==null) {
            return;
        }


        AttachManager.getInstance().getBreakpointManager().setActiveMethod(method);
    }


}
