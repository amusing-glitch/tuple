package io.github.amusing_glitch.tuple.domain.javac.signature.argument.typed;

import io.github.amusing_glitch.tuple.domain.type.TypeInfo;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.Argument;

public interface TypedArgument extends Argument, TypeInfo {
    default TypedArgument toTyped() {
        return this;
    }
}
