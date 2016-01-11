package my.powerassert.ecj;

import com.sun.tools.javac.tree.JCTree;
import my.powerassert.*;

import javax.annotation.processing.ProcessingEnvironment;

public class EnvironmentProviderECJ implements EnvironmentProvider {

    @Override
    public ProcessorIntf createProcessorIntf() {
        return new EcjProcessorImpl();
    }

    @Override
    public CompilerFacade createCompilerFacade() {
        return null;
    }

    @Override
    public TreeFactory createTreeFactory(ProcessingEnvironment processingEnvironment) {
        return null;
    }

    @Override
    public ExpressionMorpher createExpressionMorpher(JCTree.JCCompilationUnit compilationUnit) {
        return null;
    }
}
