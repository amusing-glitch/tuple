package io.github.amusing_glitch.tuple.domain.javac.signature.basic;

import io.github.amusing_glitch.tuple.domain.javac.signature.MethodNomenclature;
import io.github.amusing_glitch.tuple.domain.javac.signature.NamedSignature;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.basic.BasicArgument;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.basic.BasicLambdaArgument;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.basic.BasicTypeRefArgument;

import java.util.List;
import java.util.stream.Stream;

public record BasicNamedSignature(
        MethodNomenclature methodNomenclature,
        BasicTypeRefArgument typeRefArgument,
        List<BasicLambdaArgument> lambdaArguments,
        Object node
) implements NamedSignature<BasicArgument>, BasicSignature {
    @Override
    public List<BasicArgument> arguments() {
        return Stream.concat(
                Stream.of(typeRefArgument().toBasic()),
                lambdaArguments().stream().map(BasicArgument::toBasic)
        ).toList();
    }
}
