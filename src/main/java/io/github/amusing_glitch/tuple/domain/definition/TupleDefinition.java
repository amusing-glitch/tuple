package io.github.amusing_glitch.tuple.domain.definition;

import io.github.amusing_glitch.tuple.domain.definition.field.FieldDefinition;

import java.util.List;

public interface TupleDefinition<F extends FieldDefinition> {
    String packageName();

    String name();

    Object node();

    List<F> fieldDefinitions();
}
