package io.github.amusing_glitch.tuple.domain.type;

import java.util.Optional;

public record Type(
        String value,
        Optional<Type> innerType
) {}
