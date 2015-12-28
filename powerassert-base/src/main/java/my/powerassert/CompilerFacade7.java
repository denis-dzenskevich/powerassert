package my.powerassert;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.Tree;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.*;

public class CompilerFacade7 implements CompilerFacade {

    @Override
    public int getEndPosition(CompilationUnitTree compilationUnit, Tree tree) {
        JCCompilationUnit jcCompilationUnit = (JCCompilationUnit) compilationUnit;
        JCTree jcTree = (JCTree) tree;
        return jcTree.getEndPosition(jcCompilationUnit.endPositions);
    }
}
