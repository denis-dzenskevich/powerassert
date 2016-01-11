package my.powerassert;

import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import my.powerassert.javac.JavacProcessorImpl;

import javax.annotation.processing.ProcessingEnvironment;

public class EnvironmentProvider8 implements EnvironmentProvider {

    @Override
    public ProcessorIntf createProcessorIntf() {
        return new JavacProcessorImpl();
    }

    @Override
    public CompilerFacade createCompilerFacade() {
        return new CompilerFacade8();
    }

    @Override
    public TreeFactory createTreeFactory(ProcessingEnvironment processingEnvironment) {
        return new TreeFactory8((JavacProcessingEnvironment) processingEnvironment);
    }

    @Override
    public ExpressionMorpher createExpressionMorpher(JCTree.JCCompilationUnit compilationUnit) {
        return new ExpressionMorpher7(compilationUnit, createCompilerFacade());
    }
}
