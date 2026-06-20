package io.github.amusing_glitch.tuple.domain.spec;

import io.github.amusing_glitch.tuple.domain.definition.NamedFieldDefinition;
import io.github.amusing_glitch.tuple.domain.definition.NamedTupleDefinition;
import io.github.amusing_glitch.tuple.domain.javac.signature.MethodNomenclature;
import io.github.amusing_glitch.tuple.domain.javac.signature.Signature;
import io.github.amusing_glitch.tuple.domain.javac.signature.Type;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.Argument;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.LambdaArgument;
import io.github.amusing_glitch.tuple.domain.javac.signature.argument.TypeRefArgument;
import io.github.amusing_glitch.tuple.dynamic.templates.JavaTemplate;

import java.util.List;


public class NamedTupleSpec extends TupleSpec<NamedTupleDefinition> {
    private static final MethodNomenclature targetMethodNomenclature = new MethodNomenclature(
            JavaTemplate.packageName,
            JavaTemplate.dynamicTupleClassName,
            JavaTemplate.namedTupleFactoryMethodName
    );

    @Override
    public boolean hasMatchingArguments(List<Argument> arguments) {
        return
                arguments.stream().findFirst().orElseThrow() instanceof TypeRefArgument &&
                arguments.stream().skip(1).allMatch(it -> it instanceof LambdaArgument);
    }

    @Override
    protected MethodNomenclature targetMethodNomenclature() {
        return targetMethodNomenclature;
    }

    @Override
    public NamedTupleDefinition process(Signature signature) {
        String tupleClassName = ((TypeRefArgument) signature.arguments().stream().findFirst().orElseThrow()).name();
        var fields = signature.arguments().stream()
                .skip(1)
                .map(it -> (LambdaArgument) it)
                .map(it ->
                        new NamedFieldDefinition(
                                it.name(),
                                it.type().map(Type::value)
                        )
                ).toList();

        return new NamedTupleDefinition(
                JavaTemplate.packageName,
                tupleClassName,
                fields,
                signature.node()
        );
    }
}
