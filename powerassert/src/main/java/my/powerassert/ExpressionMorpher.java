package my.powerassert;

import com.sun.source.tree.*;
import com.sun.source.util.TreePath;
import com.sun.source.util.TreePathScanner;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.EndPosTable;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.*;

import java.util.ArrayList;
import java.util.List;

class ExpressionMorpher {

    private final EndPosTable endPositions;
    private final JCCompilationUnit compilationUnit;

    ExpressionMorpher(EndPosTable endPositions, JCCompilationUnit compilationUnit) {
        this.endPositions = endPositions;
        this.compilationUnit = compilationUnit;
    }

    List<ExpressionPart> splitExpression(Tree expression) {
        final List<ExpressionPart> parts = new ArrayList<ExpressionPart>();
        new TreePathScanner<Object, Integer>() {
            @Override
            public Object visitConditionalExpression(ConditionalExpressionTree tree, Integer level) {
                return add(tree, preferredPos(tree), level, super.visitConditionalExpression(tree, level));
            }
            @Override
            public Object visitMethodInvocation(MethodInvocationTree tree, Integer level) {
                MemberSelectTree memberSelect = (MemberSelectTree) tree.getMethodSelect();
                return add(tree, endPos(memberSelect) - memberSelect.getIdentifier().length(), level, super.visitMethodInvocation(tree, level + 1));
            }
            @Override
            public Object visitNewClass(NewClassTree tree, Integer level) {
                return add(tree, preferredPos(tree), level, super.visitNewClass(tree, level + 1));
            }
            @Override
            public Object visitNewArray(NewArrayTree tree, Integer level) {
                return add(tree, preferredPos(tree), level, super.visitNewArray(tree, level + 1));
            }
            @Override
            public Object visitLambdaExpression(LambdaExpressionTree tree, Integer level) {
                return super.visitLambdaExpression(tree, level);
            }
            @Override
            public Object visitParenthesized(ParenthesizedTree tree, Integer level) {
                return super.visitParenthesized(tree, level); // ???
            }
            @Override
            public Object visitAssignment(AssignmentTree tree, Integer level) {
                return super.visitAssignment(tree, level); // ???
            }
            @Override
            public Object visitCompoundAssignment(CompoundAssignmentTree tree, Integer level) {
                return super.visitCompoundAssignment(tree, level); // ???
            }
            @Override
            public Object visitUnary(UnaryTree tree, Integer level) {
                return super.visitUnary(tree, level);
            }
            @Override
            public Object visitBinary(BinaryTree tree, Integer level) {
                return add(tree, preferredPos(tree), level, super.visitBinary(tree, level + 1));
            }
            @Override
            public Object visitInstanceOf(InstanceOfTree tree, Integer level) {
                return super.visitInstanceOf(tree, level);
            }
            @Override
            public Object visitArrayAccess(ArrayAccessTree tree, Integer level) {
                return super.visitArrayAccess(tree, level);
            }
            @Override
            public Object visitMemberSelect(MemberSelectTree tree, Integer level) {
                return isSymbolShown(((JCFieldAccess) tree).sym)
                        ? add(tree, endPos(tree) - tree.getIdentifier().length(), level, super.visitMemberSelect(tree, level + 1))
                        : super.visitMemberSelect(tree, level);
            }
            // TODO since 1.7
//            @Override
//            public Object visitMemberReference(MemberReferenceTree tree, Integer level) {
//                return add(tree, preferredPos(tree), level, super.visitMemberReference(tree, level.next()));
//            }
            @Override
            public Object visitIdentifier(IdentifierTree tree, Integer level) {
                return isSymbolShown(((JCIdent) tree).sym)
                        ? add(tree, preferredPos(tree), level, super.visitIdentifier(tree, level + 1))
                        : super.visitIdentifier(tree, level);
            }
            @Override
            public Object visitOther(Tree tree, Integer level) {
                return super.visitOther(tree, level); // ???
            }
            private Object add(Tree tree, int position, Integer level, Object result) {
                parts.add(new ExpressionPart((JCExpression) tree, level, position));
                return result;
            }
        }.scan(TreePath.getPath(compilationUnit, expression), 0);
        return parts;
    }

    private boolean isSymbolShown(Symbol symbol) {
        switch (symbol.getKind()) {
            case FIELD:
            case PARAMETER:
            case LOCAL_VARIABLE:
            case EXCEPTION_PARAMETER:
            case RESOURCE_VARIABLE: return true; // TODO RESOURCE_VARIABLE since 1.7
            default: return false;
        }
    }

    private int preferredPos(Tree tree) {
        return ((JCTree) tree).getPreferredPosition();
    }

    private int endPos(Tree tree) {
        return ((JCTree) tree).getEndPosition(endPositions);
    }

    static class ExpressionPart {

        final JCExpression expression;
        final int level;
        final int position;

        ExpressionPart(JCExpression expression, int level, int position) {
            this.expression = expression;
            this.level = level;
            this.position = position;
        }
    }
}
