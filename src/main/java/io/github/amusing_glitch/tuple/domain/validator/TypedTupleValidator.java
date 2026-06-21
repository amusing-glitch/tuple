package io.github.amusing_glitch.tuple.domain.validator;

import io.github.amusing_glitch.tuple.domain.type.TypeInfo;
import io.github.amusing_glitch.tuple.domain.definition.TupleDefinition;

public interface TypedTupleValidator<T extends TupleDefinition & TypeInfo>
        extends TupleValidator<T> {}
