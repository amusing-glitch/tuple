package io.github.amusing_glitch.tuple.domain.definition.field.typed;

import io.github.amusing_glitch.tuple.domain.definition.field.NumberedFieldDefinition;
import io.github.amusing_glitch.tuple.domain.type.Type;

public record TypedNumberedFieldDefinition(
        Type type,
        Object node
) implements NumberedFieldDefinition, TypedFieldDefinition {
}
