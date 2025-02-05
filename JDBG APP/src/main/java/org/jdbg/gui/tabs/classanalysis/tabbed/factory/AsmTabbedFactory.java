package org.jdbg.gui.tabs.classanalysis.tabbed.factory;

import org.jdbg.gui.tabs.classanalysis.asm.AsmTabbedPane;
import org.jdbg.gui.tabs.classanalysis.tabbed.ThinTabbedPane;

public class AsmTabbedFactory extends ThinTabbedPaneFactory {



    public AsmTabbedFactory() {
    }

    @Override
    protected ThinTabbedPane getPane() {
        return new AsmTabbedPane();
    }
}
