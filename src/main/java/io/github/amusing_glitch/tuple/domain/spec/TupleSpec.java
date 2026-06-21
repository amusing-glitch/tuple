package io.github.amusing_glitch.tuple.domain.spec;

import io.github.amusing_glitch.tuple.domain.definition.TupleDefinition;
import io.github.amusing_glitch.tuple.domain.javac.signature.Signature;
import io.github.amusing_glitch.tuple.domain.javac.signature.MethodNomenclature;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.Argument;

import java.util.List;

public abstract class TupleSpec<T extends TupleDefinition> {
    public final boolean hasMatchingSignature(Signature<?> signature) {
        return
                signature.methodNomenclature().equals(targetMethodNomenclature()) &&
                hasMatchingArguments(signature.arguments());
    }

    protected abstract MethodNomenclature targetMethodNomenclature();

    protected abstract boolean hasMatchingArguments(List<? extends Argument> arguments);

    public abstract T process(Signature<?> signature);
}
