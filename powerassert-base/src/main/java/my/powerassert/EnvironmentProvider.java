package my.powerassert;

import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;

import javax.annotation.processing.ProcessingEnvironment;

public interface EnvironmentProvider {

    ProcessorIntf createProcessorIntf();

    CompilerFacade createCompilerFacade();

    TreeFactory createTreeFactory(ProcessingEnvironment processingEnvironment);

    ExpressionMorpher createExpressionMorpher(JCTree.JCCompilationUnit compilationUnit);
}
