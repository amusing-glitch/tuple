package io.github.amusing_glitch.tuple.domain.javac.signature;

import io.github.amusing_glitch.tuple.domain.javac.signature.argument.Argument;

import java.util.List;

public interface Signature<T extends Argument> {
    MethodNomenclature methodNomenclature();
    List<T> arguments();
    Object node();
}
