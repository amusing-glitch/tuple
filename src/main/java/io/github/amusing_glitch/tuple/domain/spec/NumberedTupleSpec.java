package io.github.amusing_glitch.tuple.domain.spec;

import io.github.amusing_glitch.tuple.domain.definition.basic.BasicNumberedTupleDefinition;
import io.github.amusing_glitch.tuple.domain.javac.signature.MethodNomenclature;
import io.github.amusing_glitch.tuple.domain.javac.signature.Signature;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.Argument;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.SimpleArgument;
import io.github.amusing_glitch.tuple.dynamic.templates.JavaTemplate;

import java.util.List;

public class NumberedTupleSpec extends TupleSpec<BasicNumberedTupleDefinition> {
    private static final MethodNomenclature targetMethodNomenclature = new MethodNomenclature(
            JavaTemplate.packageName,
            JavaTemplate.dynamicTupleClassName,
            JavaTemplate.dynamicTupleFactoryMethodName
    );

    @Override
    protected MethodNomenclature targetMethodNomenclature() {
        return targetMethodNomenclature;
    }

    @Override
    protected boolean hasMatchingArguments(List<? extends Argument> arguments) {
        return arguments.stream().allMatch(it -> it instanceof SimpleArgument);
    }

    @Override
    public BasicNumberedTupleDefinition process(Signature signature) {
        return new BasicNumberedTupleDefinition(
                JavaTemplate.packageName,
                JavaTemplate.className(signature.arguments().size()),
                signature.arguments().size(),
                signature.node()
        );
    }
}
