package io.github.amusing_glitch.tuple.domain.definition.basic;

import io.github.amusing_glitch.tuple.domain.definition.NamedTupleDefinition;
import io.github.amusing_glitch.tuple.domain.definition.field.basic.BasicNamedFieldDefinition;

import java.util.List;

public record BasicNamedTupleDefinition(
        String packageName,
        String name,
        List<BasicNamedFieldDefinition> fieldDefinitions,
        Object node
) implements NamedTupleDefinition<BasicNamedFieldDefinition>, BasicTupleDefinition<BasicNamedFieldDefinition> {}