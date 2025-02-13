package org.jdbg.gui.tabs.classanalysis.codepanel;

import org.fife.ui.rtextarea.IconRowEvent;
import org.fife.ui.rtextarea.IconRowHeader;
import org.fife.ui.rtextarea.IconRowListener;
import org.jdbg.Util;
import org.jdbg.logger.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;

public class AsmCodePanel extends CodePanel {


    public AsmCodePanel() {
        super();

        scrollPane.setIconRowHeaderEnabled(true);
        scrollPane.getGutter().setIconRowHeaderInheritsGutterBackground(true);
        scrollPane.getGutter().setBookmarkingEnabled(true);
        scrollPane.getGutter().setBookmarkIcon(createBreakpointIcon());
        scrollPane.getGutter().addIconRowListener(new IconRowListener() {
            @Override
            public void bookmarkAdded(IconRowEvent iconRowEvent) {
                Logger.log("Added");
            }

            @Override
            public void bookmarkRemoved(IconRowEvent iconRowEvent) {
                Logger.log("removed");

            }
        });

        Icon folderIcon = Util.getIcon("assets/icons/ic_fluent_folder_24_filled.png", 15, 15);


    }
    /**
     * Creates a simple red circle icon to represent a breakpoint.
     */
    private Icon createBreakpointIcon() {
        int size = 12;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        // Enable antialiasing for a smoother circle.
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(255, 0, 0, 128));
        g2.fillOval(0, 0, size, size);
        g2.dispose();
        return new PaddedIcon(new ImageIcon(image), 4);
    }
}
