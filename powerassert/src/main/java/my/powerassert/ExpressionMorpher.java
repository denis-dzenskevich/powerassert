package my.powerassert;

import com.sun.source.tree.*;
import com.sun.source.util.TreeScanner;
import com.sun.tools.javac.tree.EndPosTable;
import com.sun.tools.javac.tree.JCTree;

import java.util.ArrayList;
import java.util.List;

public class ExpressionMorpher {

    private final EndPosTable endPositions;

    public ExpressionMorpher(EndPosTable endPositions) {
        this.endPositions = endPositions;
    }

    public List<ExpressionPart> splitExpression(Tree expression) {
        final List<ExpressionPart> parts = new ArrayList<ExpressionPart>();
        expression.accept(new TreeScanner<Object, VisitContext>() {
            @Override
            public Object visitConditionalExpression(ConditionalExpressionTree tree, VisitContext context) {
                return super.visitConditionalExpression(tree, context);
            }
            @Override
            public Object visitExpressionStatement(ExpressionStatementTree tree, VisitContext context) {
                return super.visitExpressionStatement(tree, context); // ???
            }
            @Override
            public Object visitMethodInvocation(MethodInvocationTree tree, VisitContext context) {
                MemberSelectTree memberSelect = (MemberSelectTree) tree.getMethodSelect();
                return add(tree, endPos(memberSelect) - memberSelect.getIdentifier().length(), context, super.visitMethodInvocation(tree, context.skipNextMemberSelect()));
            }
            @Override
            public Object visitNewClass(NewClassTree tree, VisitContext context) {
                return super.visitNewClass(tree, context);
            }
            @Override
            public Object visitNewArray(NewArrayTree tree, VisitContext context) {
                return super.visitNewArray(tree, context);
            }
            @Override
            public Object visitLambdaExpression(LambdaExpressionTree tree, VisitContext context) {
                return super.visitLambdaExpression(tree, context);
            }
            @Override
            public Object visitParenthesized(ParenthesizedTree tree, VisitContext context) {
                return super.visitParenthesized(tree, context); // ???
            }
            @Override
            public Object visitAssignment(AssignmentTree tree, VisitContext context) {
                return super.visitAssignment(tree, context);
            }
            @Override
            public Object visitCompoundAssignment(CompoundAssignmentTree tree, VisitContext context) {
                return super.visitCompoundAssignment(tree, context); // ???
            }
            @Override
            public Object visitUnary(UnaryTree tree, VisitContext context) {
                return super.visitUnary(tree, context);
            }
            @Override
            public Object visitBinary(BinaryTree tree, VisitContext context) {
                return add(tree, ((JCTree) tree).getPreferredPosition(), context, super.visitBinary(tree, context.next()));
            }
            @Override
            public Object visitInstanceOf(InstanceOfTree tree, VisitContext context) {
                return super.visitInstanceOf(tree, context);
            }
            @Override
            public Object visitArrayAccess(ArrayAccessTree tree, VisitContext context) {
                return super.visitArrayAccess(tree, context);
            }
            @Override
            public Object visitMemberSelect(MemberSelectTree tree, VisitContext context) {
                if (context.skipMemberSelect) {
                    return super.visitMemberSelect(tree, context);
                } else {
                    return add(tree, endPos(tree) - tree.getIdentifier().length(), context, super.visitMemberSelect(tree, context.next()));
                }
            }
            @Override
            public Object visitMemberReference(MemberReferenceTree tree, VisitContext context) {
                return add(tree, ((JCTree) tree).getPreferredPosition(), context, super.visitMemberReference(tree, context.next()));
            }
            @Override
            public Object visitIdentifier(IdentifierTree tree, VisitContext context) {
                return add(tree, ((JCTree) tree).getPreferredPosition(), context, super.visitIdentifier(tree, context.next()));
            }
            @Override
            public Object visitOther(Tree tree, VisitContext context) {
                return super.visitOther(tree, context); // ???
            }
            private Object add(Tree tree, int position, VisitContext context, Object result) {
                parts.add(new ExpressionPart(tree, context.level, position));
                return result;
            }
        }, new VisitContext(0, false));
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

    private static class VisitContext {

        private final int level;
        private final boolean skipMemberSelect;

        public VisitContext(int level, boolean skipMemberSelect) {
            this.level = level;
            this.skipMemberSelect = skipMemberSelect;
        }

        private VisitContext next() {
            return new VisitContext(level + 1, false);
        }

        private VisitContext skipNextMemberSelect() {
            return new VisitContext(level + 1, true);
        }
    }
}
