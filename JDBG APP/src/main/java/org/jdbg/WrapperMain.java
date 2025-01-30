package org.jdbg;

import java.util.ArrayList;
import java.util.List;

public class WrapperMain {

    public static List<WrapperTest> test = new ArrayList<>();
    public static void wrap() {
        for(int i = 0; i < 10000; i++) {
            test.add(new WrapperTest(i));
        }
    }
}
