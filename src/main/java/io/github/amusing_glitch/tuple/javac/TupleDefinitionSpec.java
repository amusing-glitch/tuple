package io.github.amusing_glitch.tuple.javac;

public record TupleDefinitionSpec(
        String packageName,
        String className,
        String methodName
) {
    public String completeClassName() {
        return packageName + "." + className;
    }
}
