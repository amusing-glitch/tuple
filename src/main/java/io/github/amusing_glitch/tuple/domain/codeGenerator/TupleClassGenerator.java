package io.github.amusing_glitch.tuple.domain.codeGenerator;

import io.github.amusing_glitch.tuple.domain.definition.NamedTupleDefinition;
import io.github.amusing_glitch.tuple.domain.definition.NumberedTupleDefinition;

public interface TupleClassGenerator {
    String generate(NamedTupleDefinition tupleDefinition);
    String generate(NumberedTupleDefinition tupleDefinition);
}
