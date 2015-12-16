package my.powerassert;

import com.sun.source.tree.*;
import com.sun.source.util.*;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.code.Types;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;
import my.powerassert.javac.Replacements;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.lang.reflect.Array;
import java.util.*;

@SupportedAnnotationTypes("my.powerassert.Assert")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class AssertProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        JavacProcessingEnvironment javacProcessingEnv = (JavacProcessingEnvironment) processingEnv;
        Trees trees = Trees.instance(processingEnv);
        Types.instance(javacProcessingEnv.getContext());
        final Factory factory = TreeMaker.instance(javacProcessingEnv.getContext());
        final Names names = Names.instance(javacProcessingEnv.getContext());
        final Replacements replacements = new Replacements();
        for (TypeElement annotation : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                ((MethodTree) ((ClassTree) trees.getTree(element)).getMembers().get(1)).getBody();
                new TreePathScanner<Object, Object>() {
                    @Override
                    public Object visitAssert(AssertTree assertTree, Object o) {
                        System.out.println(assertTree);
                        Name variable = names.fromString("_powerassert");
                        JCExpression powerAssertClass = fullyQualifiedName(factory, names, "my", "powerassert", "PowerAssert");
                        JCExpression instantiation = factory.NewClass(null /* encl */
                                , null /* typeargs */
                                , powerAssertClass /* clazz */
                                , List.of((JCExpression) assertTree.getDetail(), factory.Literal(TypeTag.CLASS, assertTree.getCondition().toString())) /* args */
                                , null /* def */);
                        JCStatement declaration = factory.VarDef(factory.Modifiers(0, List.<JCAnnotation>nil()) /* mods */
                                , variable /* name */
                                , powerAssertClass /* vartype */
                                , instantiation /* init */);
                        ArrayList<JCStatement> statements = new ArrayList<JCStatement>();
                        statements.add(declaration);
                        for (ExpressionPart part : splitExpression(assertTree.getCondition())) {
                            JCMethodInvocation invocation = factory.Apply(List.<JCExpression>nil() /* typeargs */
                                    , factory.Select(factory.Ident(variable), names.fromString("part")) /* meth */
                                    , List.of(factory.Literal(TypeTag.INT, part.level) /* args: level */
                                            , factory.Literal(TypeTag.INT, part.position/*minus base*/) /* args: position */
                                            , (JCExpression) part.expression /* args: value */
                                            ) /* args */);
                            JCVariableDecl catchVariable = factory.VarDef(factory.Modifiers(0, List.<JCAnnotation>nil()) /* mods */
                                    , names.fromString("_powerassert_catch") /* name */
                                    , fullyQualifiedName(factory, names, "java", "lang", "Throwable") /* vartype */
                                    , null /* init */);
                            JCCatch catchBlock = factory.Catch(catchVariable /* param */
                                    , factory.Block(0, List.<JCStatement>nil()));
                            statements.add(factory.Try(factory.Block(0, List.<JCStatement>of(factory.Exec(invocation))) /* body */
                                    , List.of(catchBlock) /* catchers */
                                    , null /* finalizer */));
                        }
                        JCMethodInvocation buildInvocation = factory.Apply(List.<JCExpression>nil() /* typeargs */
                                , factory.Select(factory.Ident(variable), names.fromString("build")) /* meth */
                                , List.<JCExpression>nil() /* args */);
                        JCThrow throwStatement = factory.Throw(factory.NewClass(null /* encl */
                                , null /* typeargs */
                                , fullyQualifiedName(factory, names, "java", "lang", "AssertionError") /* clazz */
                                , List.<JCExpression>of(buildInvocation) /* args */
                                , null /* def */));
                        statements.add(throwStatement);
                        Tree substituation = factory.If(factory.Parens((JCExpression) assertTree.getCondition()), factory.Block(0, List.<JCStatement>nil()), factory.Block(0, toJavacList(JCStatement.class, statements)));
                        replacements.add(getCurrentPath().getParentPath().getLeaf(), assertTree, substituation);
                        return super.visitAssert(assertTree, o);
                    }
                }.scan(trees.getPath(element), null);
                int count = replacements.execute();
                processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, count + " replacements done");
