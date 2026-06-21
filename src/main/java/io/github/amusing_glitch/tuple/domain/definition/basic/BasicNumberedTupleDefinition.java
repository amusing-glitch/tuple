package io.github.amusing_glitch.tuple.domain.definition.basic;

import io.github.amusing_glitch.tuple.domain.definition.NumberedTupleDefinition;
import io.github.amusing_glitch.tuple.domain.definition.field.basic.BasicNumberedFieldDefinition;

import java.util.List;

public record BasicNumberedTupleDefinition(
        String packageName,
        String name,
        List<BasicNumberedFieldDefinition> fieldDefinitions,
        Object node
) implements NumberedTupleDefinition<BasicNumberedFieldDefinition>, BasicTupleDefinition<BasicNumberedFieldDefinition> {}