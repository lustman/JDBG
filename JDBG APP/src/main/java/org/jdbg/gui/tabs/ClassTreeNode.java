package org.jdbg.gui.tabs;

import javax.swing.tree.TreeNode;
import java.util.*;

public class ClassTreeNode implements TreeNode, Comparable<ClassTreeNode> {



    public static ClassTreeNode constructTree(List<String> classNames) {
        ClassTreeNode root = new ClassTreeNode(null, false, "project");

        for(String s : classNames) {
            addToTree(root, s);
        }

        return root;
    }

    public static void addToTree(ClassTreeNode current, String s) {
        String[] tokens = s.split("/");

        for(int i = 0; i < tokens.length-1; i++) {
            current = current.get(tokens[i]);
        }


        ClassTreeNode klass = new ClassTreeNode(current, true, tokens[tokens.length-1]);
        klass.setFullDir(s);
        current.children.add(klass);
    }



    private String fullDir;
    private final String name;
    SortedSet<ClassTreeNode> children = new TreeSet<>();

    private final boolean isClass;

    ClassTreeNode parent;




    public ClassTreeNode(ClassTreeNode parent, boolean isClass, String name) {
        this.parent = parent;
        this.isClass = isClass;
        this.name = name;
    }

    public void setFullDir(String fullDir) {
        this.fullDir = fullDir;
    }

    public String getFullDir() {
        return fullDir;
    }

    public String getName() {
        return name;
    }

    public boolean isClass() {
        return isClass;
    }

    private ClassTreeNode get(String thing) {
        Iterator<ClassTreeNode> it = children.iterator();
        for(int i = 0; i < children.size(); i++) {
            ClassTreeNode c = it.next();
            if(c.name.equals(thing)) {
                return c;
            }
        }

        ClassTreeNode node = new ClassTreeNode(this, false, thing);
        children.add(node);
        return node;
    }

    @Override
    public int compareTo(ClassTreeNode o) {
        return name.compareTo(o.name);
    }


    public TreeNode getChildAt(int childIndex) {
        Iterator<ClassTreeNode> it = children.iterator();
        for(int i = 0; i < childIndex; i++) {
            it.next();
        }

        return it.next();
    }

    public int getChildCount() {
        return children.size();
    }

    public TreeNode getParent() {
        return parent;
    }

    /**
     * Returns the index of <code>node</code> in the receivers children.
     * If the receiver does not contain <code>node</code>, -1 will be
     * returned.
     *
     * @param   node        node to be loked for
     * @return              index of specified node
     */
    public int getIndex(TreeNode node) {
        Iterator<ClassTreeNode> it = children.iterator();
        for(int i = 0; i < children.size(); i++) {
            if(it.next().equals(node)) {
                return i;
            }
        }

        return -1;
    }

    public boolean getAllowsChildren() {
        return !isClass;
    }


    public boolean isLeaf() {
        return isClass;
    }

    public Enumeration<? extends TreeNode> children() {
        return Collections.enumeration(children);
    }


    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof ClassTreeNode))
            return false;
        return name.equals(((ClassTreeNode) obj).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
