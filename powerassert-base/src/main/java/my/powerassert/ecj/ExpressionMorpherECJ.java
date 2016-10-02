package my.powerassert.ecj;

import my.powerassert.CompilerFacade;
import my.powerassert.ExpressionPart;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.apt.model.ElementImpl;
import org.eclipse.jdt.internal.compiler.apt.model.ElementsImpl;
import org.eclipse.jdt.internal.compiler.ast.*;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.CompilationUnitScope;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;

import javax.lang.model.util.Elements;
import java.util.ArrayList;
import java.util.List;

public class ExpressionMorpherECJ extends ASTVisitor {

    protected final ElementImpl compilationUnit;
    protected final CompilerFacade compilerFacade;

    private List<ExpressionPart<ASTNode>> parts;
    private int level;

    ExpressionMorpherECJ(ElementImpl compilationUnit, CompilerFacade compilerFacade) {
        this.compilationUnit = compilationUnit;
        this.compilerFacade = compilerFacade;
    }

    List<ExpressionPart<ASTNode>> splitExpression(Expression expression) {
        parts = new ArrayList<ExpressionPart<ASTNode>>();
        level = 0;
        expression.traverse(this, (BlockScope) null);
        return parts;
    }

//    @Override
//    public boolean visit(ThisReference thisReference, ClassScope scope) {
//        return super.visit(thisReference, scope);
//    }

    @Override
    public boolean visit(UnaryExpression unaryExpression, BlockScope scope) {
        parts.add(new ExpressionPart(unaryExpression, level++, unaryExpression.sourceStart()));
        super.visit(unaryExpression, scope);
        level--;
        return true;
    }

    @Override
    public boolean visit(UnionTypeReference unionTypeReference, BlockScope scope) {
        return super.visit(unionTypeReference, scope);
    }

    @Override
    public boolean visit(UnionTypeReference unionTypeReference, ClassScope scope) {
        return super.visit(unionTypeReference, scope);
    }

    @Override
    public boolean visit(WhileStatement whileStatement, BlockScope scope) {
        return super.visit(whileStatement, scope);
    }

    @Override
    public boolean visit(Wildcard wildcard, BlockScope scope) {
        return super.visit(wildcard, scope);
    }

    @Override
    public boolean visit(Wildcard wildcard, ClassScope scope) {
        return super.visit(wildcard, scope);
    }

    @Override
    public boolean visit(LambdaExpression lambdaExpression, BlockScope blockScope) {
        return super.visit(lambdaExpression, blockScope);
    }

    @Override
    public boolean visit(ReferenceExpression referenceExpression, BlockScope blockScope) {
        return super.visit(referenceExpression, blockScope);
    }

    @Override
    public boolean visit(IntersectionCastTypeReference intersectionCastTypeReference, ClassScope scope) {
        return super.visit(intersectionCastTypeReference, scope);
    }

    @Override
    public boolean visit(PrefixExpression prefixExpression, BlockScope scope) {
        return super.visit(prefixExpression, scope);
    }

    @Override
    public boolean visit(QualifiedAllocationExpression qualifiedAllocationExpression, BlockScope scope) {
        return super.visit(qualifiedAllocationExpression, scope);
    }

    @Override
    public boolean visit(QualifiedNameReference qualifiedNameReference, BlockScope scope) {
        return super.visit(qualifiedNameReference, scope);
    }

    @Override
    public boolean visit(QualifiedNameReference qualifiedNameReference, ClassScope scope) {
        return super.visit(qualifiedNameReference, scope);
    }

    @Override
    public boolean visit(QualifiedSuperReference qualifiedSuperReference, BlockScope scope) {
        return super.visit(qualifiedSuperReference, scope);
    }

    @Override
    public boolean visit(QualifiedSuperReference qualifiedSuperReference, ClassScope scope) {
        return super.visit(qualifiedSuperReference, scope);
    }

