package io.github.amusing_glitch.tuple.domain.definition.typed;

import io.github.amusing_glitch.tuple.domain.definition.NamedTupleDefinition;
import io.github.amusing_glitch.tuple.domain.definition.field.typed.TypedNamedFieldDefinition;

import java.util.List;

public record TypedNamedTupleDefinition(
        String packageName,
        String name,
        List<TypedNamedFieldDefinition> fieldDefinitions,
        Object node
) implements NamedTupleDefinition<TypedNamedFieldDefinition>, TypedTupleDefinition<TypedNamedFieldDefinition> {}