/*                for (StatementTree statement : method.getBody().getStatements()) {
                    if (statement instanceof AssertTree) {
                        AssertTree assertTree = (AssertTree) statement;
                        System.out.println(assertTree);
//                        int start = ((JCTree) assertTree).pos;
//                        walkExpression(0, start, assertTree.getCondition());
//                        JCTree.JCAssert jcAssert = (JCTree.JCAssert) assertTree;
//                        jcAssert.cond = newInstance(constructor(JCTree.JCLiteral.class, TypeTag.class, Object.class), TypeTag.BOOLEAN, 0);
                    }
                }*/
            }
        }
        return false;
    }

    private JCExpression fullyQualifiedName(Factory factory, Names names, String firstPart, String... remainingParts) {
        JCExpression value = factory.Ident(names.fromString(firstPart));
        for (String remainingPart : remainingParts) {
            value = factory.Select(value, names.fromString(remainingPart));
        }
        return value;
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> toJavacList(Class<T> clazz, ArrayList<T> list) {
        switch (list.size()) {
            case 0: return List.nil();
            case 1: return List.of(list.get(0));
            case 2: return List.of(list.get(0), list.get(1));
            case 3: return List.of(list.get(0), list.get(1), list.get(2));
            default: {
                T[] array = list.toArray((T[]) Array.newInstance(clazz, list.size()));
                T[] remainder = (T[]) Array.newInstance(clazz, list.size() - 3);
                System.arraycopy(array, 3, remainder, 0, remainder.length);
                return List.of(array[0], array[1], array[2], remainder);
            }
        }
    }

    private ArrayList<ExpressionPart> splitExpression(Tree expression) {
        final ArrayList<ExpressionPart> parts = new ArrayList<ExpressionPart>();
        expression.accept(new TreeScanner<Object, Integer>() {
            @Override
            public Object visitConditionalExpression(ConditionalExpressionTree conditionalExpressionTree, Integer level) {
                return super.visitConditionalExpression(conditionalExpressionTree, level);
            }
            @Override
            public Object visitExpressionStatement(ExpressionStatementTree expressionStatementTree, Integer level) {
                return super.visitExpressionStatement(expressionStatementTree, level); // ???
            }
            @Override
            public Object visitMethodInvocation(MethodInvocationTree methodInvocationTree, Integer level) {
                return super.visitMethodInvocation(methodInvocationTree, level);
            }
            @Override
            public Object visitNewClass(NewClassTree newClassTree, Integer level) {
                return super.visitNewClass(newClassTree, level);
            }
            @Override
            public Object visitNewArray(NewArrayTree newArrayTree, Integer level) {
                return super.visitNewArray(newArrayTree, level);
            }
            @Override
            public Object visitLambdaExpression(LambdaExpressionTree lambdaExpressionTree, Integer level) {
                return super.visitLambdaExpression(lambdaExpressionTree, level);
            }
            @Override
            public Object visitParenthesized(ParenthesizedTree parenthesizedTree, Integer level) {
                return super.visitParenthesized(parenthesizedTree, level); // ???
            }
            @Override
            public Object visitAssignment(AssignmentTree assignmentTree, Integer level) {
                return super.visitAssignment(assignmentTree, level);
            }
            @Override
            public Object visitCompoundAssignment(CompoundAssignmentTree compoundAssignmentTree, Integer level) {
                return super.visitCompoundAssignment(compoundAssignmentTree, level); // ???
            }
            @Override
            public Object visitUnary(UnaryTree unaryTree, Integer level) {
                return super.visitUnary(unaryTree, level);
            }
            @Override
            public Object visitBinary(BinaryTree binaryTree, Integer level) {
                parts.add(new ExpressionPart(binaryTree, level, ((JCExpression) binaryTree).getStartPosition()));
                return super.visitBinary(binaryTree, level);
            }
            @Override
            public Object visitInstanceOf(InstanceOfTree instanceOfTree, Integer level) {
                return super.visitInstanceOf(instanceOfTree, level);
            }
            @Override
            public Object visitArrayAccess(ArrayAccessTree arrayAccessTree, Integer level) {
                return super.visitArrayAccess(arrayAccessTree, level);
            }
            @Override
            public Object visitMemberSelect(MemberSelectTree memberSelectTree, Integer level) {
                return super.visitMemberSelect(memberSelectTree, level); // ???
            }
            @Override
            public Object visitMemberReference(MemberReferenceTree memberReferenceTree, Integer level) {
                return super.visitMemberReference(memberReferenceTree, level); // ???
            }
            @Override
            public Object visitIdentifier(IdentifierTree identifierTree, Integer level) {
                return super.visitIdentifier(identifierTree, level); // ???
            }
            @Override
            public Object visitOther(Tree tree, Integer level) {
                return super.visitOther(tree, level); // ???
            }
        }, 0);
        return parts;
    }

    private static class ExpressionPart {

        private final Tree expression;
        private final int level;
        private final int position;

        public ExpressionPart(Tree expression, int level, int position) {
            this.expression = expression;
            this.level = level;
            this.position = position;
        }
    }
/*
    private void walkExpression(int level, int start, ExpressionTree expression) {
        JCTree tree = (JCTree) expression;
        System.out.printf("level: %2d, %60s: %s%s\n", level, expression.getClass().getName(), spaces(tree.getStartPosition() - start), expression.toString());
        level++;
        if (expression instanceof BinaryTree) {
            BinaryTree bt = (BinaryTree) expression;
            walkExpression(level, start, bt.getLeftOperand());
            walkExpression(level, start, bt.getRightOperand());
        } else if (expression instanceof MethodInvocationTree) {
            MethodInvocationTree mit = (MethodInvocationTree) expression;
            walkExpression(level, start, mit.getMethodSelect());
            for (ExpressionTree argument : mit.getArguments()) {
                walkExpression(level, start, argument);
            }
        } else if (expression instanceof MemberSelectTree) {
            MemberSelectTree mst = (MemberSelectTree) expression;
            walkExpression(level, start, mst.getExpression());
        }
    }
*/
}
