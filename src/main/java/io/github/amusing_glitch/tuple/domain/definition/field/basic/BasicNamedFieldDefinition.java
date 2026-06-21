package io.github.amusing_glitch.tuple.domain.definition.field.basic;

import io.github.amusing_glitch.tuple.domain.definition.field.NamedFieldDefinition;

public record BasicNamedFieldDefinition(
        String name,
        Object node
) implements NamedFieldDefinition, BasicFieldDefinition {
}
