package io.github.amusing_glitch.tuple.domain.javac.signature.argument.basic;

import io.github.amusing_glitch.tuple.domain.javac.signature.argument.LambdaArgument;

public record BasicLambdaArgument(
        String name,
        Object node
) implements LambdaArgument, BasicArgument {}
