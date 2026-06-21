package io.github.amusing_glitch.tuple.domain.javac.signature.argument;

public record TypeRefArgument(
        String name,
        Object node
) implements Argument {}
