package io.github.amusing_glitch.tuple.domain.spec;

import io.github.amusing_glitch.tuple.domain.definition.field.typed.TypedFieldDefinition;
import io.github.amusing_glitch.tuple.domain.javac.signature.Signature;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.typed.TypedArgument;
import io.github.amusing_glitch.tuple.domain.definition.TupleDefinition;

public interface TypedTupleSpec<S extends Signature<TypedArgument>, T extends TupleDefinition<TypedFieldDefinition>> extends TupleSpec<S, T> {
}
