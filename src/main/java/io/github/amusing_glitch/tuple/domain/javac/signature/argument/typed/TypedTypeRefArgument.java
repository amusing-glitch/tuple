package io.github.amusing_glitch.tuple.domain.javac.signature.argument.typed;

import io.github.amusing_glitch.tuple.domain.type.Type;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.TypeRefArgument;

public record TypedTypeRefArgument(
        String name,
        Type type,
        Object node
) implements TypeRefArgument, TypedArgument {}
