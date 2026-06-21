package io.github.amusing_glitch.tuple.domain.spec;

import io.github.amusing_glitch.tuple.domain.definition.NamedTupleDefinition;
import io.github.amusing_glitch.tuple.domain.definition.field.NamedFieldDefinition;
import io.github.amusing_glitch.tuple.domain.javac.signature.NamedSignature;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.Argument;


public interface NamedTupleSpec<A extends Argument, F extends NamedFieldDefinition>
        extends TupleSpec<NamedSignature<A>, NamedTupleDefinition<F>> {
}
