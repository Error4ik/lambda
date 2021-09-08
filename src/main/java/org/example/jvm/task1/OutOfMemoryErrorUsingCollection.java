package org.example.jvm.task1;

import java.util.ArrayList;
import java.util.List;

public class OutOfMemoryErrorUsingCollection {

    public static void test() {
        List<Object> list = new ArrayList<>();
        while (true) {
            Object data = new byte[64 * 1024 - 1];
            list.add(data);
        }
    }
}