    @Override
    public boolean visit(QualifiedThisReference qualifiedThisReference, BlockScope scope) {
        return super.visit(qualifiedThisReference, scope);
    }

    @Override
    public boolean visit(QualifiedThisReference qualifiedThisReference, ClassScope scope) {
        return super.visit(qualifiedThisReference, scope);
    }

    @Override
    public boolean visit(QualifiedTypeReference qualifiedTypeReference, BlockScope scope) {
        return super.visit(qualifiedTypeReference, scope);
    }

    @Override
    public boolean visit(QualifiedTypeReference qualifiedTypeReference, ClassScope scope) {
        return super.visit(qualifiedTypeReference, scope);
    }

    @Override
    public boolean visit(ReturnStatement returnStatement, BlockScope scope) {
        return super.visit(returnStatement, scope);
    }

    @Override
    public boolean visit(SingleMemberAnnotation annotation, BlockScope scope) {
        return super.visit(annotation, scope);
    }

    @Override
    public boolean visit(SingleMemberAnnotation annotation, ClassScope scope) {
        return super.visit(annotation, scope);
    }

    @Override
    public boolean visit(SingleNameReference singleNameReference, BlockScope scope) {
        return super.visit(singleNameReference, scope);
    }

    @Override
    public boolean visit(SingleNameReference singleNameReference, ClassScope scope) {
        return super.visit(singleNameReference, scope);
    }

    @Override
    public boolean visit(SingleTypeReference singleTypeReference, BlockScope scope) {
        return super.visit(singleTypeReference, scope);
    }

    @Override
    public boolean visit(SingleTypeReference singleTypeReference, ClassScope scope) {
        return super.visit(singleTypeReference, scope);
    }

    @Override
    public boolean visit(StringLiteral stringLiteral, BlockScope scope) {
        return super.visit(stringLiteral, scope);
    }

    @Override
    public boolean visit(SuperReference superReference, BlockScope scope) {
        return super.visit(superReference, scope);
    }

    @Override
    public boolean visit(SwitchStatement switchStatement, BlockScope scope) {
        return super.visit(switchStatement, scope);
    }

    @Override
    public boolean visit(SynchronizedStatement synchronizedStatement, BlockScope scope) {
        return super.visit(synchronizedStatement, scope);
    }

    @Override
    public boolean visit(ThisReference thisReference, BlockScope scope) {
        return super.visit(thisReference, scope);
    }

    @Override
    public boolean visit(JavadocSingleNameReference argument, ClassScope scope) {
        return super.visit(argument, scope);
    }

    @Override
    public boolean visit(JavadocSingleTypeReference typeRef, BlockScope scope) {
        return super.visit(typeRef, scope);
    }

    @Override
    public boolean visit(JavadocSingleTypeReference typeRef, ClassScope scope) {
        return super.visit(typeRef, scope);
    }

    @Override
    public boolean visit(LabeledStatement labeledStatement, BlockScope scope) {
        return super.visit(labeledStatement, scope);
    }

    @Override
    public boolean visit(LocalDeclaration localDeclaration, BlockScope scope) {
        return super.visit(localDeclaration, scope);
    }

    @Override
    public boolean visit(LongLiteral longLiteral, BlockScope scope) {
        return super.visit(longLiteral, scope);
    }

    @Override
    public boolean visit(MarkerAnnotation annotation, BlockScope scope) {
        return super.visit(annotation, scope);
    }

    @Override
    public boolean visit(MarkerAnnotation annotation, ClassScope scope) {
        return super.visit(annotation, scope);
    }

    @Override
    public boolean visit(MemberValuePair pair, BlockScope scope) {
        return super.visit(pair, scope);
    }

    @Override
    public boolean visit(MemberValuePair pair, ClassScope scope) {
        return super.visit(pair, scope);
    }

    @Override
    public boolean visit(MessageSend messageSend, BlockScope scope) {
        parts.add(new ExpressionPart<ASTNode>(messageSend, level++, messageSend.sourceStart()));
        super.visit(messageSend, scope);
        level--;
        return true;
    }

