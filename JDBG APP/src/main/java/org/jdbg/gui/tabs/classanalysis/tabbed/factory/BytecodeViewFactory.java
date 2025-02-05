package org.jdbg.gui.tabs.classanalysis.tabbed.factory;

import org.jdbg.gui.tabs.classanalysis.BytecodeViewTabbedPane;
import org.jdbg.gui.tabs.classanalysis.asm.AsmTabbedPane;
import org.jdbg.gui.tabs.classanalysis.codepanel.CodePanel;
import org.jdbg.gui.tabs.classanalysis.tabbed.ThinTabbedPane;

import javax.swing.*;

public class BytecodeViewFactory extends ThinTabbedPaneFactory {

    CodePanel dec;
    AsmTabbedPane asm;
    public BytecodeViewFactory(CodePanel decompiled, AsmTabbedPane assembly) {
        this.dec= decompiled;
        this.asm = assembly;
    }
    @Override
    protected ThinTabbedPane getPane() {
        return new BytecodeViewTabbedPane(dec, asm);
    }
}
