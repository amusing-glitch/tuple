package io.github.amusing_glitch.tuple.domain.definition.field.typed;

import io.github.amusing_glitch.tuple.domain.definition.field.NamedFieldDefinition;
import io.github.amusing_glitch.tuple.domain.type.Type;

public record TypedNamedFieldDefinition(
        String name,
        Type type,
        Object node
) implements NamedFieldDefinition, TypedFieldDefinition {
}
