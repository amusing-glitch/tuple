package io.github.amusing_glitch.tuple.domain.javac;

import io.github.amusing_glitch.tuple.domain.javac.signature.Signature;

import java.util.function.Consumer;

public abstract class TupleScanner {
    public abstract void scan(Consumer<Signature> signatureConsumer);
}
