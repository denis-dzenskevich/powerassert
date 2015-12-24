package my.powerassert;

import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Names;
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
        JavacTrees trees = (JavacTrees) Trees.instance(processingEnv);
        final Factory factory = TreeMaker.instance(processingEnvironment.getContext());
        final Names names = Names.instance(processingEnvironment.getContext());
        for (Element element : roundEnv.getRootElements()) {
            TreePath path = trees.getPath(element);
            JCCompilationUnit compilationUnit = (JCCompilationUnit) path.getCompilationUnit();
            ExpressionMorpher expressionMorpher = new ExpressionMorpher(compilationUnit.endPositions, compilationUnit);
            TreeFactory treeFactory = new TreeFactory(factory, names);
            ClassMorpher classMorpher = new ClassMorpher(element, processingEnvironment, compilationUnit.endPositions, treeFactory, path, expressionMorpher);
            Replacements replacements = new Replacements();
            trees.getElement(path);
            classMorpher.run(replacements);
            replacements.execute();
        }
        return true;
    }
}