    @Override
    public boolean visit(MethodDeclaration methodDeclaration, ClassScope scope) {
        return super.visit(methodDeclaration, scope);
    }

    @Override
    public boolean visit(StringLiteralConcatenation literal, BlockScope scope) {
        return super.visit(literal, scope);
    }

    @Override
    public boolean visit(NormalAnnotation annotation, BlockScope scope) {
        return super.visit(annotation, scope);
    }

    @Override
    public boolean visit(NormalAnnotation annotation, ClassScope scope) {
        return super.visit(annotation, scope);
    }

    @Override
    public boolean visit(NullLiteral nullLiteral, BlockScope scope) {
        return super.visit(nullLiteral, scope);
    }

    @Override
    public boolean visit(OR_OR_Expression or_or_Expression, BlockScope scope) {
        return super.visit(or_or_Expression, scope);
    }

    @Override
    public boolean visit(ParameterizedQualifiedTypeReference parameterizedQualifiedTypeReference, BlockScope scope) {
        return super.visit(parameterizedQualifiedTypeReference, scope);
    }

    @Override
    public boolean visit(ParameterizedQualifiedTypeReference parameterizedQualifiedTypeReference, ClassScope scope) {
        return super.visit(parameterizedQualifiedTypeReference, scope);
    }

    @Override
    public boolean visit(ParameterizedSingleTypeReference parameterizedSingleTypeReference, BlockScope scope) {
        return super.visit(parameterizedSingleTypeReference, scope);
    }

    @Override
    public boolean visit(ParameterizedSingleTypeReference parameterizedSingleTypeReference, ClassScope scope) {
        return super.visit(parameterizedSingleTypeReference, scope);
    }

    @Override
    public boolean visit(PostfixExpression postfixExpression, BlockScope scope) {
        return super.visit(postfixExpression, scope);
    }

    @Override
    public boolean visit(IntLiteral intLiteral, BlockScope scope) {
        return super.visit(intLiteral, scope);
    }

    @Override
    public boolean visit(Javadoc javadoc, BlockScope scope) {
        return super.visit(javadoc, scope);
    }

    @Override
    public boolean visit(Javadoc javadoc, ClassScope scope) {
        return super.visit(javadoc, scope);
    }

    @Override
    public boolean visit(JavadocAllocationExpression expression, BlockScope scope) {
        return super.visit(expression, scope);
    }

    @Override
    public boolean visit(JavadocAllocationExpression expression, ClassScope scope) {
        return super.visit(expression, scope);
    }

    @Override
    public boolean visit(JavadocArgumentExpression expression, BlockScope scope) {
        return super.visit(expression, scope);
    }

    @Override
    public boolean visit(JavadocArgumentExpression expression, ClassScope scope) {
        return super.visit(expression, scope);
    }

    @Override
    public boolean visit(JavadocArrayQualifiedTypeReference typeRef, BlockScope scope) {
        return super.visit(typeRef, scope);
    }

    @Override
    public boolean visit(JavadocArrayQualifiedTypeReference typeRef, ClassScope scope) {
        return super.visit(typeRef, scope);
    }

    @Override
    public boolean visit(JavadocArraySingleTypeReference typeRef, BlockScope scope) {
        return super.visit(typeRef, scope);
    }

    @Override
    public boolean visit(JavadocArraySingleTypeReference typeRef, ClassScope scope) {
        return super.visit(typeRef, scope);
    }

    @Override
    public boolean visit(JavadocFieldReference fieldRef, BlockScope scope) {
        return super.visit(fieldRef, scope);
    }

    @Override
    public boolean visit(JavadocFieldReference fieldRef, ClassScope scope) {
        return super.visit(fieldRef, scope);
    }

    @Override
    public boolean visit(JavadocImplicitTypeReference implicitTypeReference, BlockScope scope) {
        return super.visit(implicitTypeReference, scope);
    }

