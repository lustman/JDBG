package org.jdbg;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Modifier;
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


    public static String buildModifiers(int modifiers) {
        StringBuilder sb = new StringBuilder();

        if (Modifier.isPublic(modifiers)) {
            sb.append("public ");
        }
        if (Modifier.isProtected(modifiers)) {
            sb.append("protected ");
        }
        if (Modifier.isPrivate(modifiers)) {
            sb.append("private ");
        }
        if (Modifier.isAbstract(modifiers)) {
            sb.append("abstract ");
        }
        if (Modifier.isStatic(modifiers)) {
            sb.append("static ");
        }
        if (Modifier.isFinal(modifiers)) {
            sb.append("final ");
        }
        if (Modifier.isTransient(modifiers)) {
            sb.append("transient ");
        }
        if (Modifier.isVolatile(modifiers)) {
            sb.append("volatile ");
        }
        if (Modifier.isSynchronized(modifiers)) {
            sb.append("synchronized ");
        }
        if (Modifier.isNative(modifiers)) {
            sb.append("native ");
        }
        if (Modifier.isStrict(modifiers)) {
            sb.append("strictfp ");
        }

        // Remove the trailing space if present and return the string.
        return sb.toString().trim();
    }

}
