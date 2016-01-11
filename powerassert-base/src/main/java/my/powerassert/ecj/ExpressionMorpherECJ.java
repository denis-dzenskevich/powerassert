package my.powerassert.ecj;

import my.powerassert.CompilerFacade;
import org.eclipse.jdt.internal.compiler.apt.model.ElementImpl;
import org.eclipse.jdt.internal.compiler.apt.model.ElementsImpl;
import org.eclipse.jdt.internal.compiler.ast.Expression;

import javax.lang.model.util.Elements;
import java.util.ArrayList;
import java.util.List;

public class ExpressionMorpherECJ {

    protected final ElementImpl compilationUnit;
    protected final CompilerFacade compilerFacade;

    private List<ExpressionPart> parts = new ArrayList<ExpressionPart>();

    ExpressionMorpherECJ(ElementImpl compilationUnit, CompilerFacade compilerFacade) {
        this.compilationUnit = compilationUnit;
        this.compilerFacade = compilerFacade;
    }

    List<ExpressionPart> splitExpression(Expression expression) {
        parts = new ArrayList<ExpressionPart>();
//        scan(TreePath.getPath(compilationUnit, expression), 0);
        return parts;
    }

//    protected boolean isSymbolShown(Symbol symbol) {
//        switch (symbol.getKind()) {
//            case PARAMETER:
//            case LOCAL_VARIABLE:
//            case EXCEPTION_PARAMETER: return true;
//            case FIELD:
//                return !symbol.name.contentEquals("class"); // all but 'class' special static field on classes
//            default:
//                return false;
//        }
//    }

//    private int preferredPos(Tree tree) {
//        return ((JCTree) tree).getPreferredPosition();
//    }

//    private int endPos(Tree tree) {
//        return compilerFacade.getEndPosition(compilationUnit, tree);
//        throw new UnsupportedOperationException();
//    }

//    @Override
//    public Object visitConditionalExpression(ConditionalExpressionTree tree, Integer level) {
//        return add(tree, preferredPos(tree), level, super.visitConditionalExpression(tree, level));
//    }
//
//    @Override
//    public Object visitMethodInvocation(MethodInvocationTree tree, Integer level) {
//        MemberSelectTree memberSelect = (MemberSelectTree) tree.getMethodSelect();
//        return add(tree, endPos(memberSelect) - memberSelect.getIdentifier().length(), level, super.visitMethodInvocation(tree, level + 1));
//    }
//
//    @Override
//    public Object visitNewClass(NewClassTree tree, Integer level) {
//        return add(tree, preferredPos(tree), level, super.visitNewClass(tree, level + 1));
//    }
//
//    @Override
//    public Object visitNewArray(NewArrayTree tree, Integer level) {
//        return add(tree, preferredPos(tree), level, super.visitNewArray(tree, level + 1));
//    }
//
//    @Override
//    public Object visitUnary(UnaryTree tree, Integer level) {
//        return add(tree, preferredPos(tree), level, super.visitUnary(tree, level + 1));
//    }
//
//    @Override
//    public Object visitBinary(BinaryTree tree, Integer level) {
//        return add(tree, preferredPos(tree), level, super.visitBinary(tree, level + 1));
//    }
//
//    @Override
//    public Object visitInstanceOf(InstanceOfTree tree, Integer level) {
//        return add(tree, preferredPos(tree), level, super.visitInstanceOf(tree, level + 1));
//    }
//
//    @Override
//    public Object visitArrayAccess(ArrayAccessTree tree, Integer level) {
//        return add(tree, preferredPos(tree), level, super.visitArrayAccess(tree, level + 1));
//    }
//
//    @Override
//    public Object visitMemberSelect(MemberSelectTree tree, Integer level) {
//        return isSymbolShown(((JCFieldAccess) tree).sym)
//                ? add(tree, endPos(tree) - tree.getIdentifier().length(), level, super.visitMemberSelect(tree, level + 1))
//                : super.visitMemberSelect(tree, level);
//    }
//
//    @Override
//    public Object visitIdentifier(IdentifierTree tree, Integer level) {
//        return isSymbolShown(((JCIdent) tree).sym)
//                ? add(tree, preferredPos(tree), level, super.visitIdentifier(tree, level + 1))
//                : super.visitIdentifier(tree, level);
//    }
//
//    @Override
//    public Object visitOther(Tree tree, Integer level) {
//        return super.visitOther(tree, level); // ???
//    }
//
//    private Object add(Tree tree, int position, Integer level, Object result) {
//        parts.add(new ExpressionPart((JCExpression) tree, level, position));
//        return result;
//    }

    static class ExpressionPart {

        final Object expression;
        final int level;
        final int position;

        ExpressionPart(Object expression, int level, int position) {
            this.expression = expression;
            this.level = level;
            this.position = position;
        }
    }
}
