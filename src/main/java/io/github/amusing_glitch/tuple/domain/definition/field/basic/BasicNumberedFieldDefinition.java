package io.github.amusing_glitch.tuple.domain.definition.field.basic;

import io.github.amusing_glitch.tuple.domain.definition.field.NumberedFieldDefinition;

public record BasicNumberedFieldDefinition(
        Object node
) implements NumberedFieldDefinition, BasicFieldDefinition {
}
