package my.powerassert;

import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;

public interface EnvironmentProvider {

    CompilerFacade createCompilerFacade();

    TreeFactory createTreeFactory(JavacProcessingEnvironment processingEnvironment);

    ExpressionMorpher createExpressionMorpher(JCTree.JCCompilationUnit compilationUnit);
}
