package io.github.amusing_glitch.tuple.domain.validator;

import io.github.amusing_glitch.tuple.domain.definition.TupleDefinition;

import java.util.List;

public interface TupleValidator<T extends TupleDefinition> {
    List<ValidationError> validate(T tupleDefinition);

    List<ValidationError> validate(List<T> tupleDefinitions);
}
