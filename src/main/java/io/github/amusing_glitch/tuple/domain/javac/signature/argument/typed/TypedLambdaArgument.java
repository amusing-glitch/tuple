package io.github.amusing_glitch.tuple.domain.javac.signature.argument.typed;

import io.github.amusing_glitch.tuple.domain.type.Type;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.LambdaArgument;

public record TypedLambdaArgument(
        String name,
        Type type,
        Object node
) implements LambdaArgument, TypedArgument {}
