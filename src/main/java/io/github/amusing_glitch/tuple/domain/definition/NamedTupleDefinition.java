package io.github.amusing_glitch.tuple.domain.definition;

import java.util.List;

public record NamedTupleDefinition(
        String packageName,
        String name,
        List<NamedFieldDefinition> fieldDefinitions,
        Object node
) implements TupleDefinition {}