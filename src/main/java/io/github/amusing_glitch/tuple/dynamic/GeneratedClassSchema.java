package io.github.amusing_glitch.tuple.dynamic;

public record GeneratedClassSchema(
        String packageName,
        String className,
        String javaCode
) {
    public String completeClassName() {
        return "%s.%s".formatted(packageName, className);
    }
}
