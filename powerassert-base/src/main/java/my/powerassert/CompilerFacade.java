package my.powerassert;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.Tree;

interface CompilerFacade {

    int getEndPosition(CompilationUnitTree compilationUnit, Tree tree);
}
