package com.aparigraha.tuple;

import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


public class MethodScanner {
    public List<MethodInvocationTree> scan(
            Predicate<MethodInvocationTree> isTargetMethod,
            ProcessingEnvironment processingEnvironment,
            Element rootElement
    ) {
        var treePathScanner = new TreePathScanner<List<MethodInvocationTree>, Void>() {
            @Override
            public List<MethodInvocationTree> visitMethodInvocation(MethodInvocationTree node, Void p) {
                var result = super.visitMethodInvocation(node, p);
                if (isTargetMethod.test(node)) {
                    result.add(node);
                }
                return result;
            }

            @Override
            public List<MethodInvocationTree> reduce(List<MethodInvocationTree> r1, List<MethodInvocationTree> r2) {
                var list1 = r1 == null ? new ArrayList<MethodInvocationTree>() : r1;
                var list2 = r2 == null ? new ArrayList<MethodInvocationTree>() : r2;
                list1.addAll(list2);
                return list1;
            }
        };

        return treePathScanner.scan(
                Trees.instance(processingEnvironment).getPath(rootElement),
                null
        );
    }
}
