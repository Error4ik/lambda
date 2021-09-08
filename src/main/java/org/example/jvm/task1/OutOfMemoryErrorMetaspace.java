package org.example.jvm.task1;

import javassist.CannotCompileException;
import javassist.ClassPool;

public class OutOfMemoryErrorMetaspace {

    public static final ClassPool classPool = ClassPool.getDefault();

    public static void test() {
        for (int i = 0; i < 1_000_000; i++) {
            try {
                Class<?> c = classPool.makeClass("com.saket.demo.Metaspace" + i).toClass();
            } catch (CannotCompileException e) {
                e.printStackTrace();
            }
        }
    }
}
