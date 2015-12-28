package my.powerassert;

import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;

public class EnvironmentProvider8 implements EnvironmentProvider {

    @Override
    public CompilerFacade createCompilerFacade() {
        return new CompilerFacade8();
    }

    @Override
    public TreeFactory createTreeFactory(JavacProcessingEnvironment processingEnvironment) {
        return new TreeFactory8(processingEnvironment);
    }

    @Override
    public ExpressionMorpher createExpressionMorpher(JCTree.JCCompilationUnit compilationUnit) {
        return new ExpressionMorpher8(compilationUnit, createCompilerFacade());
    }
}
