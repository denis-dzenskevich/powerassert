package my.powerassert;

import com.sun.source.tree.*;
import com.sun.source.util.TreePath;
import com.sun.source.util.TreePathScanner;
import com.sun.tools.javac.tree.EndPosTable;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeInfo;

import java.util.ArrayList;
import java.util.List;

public class ExpressionMorpher {

    private final EndPosTable endPositions;
    private final JCTree.JCCompilationUnit compilationUnit;

    public ExpressionMorpher(EndPosTable endPositions, JCTree.JCCompilationUnit compilationUnit) {
        this.endPositions = endPositions;
        this.compilationUnit = compilationUnit;
    }

    public List<ExpressionPart> splitExpression(Tree expression) {
        final List<ExpressionPart> parts = new ArrayList<ExpressionPart>();
        new TreePathScanner<Object, Integer>() {
            @Override
            public Object visitConditionalExpression(ConditionalExpressionTree tree, Integer level) {
                return super.visitConditionalExpression(tree, level);
            }
            @Override
            public Object visitExpressionStatement(ExpressionStatementTree tree, Integer level) {
                return super.visitExpressionStatement(tree, level); // ???
            }
            @Override
            public Object visitMethodInvocation(MethodInvocationTree tree, Integer level) {
                MemberSelectTree memberSelect = (MemberSelectTree) tree.getMethodSelect();
                return add(tree, endPos(memberSelect) - memberSelect.getIdentifier().length(), level, super.visitMethodInvocation(tree, level + 1));
            }
            @Override
            public Object visitNewClass(NewClassTree tree, Integer level) {
                return super.visitNewClass(tree, level);
            }
            @Override
            public Object visitNewArray(NewArrayTree tree, Integer level) {
                return super.visitNewArray(tree, level);
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
                return super.visitAssignment(tree, level);
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
                return add(tree, ((JCTree) tree).getPreferredPosition(), level, super.visitBinary(tree, level + 1));
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
                if (getCurrentPath().getParentPath().getLeaf().getKind() == Tree.Kind.METHOD_INVOCATION) {
                    return super.visitMemberSelect(tree, level);
                } else {
                    return add(tree, endPos(tree) - tree.getIdentifier().length(), level, super.visitMemberSelect(tree, level + 1));
                }
            }
            // TODO since 1.7
//            @Override
//            public Object visitMemberReference(MemberReferenceTree tree, Integer level) {
//                return add(tree, ((JCTree) tree).getPreferredPosition(), level, super.visitMemberReference(tree, level.next()));
//            }
            @Override
            public Object visitIdentifier(IdentifierTree tree, Integer level) {
                Tree parent = getCurrentPath().getParentPath().getLeaf();
                if (parent.getKind() == Tree.Kind.MEMBER_SELECT && !TreeInfo.nonstaticSelect((JCTree) parent)) {
                    return super.visitIdentifier(tree, level);
                } else {
                    return add(tree, ((JCTree) tree).getPreferredPosition(), level, super.visitIdentifier(tree, level + 1));
                }
            }
            @Override
            public Object visitOther(Tree tree, Integer level) {
                return super.visitOther(tree, level); // ???
            }
            private Object add(Tree tree, int position, Integer level, Object result) {
                parts.add(new ExpressionPart(tree, level, position));
                return result;
            }
        }.scan(TreePath.getPath(compilationUnit, expression), 0);
        return parts;
    }

    private int endPos(Tree tree) {
        return ((JCTree) tree).getEndPosition(endPositions);
    }

    public static class ExpressionPart {

        public final Tree expression;
        public final int level;
        public final int position;

        public ExpressionPart(Tree expression, int level, int position) {
            this.expression = expression;
            this.level = level;
            this.position = position;
        }
    }
}
