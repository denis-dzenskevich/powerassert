package my.powerassert.ecj;

import my.powerassert.*;

import my.powerassert.EnvironmentProvider;
import my.powerassert.ProcessorIntf;
import my.powerassert.javac.Replacements;
import org.eclipse.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import org.eclipse.jdt.internal.compiler.apt.model.TypeElementImpl;
import org.eclipse.jdt.internal.compiler.ast.*;
import org.eclipse.jdt.internal.compiler.lookup.LookupEnvironment;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.jdt.internal.compiler.parser.Parser;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.List;

public class EcjProcessorImpl implements ProcessorIntf {

    public void process(EnvironmentProvider environmentProvider, ProcessingEnvironment processingEnv, RoundEnvironment roundEnv) {
        TreeFactory treeFactory = environmentProvider.createTreeFactory(processingEnv);
        CompilerFacade compilerFacade = environmentProvider.createCompilerFacade();
        BaseProcessingEnvImpl processingEnvironment = (BaseProcessingEnvImpl) processingEnv;
        for (Element element : roundEnv.getRootElements()) {
//            TreePath path = trees.getPath(element);
//            JCTree.JCCompilationUnit compilationUnit = (JCTree.JCCompilationUnit) path.getCompilationUnit();
            TypeElementImpl typeElement = (TypeElementImpl) element;
            SourceTypeBinding binding = (SourceTypeBinding) typeElement._binding;
            ExpressionMorpherECJ expressionMorpher = new ExpressionMorpherECJ(null, compilerFacade);
            for (CompilationUnitDeclaration compilationUnit : processingEnvironment.getCompiler().unitsToProcess) {
                Parser parser = ((BaseProcessingEnvImpl) processingEnv).getCompiler().parser;
                parser.getMethodBodies(compilationUnit);
                TreeFactoryECJ factory = new TreeFactoryECJ();
                for (TypeDeclaration type : compilationUnit.types) { // TODO compilationUnit.localTypes ?
                    for (AbstractMethodDeclaration method : type.methods) {
                        if (method.statements != null) {
                            for (int i = 0; i < method.statements.length; i++) {
                                Statement statement = method.statements[i];
                                if (statement instanceof AssertStatement) {
                                    AssertStatement assertStatement = (AssertStatement) statement;
                                    Expression message = assertStatement.exceptionArgument != null ? assertStatement.exceptionArgument : factory.null_();
                                    char[] contents = ((SourceTypeBinding) ((TypeElementImpl) element)._binding).scope.referenceContext.compilationResult().getCompilationUnit().getContents();
                                    String source = new String(contents, assertStatement.assertExpression.sourceStart(), assertStatement.assertExpression.sourceEnd() - assertStatement.assertExpression.sourceStart() + 1);
                                    LocalDeclaration varDeclaration = factory.var("_powerassert", "my.powerassert.PowerAssert"
                                            , factory.new_("my.powerassert.PowerAssert", message, factory.literal(source)));
                                    AllocationExpression exceptionInitialization = new AllocationExpression();
                                    exceptionInitialization.type = new QualifiedTypeReference(charArrays("java", "lang", "AssertionError"), new long[] {-1, -1, -1});
                                    MessageSend exceptionArgument = new MessageSend();
                                    exceptionArgument.receiver = new SingleNameReference("_powerassert".toCharArray(), -1);
                                    exceptionArgument.selector = "build".toCharArray();
                                    exceptionInitialization.arguments = new Expression[] {
                                            exceptionArgument
                                    };
                                    ThrowStatement throwStatement = new ThrowStatement(exceptionInitialization, -1, -1);
                                    Block block = new Block(1);
                                    block.statements = new Statement[] {
                                            varDeclaration,
                                            throwStatement
                                    };
                                    IfStatement ifStatement = new IfStatement(((AssertStatement) statement).assertExpression, null, block, -1, -1);
                                    method.statements[i] = ifStatement;
                                    List<ExpressionPart<ASTNode>> parts = expressionMorpher.splitExpression(assertStatement.assertExpression);
                                    for (ExpressionPart<ASTNode> part : parts) {
                                        TryStatement tryStatement = new TryStatement();
                                        tryStatement.tryBlock = new Block(0);
//                                        tryStatement
                                        System.out.println(part.expression);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            System.out.println(element); // element is TypeElementImpl
            // TODO what if file contains several classes?
            ClassMorpherECJ classMorpher = new ClassMorpherECJ((TypeElement) element, compilerFacade, processingEnv, treeFactory, null/*expressionMorpher*/);
            Replacements replacements = null;//new Replacements();
            classMorpher.run(replacements);
//            replacements.execute();
        }
    }

    private static char[][] charArrays(String... strings) {
        char[][] arrays = new char[strings.length][];
        for (int i = 0; i < strings.length; i++) {
            arrays[i] = strings[i].toCharArray();
        }
        return arrays;
    }
}
