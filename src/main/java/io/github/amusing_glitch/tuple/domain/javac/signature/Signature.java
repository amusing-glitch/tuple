package io.github.amusing_glitch.tuple.domain.javac.signature;

import io.github.amusing_glitch.tuple.domain.javac.signature.argument.Argument;

import java.util.List;

public record Signature(
        MethodNomenclature methodNomenclature,
        List<Argument> arguments,
        Object node
) {}