    @Override
    public boolean visit(JavadocImplicitTypeReference implicitTypeReference, ClassScope scope) {
        return super.visit(implicitTypeReference, scope);
    }

    @Override
    public boolean visit(JavadocMessageSend messageSend, BlockScope scope) {
        return super.visit(messageSend, scope);
    }

    @Override
    public boolean visit(JavadocMessageSend messageSend, ClassScope scope) {
        return super.visit(messageSend, scope);
    }

    @Override
    public boolean visit(JavadocQualifiedTypeReference typeRef, BlockScope scope) {
        return super.visit(typeRef, scope);
    }

    @Override
    public boolean visit(JavadocQualifiedTypeReference typeRef, ClassScope scope) {
        return super.visit(typeRef, scope);
    }

    @Override
    public boolean visit(JavadocReturnStatement statement, BlockScope scope) {
        return super.visit(statement, scope);
    }

    @Override
    public boolean visit(JavadocReturnStatement statement, ClassScope scope) {
        return super.visit(statement, scope);
    }

    @Override
    public boolean visit(JavadocSingleNameReference argument, BlockScope scope) {
        return super.visit(argument, scope);
    }

    @Override
    public boolean visit(CompilationUnitDeclaration compilationUnitDeclaration, CompilationUnitScope scope) {
        return super.visit(compilationUnitDeclaration, scope);
    }

    @Override
    public boolean visit(CompoundAssignment compoundAssignment, BlockScope scope) {
        return super.visit(compoundAssignment, scope);
    }

    @Override
    public boolean visit(ConditionalExpression conditionalExpression, BlockScope scope) {
        return super.visit(conditionalExpression, scope);
    }

    @Override
    public boolean visit(ConstructorDeclaration constructorDeclaration, ClassScope scope) {
        return super.visit(constructorDeclaration, scope);
    }

    @Override
    public boolean visit(ContinueStatement continueStatement, BlockScope scope) {
        return super.visit(continueStatement, scope);
    }

    @Override
    public boolean visit(DoStatement doStatement, BlockScope scope) {
        return super.visit(doStatement, scope);
    }

    @Override
    public boolean visit(DoubleLiteral doubleLiteral, BlockScope scope) {
        return super.visit(doubleLiteral, scope);
    }

    @Override
    public boolean visit(EmptyStatement emptyStatement, BlockScope scope) {
        return super.visit(emptyStatement, scope);
    }

    @Override
    public boolean visit(EqualExpression equalExpression, BlockScope scope) {
        return super.visit(equalExpression, scope);
    }

    @Override
    public boolean visit(ExplicitConstructorCall explicitConstructor, BlockScope scope) {
        return super.visit(explicitConstructor, scope);
    }

    @Override
    public boolean visit(ExtendedStringLiteral extendedStringLiteral, BlockScope scope) {
        return super.visit(extendedStringLiteral, scope);
    }

    @Override
    public boolean visit(FalseLiteral falseLiteral, BlockScope scope) {
        return super.visit(falseLiteral, scope);
    }

    @Override
    public boolean visit(FieldDeclaration fieldDeclaration, MethodScope scope) {
        return super.visit(fieldDeclaration, scope);
    }

    @Override
    public boolean visit(FieldReference fieldReference, BlockScope scope) {
        return super.visit(fieldReference, scope);
    }

    @Override
    public boolean visit(FieldReference fieldReference, ClassScope scope) {
        return super.visit(fieldReference, scope);
    }

    @Override
    public boolean visit(FloatLiteral floatLiteral, BlockScope scope) {
        return super.visit(floatLiteral, scope);
    }

    @Override
    public boolean visit(ForeachStatement forStatement, BlockScope scope) {
        return super.visit(forStatement, scope);
    }

    @Override
    public boolean visit(ForStatement forStatement, BlockScope scope) {
        return super.visit(forStatement, scope);
    }

