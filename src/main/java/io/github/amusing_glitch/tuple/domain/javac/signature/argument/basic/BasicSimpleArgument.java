package io.github.amusing_glitch.tuple.domain.javac.signature.argument.basic;

import io.github.amusing_glitch.tuple.domain.javac.signature.argument.SimpleArgument;

public record BasicSimpleArgument(
        Object node
) implements SimpleArgument, BasicArgument {}
