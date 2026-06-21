package io.github.amusing_glitch.tuple.domain.spec;

import io.github.amusing_glitch.tuple.domain.definition.NumberedTupleDefinition;
import io.github.amusing_glitch.tuple.domain.javac.signature.MethodNomenclature;
import io.github.amusing_glitch.tuple.domain.javac.signature.Signature;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.Argument;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.SimpleArgument;
import io.github.amusing_glitch.tuple.dynamic.templates.JavaTemplate;

import java.util.List;

public class NumberedTupleSpec extends TupleSpec<NumberedTupleDefinition> {
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
    public NumberedTupleDefinition process(Signature signature) {
        return new NumberedTupleDefinition(
                JavaTemplate.packageName,
                JavaTemplate.className(signature.arguments().size()),
                signature.arguments().size(),
                signature.node()
        );
    }
}
