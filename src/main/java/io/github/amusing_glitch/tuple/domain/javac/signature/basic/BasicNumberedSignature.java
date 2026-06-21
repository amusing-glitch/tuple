package io.github.amusing_glitch.tuple.domain.javac.signature.basic;

import io.github.amusing_glitch.tuple.domain.javac.signature.MethodNomenclature;
import io.github.amusing_glitch.tuple.domain.javac.signature.NumberedSignature;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.basic.BasicArgument;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.basic.BasicSimpleArgument;

import java.util.List;

public record BasicNumberedSignature(
        MethodNomenclature methodNomenclature,
        List<BasicSimpleArgument> simpleArguments,
        Object node
) implements NumberedSignature<BasicArgument>, BasicSignature {
    @Override
    public List<BasicArgument> arguments() {
        return simpleArguments().stream().map(BasicArgument::toBasic).toList();
    }
}
