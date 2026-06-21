package io.github.amusing_glitch.tuple.domain.javac.signature;

import io.github.amusing_glitch.tuple.domain.javac.signature.argument.typed.TypedArgument;

import java.util.List;

public record PostCompileSignature(
        MethodNomenclature methodNomenclature,
        List<TypedArgument> arguments,
        Object node
) implements Signature<TypedArgument> {}
