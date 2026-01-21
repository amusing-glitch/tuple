package com.aparigraha.tuple.javac;


public record MatchingStaticMethod(
        String className,
        String methodName,
        int argumentCount
) {
}
