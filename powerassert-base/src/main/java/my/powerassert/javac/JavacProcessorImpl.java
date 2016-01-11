package my.powerassert.javac;

import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import my.powerassert.*;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

public class JavacProcessorImpl implements ProcessorIntf {

    @Override
    public void process(EnvironmentProvider environmentProvider, ProcessingEnvironment processingEnv, RoundEnvironment roundEnv) {
        JavacProcessingEnvironment processingEnvironment = (JavacProcessingEnvironment) processingEnv;
        JavacTrees trees = (JavacTrees) Trees.instance(processingEnv);
        TreeFactory treeFactory = environmentProvider.createTreeFactory(processingEnvironment);
        CompilerFacade compilerFacade = environmentProvider.createCompilerFacade();
        for (Element element : roundEnv.getRootElements()) {
            TreePath path = trees.getPath(element);
            JCTree.JCCompilationUnit compilationUnit = (JCTree.JCCompilationUnit) path.getCompilationUnit();
            ExpressionMorpher expressionMorpher = environmentProvider.createExpressionMorpher(compilationUnit);
            ClassMorpher classMorpher = new ClassMorpher(element, compilerFacade, processingEnvironment, compilationUnit, treeFactory, path, expressionMorpher);
            Replacements replacements = new Replacements();
            classMorpher.run(replacements);
            replacements.execute();
        }
    }
}
