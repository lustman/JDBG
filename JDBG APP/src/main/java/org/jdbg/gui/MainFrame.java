package org.jdbg.gui;

import org.jdbg.Util;
import org.jdbg.gui.buttonbar.buttons.AttachProcessButton;
import org.jdbg.gui.menubar.TopMenuBar;
import org.jdbg.gui.log.LogPane;
import org.jdbg.logger.Logger;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class MainFrame extends JFrame {

    MainTabbedPane mainTabbedPane = new MainTabbedPane();
    private static MainFrame instance;

    JLabel text = new JLabel();

    public MainFrame() {
        super("JDBG");
        instance = this;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initUI();
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        setupMenuBar();
        setupTopButtons();
        setupSplitPane();

        pack();
        getContentPane().requestFocus(); // remove annoying focus thing
        Logger.log("JDBG initialisation complete");
    }

    void initUI() {
        try {
            Icon folderIcon = Util.getIcon("assets/icons/ic_fluent_folder_24_filled.png", 15, 15);
            Icon coffeeIcon = Util.getIcon("assets/icons/ic_fluent_drink_coffee_24_filled.png", 15, 15);
            UIManager.put("Tree.closedIcon", folderIcon);
            UIManager.put("Tree.openIcon", folderIcon);

            UIManager.put("Tree.leafIcon", coffeeIcon);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    void setupMenuBar() {
        setJMenuBar(new TopMenuBar());
    }

    void setupTopButtons() {
        JPanel topButtons = new JPanel();

        topButtons.add(new AttachProcessButton());
        //topButtons.add(new AttachJarButton());

        text.setText("Not attached");
        text.setForeground(Color.RED);
        topButtons.add(text);

        topButtons.setLayout(new FlowLayout(FlowLayout.LEADING));
        topButtons.setAlignmentX(Component.LEFT_ALIGNMENT);
        getContentPane().add(topButtons);
    }

    void setupSplitPane() {
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setPreferredSize(new Dimension(1000, 700));
        splitPane.setResizeWeight(0.87);
        splitPane.add(mainTabbedPane);
        splitPane.add((new LogPane()));
        getContentPane().add(splitPane);
    }

    public MainTabbedPane getMainPane() {
        return mainTabbedPane;
    }


    public void setAttached(String process) {
        text.setForeground(Color.GREEN);
        text.setText("Attached: " + process);
    }

    public static MainFrame getInstance() {
        return instance;
    }
}
