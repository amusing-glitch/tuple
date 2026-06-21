package io.github.amusing_glitch.tuple.domain.javac.signature.typed;

import io.github.amusing_glitch.tuple.domain.javac.signature.MethodNomenclature;
import io.github.amusing_glitch.tuple.domain.javac.signature.NumberedSignature;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.typed.TypedArgument;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.typed.TypedSimpleArgument;

import java.util.List;

public record TypedNumberedSignature(
        MethodNomenclature methodNomenclature,
        List<TypedSimpleArgument> simpleArguments,
        Object node

) implements NumberedSignature<TypedArgument>, TypedSignature {
    @Override
    public List<TypedArgument> arguments() {
        return simpleArguments.stream().map(TypedArgument::toTyped).toList();
    }
}
