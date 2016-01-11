package my.powerassert;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.Tree;

public interface CompilerFacade {

    int getEndPosition(CompilationUnitTree compilationUnit, Tree tree);
}
