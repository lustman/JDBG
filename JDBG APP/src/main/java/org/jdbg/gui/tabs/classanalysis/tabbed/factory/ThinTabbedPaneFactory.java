package org.jdbg.gui.tabs.classanalysis.tabbed.factory;

import org.jdbg.gui.tabs.classanalysis.tabbed.ThinTabbedPane;

import javax.swing.*;
import java.awt.*;

public abstract class ThinTabbedPaneFactory {

    ThinTabbedPane pane;

    protected abstract ThinTabbedPane getPane();

    public ThinTabbedPane makePane() {




        int temp = (int) UIManager.get("TabbedPane.tabHeight");
        UIManager.put("TabbedPane.tabHeight", 30);
        UIManager.put( "TabbedPane.showTabSeparators", true);

        ThinTabbedPane pane = getPane();
        UIManager.put("TabbedPane.tabHeight", temp);
        UIManager.put( "TabbedPane.showTabSeparators", false);

        return pane;
    }
}
