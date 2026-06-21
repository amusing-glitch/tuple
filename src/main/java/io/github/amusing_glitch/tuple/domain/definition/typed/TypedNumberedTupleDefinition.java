package io.github.amusing_glitch.tuple.domain.definition.typed;

import io.github.amusing_glitch.tuple.domain.definition.NumberedTupleDefinition;
import io.github.amusing_glitch.tuple.domain.definition.field.typed.TypedNumberedFieldDefinition;

import java.util.List;

public record TypedNumberedTupleDefinition(
        String packageName,
        String name,
        List<TypedNumberedFieldDefinition> fieldDefinitions,
        Object node
) implements NumberedTupleDefinition<TypedNumberedFieldDefinition>, TypedTupleDefinition<TypedNumberedFieldDefinition> {}