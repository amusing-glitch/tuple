package io.github.amusing_glitch.tuple.domain.javac.signature.argument;

import io.github.amusing_glitch.tuple.domain.javac.signature.Type;

import java.util.Optional;

public record SimpleArgument(Optional<Type> type) implements Argument {
}
