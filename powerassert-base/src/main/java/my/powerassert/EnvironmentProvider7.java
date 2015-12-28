package my.powerassert;

import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;

public class EnvironmentProvider7 implements EnvironmentProvider {

    @Override
    public CompilerFacade createCompilerFacade() {
        return new CompilerFacade7();
    }

    @Override
    public TreeFactory createTreeFactory(JavacProcessingEnvironment processingEnvironment) {
        return new TreeFactory(processingEnvironment);
    }

    @Override
    public ExpressionMorpher createExpressionMorpher(JCTree.JCCompilationUnit compilationUnit) {
        return new ExpressionMorpher(compilationUnit, createCompilerFacade());
    }
}
