package io.github.amusing_glitch.tuple.domain.javac.signature.argument.basic;

import io.github.amusing_glitch.tuple.domain.javac.signature.argument.TypeRefArgument;

public record BasicTypeRefArgument(
        String name,
        Object node
) implements TypeRefArgument, BasicArgument {}
