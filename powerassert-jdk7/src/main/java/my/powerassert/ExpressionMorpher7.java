package my.powerassert;

import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree;

class ExpressionMorpher7 extends ExpressionMorpher {

    ExpressionMorpher7(JCTree.JCCompilationUnit compilationUnit, CompilerFacade compilerFacade) {
        super(compilationUnit, compilerFacade);
    }

    @Override
    protected boolean isSymbolShown(Symbol symbol) {
        switch (symbol.getKind()) {
            case RESOURCE_VARIABLE: return true;
            default: return super.isSymbolShown(symbol);
        }
    }
}
