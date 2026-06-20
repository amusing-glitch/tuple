package io.github.amusing_glitch.tuple.domain.validator;

public record ValidationError(
    Object node,
    String errorMessage
) {}
