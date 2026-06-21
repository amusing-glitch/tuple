package io.github.amusing_glitch.tuple.domain.definition;

public sealed interface NamedFieldDefinition permits BasicNamedFieldDefinition, TypedNamedFieldDefinition {
    String name();
    Object node();
}
