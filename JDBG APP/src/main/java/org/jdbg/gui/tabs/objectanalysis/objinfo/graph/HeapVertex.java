package org.jdbg.gui.tabs.objectanalysis.objinfo.graph;

public class HeapVertex {


    Integer tag;
    String origin;


    public HeapVertex(Integer tag) {
        this(tag, "N/A");
    }

    public HeapVertex(Integer tag, String origin) {
        this.tag = tag;
        this.origin = origin;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof HeapVertex) {
            return tag.equals(((HeapVertex) obj).tag);
        }

        if(obj instanceof Integer) {
            return tag.equals(obj);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return tag;
    }

    @Override
    public String toString() {
        return origin + " (" + tag + ")";
    }
}
