package org.example.jvm.task1;

public class StackOverFlowRecursive {

    public static int stackOverFlowRecursive(int num) {
        return stackOverFlowRecursive(num + 1);
    }
}
