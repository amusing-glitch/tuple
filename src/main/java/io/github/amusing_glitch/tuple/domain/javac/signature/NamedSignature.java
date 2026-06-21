package io.github.amusing_glitch.tuple.domain.javac.signature;

import io.github.amusing_glitch.tuple.domain.javac.signature.argument.Argument;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.LambdaArgument;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.TypeRefArgument;

import java.util.List;

public interface NamedSignature<T extends Argument> extends Signature<T> {
    TypeRefArgument typeRefArgument();

    List<? extends LambdaArgument> lambdaArguments();
}
