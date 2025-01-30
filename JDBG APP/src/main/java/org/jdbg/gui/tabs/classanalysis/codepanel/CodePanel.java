package org.jdbg.gui.tabs.classanalysis.codepanel;


import org.fife.ui.rtextarea.RTextScrollPane;
import org.jdbg.gui.tabs.classanalysis.codepanel.comp.CodeText;

import javax.swing.*;
import java.awt.*;

public class CodePanel extends JPanel {

    CodeText text;
    public CodePanel() {
        setLayout(new GridLayout());
        text = new CodeText();
        RTextScrollPane pane = new RTextScrollPane(text);
        pane.getGutter().setBorderColor(UIManager.getColor("TextArea.background"));

        add(pane);
    }

    public void setText(String s) {
        text.setText(s);
    }




}
