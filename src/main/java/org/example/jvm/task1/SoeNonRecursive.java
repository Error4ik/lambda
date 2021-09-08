package org.example.jvm.task1;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.CtNewMethod;

public class SoeNonRecursive {
    static final String generatedMethodName = "holderForVariablesMethod";

    Class<?> createClassWithLotsOfLocalVars(String generatedClassName, final int numberOfLocalVarsToGenerate) throws CannotCompileException {
        ClassPool pool = ClassPool.getDefault();
        CtClass generatedClass = pool.makeClass(generatedClassName);
        CtMethod generatedMethod = CtNewMethod.make(getMethodBody(numberOfLocalVarsToGenerate), generatedClass);
        generatedClass.addMethod(generatedMethod);
        return generatedClass.toClass();
    }

    private String getMethodBody(final int numberOfLocalVarsToGenerate) {
        StringBuilder methodBody = new StringBuilder("public static long ")
                .append(generatedMethodName).append("() {")
                .append(System.lineSeparator());
        StringBuilder antiDeadCodeEliminationString = new StringBuilder("long result = i0");
        long i = 0;
        while (i < numberOfLocalVarsToGenerate) {
            methodBody.append("  long i").append(i)
                    .append(" = ").append(i).append(";")
                    .append(System.lineSeparator());
            antiDeadCodeEliminationString.append("+").append("i").append(i);
            i++;
        }
        antiDeadCodeEliminationString.append(";");
        methodBody.append("  ").append(antiDeadCodeEliminationString)
                .append(System.lineSeparator())
                .append("  return result;")
                .append(System.lineSeparator())
                .append("}");
        return methodBody.toString();
    }
}
