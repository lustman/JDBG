package org.jdbg;

import com.formdev.flatlaf.FlatDarkLaf;
import org.jdbg.core.pipeline.impl.logdll.PipelineLogDll;
import org.jdbg.gui.MainFrame;
import org.jdbg.core.pipeline.impl.main.PipelineMain;
import org.jdbg.logger.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    // TODO need to make a process termination callback
    public static void main(String[] args) {
        WrapperMain.wrap();


        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());

            List<String> colors = new ArrayList<String>();
            for (Map.Entry<Object, Object> entry : UIManager.getDefaults().entrySet()) {
                if (entry.getValue() instanceof Color) {
                    System.out.println(entry.getKey() + " - " + ((Color) entry.getValue()).getRGB());
                }
            }


        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final MainFrame wnd = new MainFrame();
                wnd.setVisible(true);

                PipelineMain.init();
                PipelineLogDll.init();

                //TODO make this better
                Thread logDll = new Thread(() -> {
                    PipelineLogDll.getInstance().start();
                });
                logDll.start();


                Thread shutdown = new Thread(() -> {
                    PipelineMain.getInstance().shutdown();
                });
                Runtime.getRuntime().addShutdownHook(shutdown);
            }
        });




    }

}