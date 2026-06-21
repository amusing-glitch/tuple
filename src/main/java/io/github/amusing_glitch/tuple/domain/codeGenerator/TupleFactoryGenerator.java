package io.github.amusing_glitch.tuple.domain.codeGenerator;

import io.github.amusing_glitch.tuple.domain.definition.basic.BasicNamedTupleDefinition;
import io.github.amusing_glitch.tuple.domain.definition.basic.BasicNumberedTupleDefinition;

public interface TupleFactoryGenerator {
    String generate(BasicNamedTupleDefinition tupleDefinition);
    String generate(BasicNumberedTupleDefinition tupleDefinition);
}
