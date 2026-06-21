package io.github.amusing_glitch.tuple.domain.spec;

import io.github.amusing_glitch.tuple.domain.type.TypeInfo;
import io.github.amusing_glitch.tuple.domain.definition.TupleDefinition;

public abstract class TypedTupleSpec<T extends TupleDefinition & TypeInfo> extends TupleSpec<T> {}
