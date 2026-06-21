package io.github.amusing_glitch.tuple.domain.javac;

import io.github.amusing_glitch.tuple.domain.javac.signature.Signature;

import java.util.function.Consumer;

public sealed interface TupleScanner<S extends Signature<?>> permits BasicTupleScanner, TypedTupleScanner {
    void scan(Consumer<S> signatureConsumer);
}
