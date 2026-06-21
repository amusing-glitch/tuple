package io.github.amusing_glitch.tuple.domain.javac.signature;

import io.github.amusing_glitch.tuple.domain.javac.signature.argument.basic.BasicArgument;

import java.util.List;

public record PreCompileSignature(
        MethodNomenclature methodNomenclature,
        List<BasicArgument> arguments,
        Object node
) implements Signature<BasicArgument> {}
