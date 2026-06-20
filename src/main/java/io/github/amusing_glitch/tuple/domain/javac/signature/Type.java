package io.github.amusing_glitch.tuple.domain.javac.signature;

import java.util.Optional;

public record Type(
        String value,
        Optional<Type> innerType
) {}
