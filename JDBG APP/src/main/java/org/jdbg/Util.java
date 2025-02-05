package org.jdbg;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Util {


    public static ImageIcon getIcon(String path, int width, int height) {
        try {
            URL folderResource = Util.class.getClassLoader().getResource(path);
            ImageIcon folderIcon = new ImageIcon(new ImageIcon(folderResource).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            return folderIcon;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