    @Override
    public boolean visit(IfStatement ifStatement, BlockScope scope) {
        return super.visit(ifStatement, scope);
    }

    @Override
    public boolean visit(ImportReference importRef, CompilationUnitScope scope) {
        return super.visit(importRef, scope);
    }

    @Override
    public boolean visit(Initializer initializer, MethodScope scope) {
        return super.visit(initializer, scope);
    }

    @Override
    public boolean visit(InstanceOfExpression instanceOfExpression, BlockScope scope) {
        return super.visit(instanceOfExpression, scope);
    }

    @Override
    public boolean visit(ArrayInitializer arrayInitializer, ClassScope scope) {
        return super.visit(arrayInitializer, scope);
    }

    @Override
    public boolean visit(ArrayQualifiedTypeReference arrayQualifiedTypeReference, BlockScope scope) {
        return super.visit(arrayQualifiedTypeReference, scope);
    }

    @Override
    public boolean visit(ArrayQualifiedTypeReference arrayQualifiedTypeReference, ClassScope scope) {
        return super.visit(arrayQualifiedTypeReference, scope);
    }

    @Override
    public boolean visit(ArrayReference arrayReference, BlockScope scope) {
        return super.visit(arrayReference, scope);
    }

    @Override
    public boolean visit(ArrayTypeReference arrayTypeReference, BlockScope scope) {
        return super.visit(arrayTypeReference, scope);
    }

    @Override
    public boolean visit(ArrayTypeReference arrayTypeReference, ClassScope scope) {
        return super.visit(arrayTypeReference, scope);
    }

    @Override
    public boolean visit(AssertStatement assertStatement, BlockScope scope) {
        return super.visit(assertStatement, scope);
    }

    @Override
    public boolean visit(Assignment assignment, BlockScope scope) {
        return super.visit(assignment, scope);
    }

    @Override
    public boolean visit(BinaryExpression binaryExpression, BlockScope scope) {
        return super.visit(binaryExpression, scope);
    }

    @Override
    public boolean visit(Block block, BlockScope scope) {
        return super.visit(block, scope);
    }

    @Override
    public boolean visit(BreakStatement breakStatement, BlockScope scope) {
        return super.visit(breakStatement, scope);
    }

    @Override
    public boolean visit(CaseStatement caseStatement, BlockScope scope) {
        return super.visit(caseStatement, scope);
    }

    @Override
    public boolean visit(CastExpression castExpression, BlockScope scope) {
        return super.visit(castExpression, scope);
    }

    @Override
    public boolean visit(CharLiteral charLiteral, BlockScope scope) {
        return super.visit(charLiteral, scope);
    }

    @Override
    public boolean visit(ClassLiteralAccess classLiteral, BlockScope scope) {
        return super.visit(classLiteral, scope);
    }

    @Override
    public boolean visit(Clinit clinit, ClassScope scope) {
        return super.visit(clinit, scope);
    }

    @Override
    public boolean visit(ArrayInitializer arrayInitializer, BlockScope scope) {
        return super.visit(arrayInitializer, scope);
    }

    @Override
    public boolean visit(ArrayAllocationExpression arrayAllocationExpression, BlockScope scope) {
        return super.visit(arrayAllocationExpression, scope);
    }

    @Override
    public boolean visit(Argument argument, ClassScope scope) {
        return super.visit(argument, scope);
    }

    @Override
    public boolean visit(Argument argument, BlockScope scope) {
        return super.visit(argument, scope);
    }

    @Override
    public boolean visit(AnnotationMethodDeclaration annotationTypeDeclaration, ClassScope classScope) {
        return super.visit(annotationTypeDeclaration, classScope);
    }

    @Override
    public boolean visit(AND_AND_Expression and_and_Expression, BlockScope scope) {
        return super.visit(and_and_Expression, scope);
    }

    @Override
    public boolean visit(AllocationExpression allocationExpression, BlockScope scope) {
        return super.visit(allocationExpression, scope);
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
}
