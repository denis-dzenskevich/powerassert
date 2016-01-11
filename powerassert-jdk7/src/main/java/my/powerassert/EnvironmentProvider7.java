package my.powerassert;

import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import my.powerassert.javac.JavacProcessorImpl;

import javax.annotation.processing.ProcessingEnvironment;

public class EnvironmentProvider7 implements EnvironmentProvider {

    @Override
    public ProcessorIntf createProcessorIntf() {
        return new JavacProcessorImpl();
    }

    @Override
    public CompilerFacade createCompilerFacade() {
        return new CompilerFacade6();
    }

    @Override
    public TreeFactory createTreeFactory(ProcessingEnvironment processingEnvironment) {
        return new TreeFactory((JavacProcessingEnvironment) processingEnvironment);
    }

    @Override
    public ExpressionMorpher createExpressionMorpher(JCTree.JCCompilationUnit compilationUnit) {
        return new ExpressionMorpher7(compilationUnit, createCompilerFacade());
    }
}
