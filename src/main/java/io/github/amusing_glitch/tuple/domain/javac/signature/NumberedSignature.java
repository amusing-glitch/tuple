package io.github.amusing_glitch.tuple.domain.javac.signature;

import io.github.amusing_glitch.tuple.domain.javac.signature.argument.Argument;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.SimpleArgument;

import java.util.List;

public interface NumberedSignature<T extends Argument> extends Signature<T> {
    List<? extends SimpleArgument> simpleArguments();
}
