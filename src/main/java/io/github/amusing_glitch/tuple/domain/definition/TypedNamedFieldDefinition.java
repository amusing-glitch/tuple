package io.github.amusing_glitch.tuple.domain.definition;

import io.github.amusing_glitch.tuple.domain.type.TypeInfo;
import io.github.amusing_glitch.tuple.domain.type.Type;

public record TypedNamedFieldDefinition(
        String name,
        Type type,
        Object node
) implements NamedFieldDefinition, TypeInfo {}
