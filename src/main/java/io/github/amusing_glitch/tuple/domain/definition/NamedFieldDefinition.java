package io.github.amusing_glitch.tuple.domain.definition;

import java.util.Optional;

public record NamedFieldDefinition(
        String name,
        Optional<String> type,
        Object node
) {}
