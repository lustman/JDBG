package org.jdbg.gui.tabs.classanalysis.asm;

import org.jdbg.Util;
import org.jdbg.gui.tabs.classanalysis.tabbed.ThinTabbedPane;
import org.objectweb.asm.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AsmTabbedPane extends ThinTabbedPane {


    public AsmTabbedPane() {
    }





    public void init(byte[] bytes) {
        removeAll();
        Icon folderIcon = Util.getIcon("assets/icons/ic_fluent_folder_24_filled.png", 15, 15);

        List<FieldStruct> fields = new ArrayList<>();




    }
}
