package io.github.amusing_glitch.tuple.domain.definition.typed;

import io.github.amusing_glitch.tuple.domain.definition.TupleDefinition;
import io.github.amusing_glitch.tuple.domain.definition.field.typed.TypedFieldDefinition;

public interface TypedTupleDefinition<T extends TypedFieldDefinition> extends TupleDefinition<T> {
}
