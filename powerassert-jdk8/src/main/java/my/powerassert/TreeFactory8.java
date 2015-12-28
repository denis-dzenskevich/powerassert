package my.powerassert;

import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;

class TreeFactory8 extends TreeFactory {

    TreeFactory8(JavacProcessingEnvironment processingEnvironment) {
        super(processingEnvironment);
    }

    @Override
    JCTree.JCLiteral literal(String value) {
        return factory.Literal(TypeTag.CLASS, value);
    }

    @Override
    JCTree.JCLiteral literal(int value) {
        return factory.Literal(TypeTag.INT, value);
    }

    @Override
    JCTree.JCThrow throw_(JCTree.JCExpression thrown) {
        return factory.Throw(thrown);
    }
}
