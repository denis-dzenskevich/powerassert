package my.powerassert.ecj;

import my.powerassert.CompilerFacade;
import my.powerassert.ExpressionMorpher;
import my.powerassert.TreeFactory;
import my.powerassert.javac.Replacements;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.util.AbstractElementVisitor6;
import javax.lang.model.util.ElementScanner6;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClassMorpherECJ {

    private final TypeElement element;
    private final CompilerFacade compilerFacade;
    private final TreeFactory t;
    private final ProcessingEnvironment processingEnvironment;
//    private final CharSequence source;
    private final ExpressionMorpherECJ expressionMorpher;
    private boolean attributed;

    public ClassMorpherECJ(TypeElement element, CompilerFacade compilerFacade, ProcessingEnvironment processingEnvironment, TreeFactory treeFactory, ExpressionMorpherECJ expressionMorpher) {
        this.element = element;
        this.compilerFacade = compilerFacade;
        this.processingEnvironment = processingEnvironment;
        this.t = treeFactory;
        this.expressionMorpher = expressionMorpher;
    }

    public void run(final Replacements replacements) {
        element.accept(new ElementScanner6<Object, Object>() {
            @Override
            public Object visitPackage(PackageElement e, Object o) {
                return super.visitPackage(e, o);
            }
            @Override
            public Object visitType(TypeElement e, Object o) {
                return super.visitType(e, o);
            }
            @Override
            public Object visitVariable(VariableElement e, Object o) {
                return super.visitVariable(e, o);
            }
            @Override
            public Object visitExecutable(ExecutableElement e, Object o) {
                System.out.println(e);
                return super.visitExecutable(e, o);
            }
            @Override
            public Object visitTypeParameter(TypeParameterElement e, Object o) {
                return super.visitTypeParameter(e, o);
            }
        }, null);
/*        new TreePathScanner<Object, Object>() {
            @Override
            public Object visitAssert(AssertTree assertTree, Object o) {
                JCAssert jcAssert = (JCAssert) assertTree;
                attributeIfNeeded();
                List<JCStatement> statements = new ArrayList<JCStatement>();
                int basePosition = jcAssert.getCondition().getStartPosition();
                t.setPosition(basePosition);
                // generated: PowerAssert _powerassert = new PowerAssert(<message>, <expressionString>);
                JCExpression instantiation = t.new_("my.powerassert.PowerAssert"
                        , jcAssert.getDetail() != null ? jcAssert.getDetail() : t.literal("assertion failed")
                        , t.literal(sourceFor(jcAssert.getCondition())));
                JCVariableDecl declaration = t.var("_powerassert", "my.powerassert.PowerAssert", instantiation);
                statements.add(declaration);
                for (ExpressionMorpher.ExpressionPart part : expressionMorpher.splitExpression(jcAssert.getCondition())) {
                    // generated: try {
                    //                _powerassert.part(<level>, <position>, <expression>);
                    //            } catch (java.lang.Throwable _powerassert_catch) {
                    //            }
                    JCMethodInvocation invocation = t.call("_powerassert", "part"
                            , t.literal(part.level), t.literal(part.position - basePosition)
                            , part.expression);
                    JCCatch catchBlock = t.catch_("_powerassert_catch", "java.lang.Throwable");
                    statements.add(t.tryCatch(catchBlock, t.exec(invocation)));
                }
                // generated: throw new java.lang.AssertionError(_powerassert.build());
                JCThrow throwStatement = t.throw_(t.new_("java.lang.AssertionError", t.call("_powerassert", "build")));
                statements.add(throwStatement);
                // generated: if (<expression>) {
                //                // empty
                //            } else {
                //                <statements>
                //            }
                StatementTree substitution = t.if_(jcAssert.getCondition(), t.block(), t.block(statements.toArray(new JCStatement[statements.size()])));
                replacements.add(getCurrentPath().getParentPath().getLeaf(), jcAssert, substitution);
                return null;
            }
        }.scan(path, null);*/
    }

//    private String sourceFor(JCTree tree) {
//        return source.subSequence(tree.getStartPosition(), compilerFacade.getEndPosition(compilationUnit, tree)).toString();
//    }

//    private void attributeIfNeeded() {
//        if (!attributed) {
//            try {
//                Enter enter = Enter.instance(processingEnvironment.getContext());
//                Attr.instance(processingEnvironment.getContext()).attribExpr((JCTree) path.getLeaf(), enter.getTopLevelEnv((JCCompilationUnit) compilationUnit), Type.noType);
//                attributed = true;
//            } catch (Exception e) {
//                throw new RuntimeException("Cannot attribute: " + e.getMessage(), e);
//            }
//        }
//    }
}
