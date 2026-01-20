package com.aparigraha.tuple;

import com.sun.source.util.Trees;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.util.Set;


abstract public class OncePerLifecycleProcessor extends AbstractProcessor {
    private boolean executed = false;
    private Trees trees;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        trees = Trees.instance(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (!executed) {
            executed = true;
            return processFirstRound(annotations, roundEnv);
        }
        return false;
    }

    abstract protected boolean processFirstRound(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv);

    protected Trees getTrees() {
        return trees;
    }
}
