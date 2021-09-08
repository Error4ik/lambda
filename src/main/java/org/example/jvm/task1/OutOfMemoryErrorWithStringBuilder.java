package org.example.jvm.task1;

public class OutOfMemoryErrorWithStringBuilder {

    public static void test() {
        StringBuilder sb = new StringBuilder();
        while (true) {
            sb.append("Hello");
        }
    }
}
