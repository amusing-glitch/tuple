package io.github.amusing_glitch.tuple.domain.definition;

public record NumberedTupleDefinition(
        String packageName,
        String name,
        int size,
        Object node
) implements TupleDefinition {}