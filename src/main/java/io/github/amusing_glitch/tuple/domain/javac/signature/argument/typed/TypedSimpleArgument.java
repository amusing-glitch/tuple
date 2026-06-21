package io.github.amusing_glitch.tuple.domain.javac.signature.argument.typed;

import io.github.amusing_glitch.tuple.domain.type.Type;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.SimpleArgument;

public record TypedSimpleArgument(
        Type type,
        Object node
) implements SimpleArgument, TypedArgument {}
