package io.github.amusing_glitch.tuple.domain.javac.signature.typed;

import io.github.amusing_glitch.tuple.domain.javac.signature.MethodNomenclature;
import io.github.amusing_glitch.tuple.domain.javac.signature.NamedSignature;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.typed.TypedArgument;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.typed.TypedLambdaArgument;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.typed.TypedTypeRefArgument;

import java.util.List;
import java.util.stream.Stream;

public record TypedNamedSignature(
        MethodNomenclature methodNomenclature,
        TypedTypeRefArgument typeRefArgument,
        List<TypedLambdaArgument> lambdaArguments,
        Object node
) implements NamedSignature<TypedArgument>, TypedSignature {
    @Override
    public List<TypedArgument> arguments() {
        return Stream.concat(
                Stream.of(typeRefArgument().toTyped()),
                lambdaArguments().stream().map(TypedArgument::toTyped)
        ).toList();
    }
}
