package my.powerassert;

import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree.*;
import my.powerassert.javac.Replacements;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

//@SupportedAnnotationTypes("my.powerassert.Assert")
@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class AssertProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        JavacProcessingEnvironment processingEnvironment = (JavacProcessingEnvironment) processingEnv;
        EnvironmentProvider environmentProvider = createEnvironmentProvider();
        JavacTrees trees = (JavacTrees) Trees.instance(processingEnv);
        TreeFactory treeFactory = environmentProvider.createTreeFactory(processingEnvironment);
        CompilerFacade compilerFacade = environmentProvider.createCompilerFacade();
        for (Element element : roundEnv.getRootElements()) {
            TreePath path = trees.getPath(element);
            JCCompilationUnit compilationUnit = (JCCompilationUnit) path.getCompilationUnit();
            ExpressionMorpher expressionMorpher = environmentProvider.createExpressionMorpher(compilationUnit);
            ClassMorpher classMorpher = new ClassMorpher(element, compilerFacade, processingEnvironment, compilationUnit, treeFactory, path, expressionMorpher);
            Replacements replacements = new Replacements();
            classMorpher.run(replacements);
            replacements.execute();
        }
        return true;
    }

    private EnvironmentProvider createEnvironmentProvider() {
        String clazz;
        switch (JavaVersion.current()) {
            case JDK7: clazz = "my.powerassert.EnvironmentProvider7"; break;
            case JDK8: clazz = "my.powerassert.EnvironmentProvider8"; break;
            default: throw new IllegalStateException(JavaVersion.current().name());
        }
        try {
            return (EnvironmentProvider) Class.forName(clazz).newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Cannot create EnvironmentProvider: " + e.getMessage(), e);
        }
    }
}
