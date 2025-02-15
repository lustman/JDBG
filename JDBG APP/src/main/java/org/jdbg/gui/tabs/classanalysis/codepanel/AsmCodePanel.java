package org.jdbg.gui.tabs.classanalysis.codepanel;

import org.fife.ui.rtextarea.IconRowEvent;
import org.fife.ui.rtextarea.IconRowHeader;
import org.fife.ui.rtextarea.IconRowListener;
import org.jdbg.Util;
import org.jdbg.core.attach.AttachManager;
import org.jdbg.core.attach.breakpoint.BreakpointManager;
import org.jdbg.logger.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.util.Set;

public class AsmCodePanel extends CodePanel {


    Set<BreakpointManager.Breakpoint> breakpoints;
    public AsmCodePanel(Set<BreakpointManager.Breakpoint> breakPoints) {
        super();

        scrollPane.setIconRowHeaderEnabled(true);
        scrollPane.getGutter().setIconRowHeaderInheritsGutterBackground(true);
        scrollPane.getGutter().setBookmarkingEnabled(true);
        scrollPane.getGutter().setBookmarkIcon(createBreakpointIcon());
        scrollPane.getGutter().addIconRowListener(AttachManager.getInstance().getBreakpointManager());
        this.breakpoints = breakPoints;


    }

    @Override
    public void setText(String s) {
        super.setText(s);

        breakpoints.forEach((breakpoint) -> {
            try {
                scrollPane.getGutter().toggleBookmark(breakpoint.line);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        });
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
