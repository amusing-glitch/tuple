package io.github.amusing_glitch.tuple.domain.definition;

public record BasicNamedFieldDefinition(
        String name,
        Object node
) implements NamedFieldDefinition {}
