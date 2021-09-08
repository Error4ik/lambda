package org.example.jvm.task1;

import javassist.CannotCompileException;

public class Runner {

    public static void main(String[] args) throws CannotCompileException {
        //1.java.lang.OutOfMemoryError: Java heap space. You can use different data structures.
        OutOfMemoryErrorUsingCollection.test();

        //2.java.lang.OutOfMemoryError: Java heap space. Create big objects continuously and make them stay in memory.
//        OutOfMemoryErrorWithStringBuilder.test();

        //3. java.lang.OutOfMemoryError: Metaspace. Using library javassist and -XX:MaxMetaspaceSize=128m
//        OutOfMemoryErrorMetaspace.test();

        //4. java.lang.StackOverflowError. Use recursive methods. Don’t tune stack size.
//        StackOverFlowRecursive.stackOverFlowRecursive(1);

        //5 java.lang.StackOverflowError Using library javassist
//        new SoeNonRecursive().createClassWithLotsOfLocalVars("Soe1", 6000);
    }
}
