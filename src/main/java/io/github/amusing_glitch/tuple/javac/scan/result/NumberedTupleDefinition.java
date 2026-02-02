package io.github.amusing_glitch.tuple.javac.scan.result;


public record NumberedTupleDefinition(
        String className,
        String methodName,
        int argumentCount
) {
}
