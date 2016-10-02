package my.powerassert.ecj;

import org.eclipse.jdt.internal.compiler.ast.*;

import java.util.Arrays;

public class TreeFactoryECJ {

    protected int lineNumber;
    protected int position;

    TreeFactoryECJ() {
    }

    void setPosition(int position, int lineNumber) {
        this.position = position;
        this.lineNumber = lineNumber;
    }

    StringLiteral literal(String value) {
        return augment(new StringLiteral(value.toCharArray(), -1, -1, -1));
    }

    IntLiteral literal(int value) {
        return augment(IntLiteral.buildIntLiteral(Integer.valueOf(value).toString().toCharArray(), -1, -1));
    }

    NullLiteral null_() {
        return new NullLiteral(position, position);
    }

    AllocationExpression new_(String clazz, Expression... arguments) {
        AllocationExpression new_ = new AllocationExpression();
        new_.type = fullyQualifiedName(clazz);
        new_.arguments = arguments;
        return augment(new_);
    }

    LocalDeclaration var(String name, String clazz, Expression initValue) {
        LocalDeclaration var = new LocalDeclaration(name.toCharArray(), position, position);
        var.type = fullyQualifiedName(clazz);
        var.initialization = initValue;
        return var;
    }

    MessageSend call(String variable, String method, Expression... arguments) {
        MessageSend call = new MessageSend();
        call.receiver = new SingleNameReference(variable.toCharArray(), position);
        call.selector = method.toCharArray();
        call.arguments = arguments;
        return call;
    }

    TryStatement tryCatch(Argument catchVariable, Block catchBlock, Statement... body) {
        TryStatement tryCatch = new TryStatement();
        tryCatch.tryBlock = block(body);
        tryCatch.catchArguments = new Argument[] {catchVariable};
        tryCatch.catchBlocks = new Block[] {catchBlock};
        return augment(tryCatch);
    }

    Argument argument(String variable, String clazz) {
        return new Argument(variable.toCharArray(), posNom(), fullyQualifiedName(clazz), 0);
    }

    ThrowStatement throw_(Expression thrown) {
        return new ThrowStatement(thrown, position, position);
    }

    IfStatement if_(Expression expression, Statement then, Statement else_) {
        return new IfStatement(expression, then, else_, position, position);
    }

    Block block(Statement... statements) {
        Block block = new Block(0);
        block.statements = statements;
        return augment(block);
    }

//    JCStatement exec(JCExpression expression) {
//        return augment(factory.Exec(expression));
//    }

//    private JCFieldAccess access(String variable, String fieldOrMethod) {
//        return augment(factory.Select(factory.Ident(name(variable)), name(fieldOrMethod)));
//    }

    private <T extends ASTNode> T augment(T tree) {
        tree.sourceStart = position;
        tree.sourceEnd = position;
        return tree;
    }

    private QualifiedTypeReference fullyQualifiedName(String dotSeparatedName) {
        String[] parts = dotSeparatedName.split("\\.");
        char[][] array = new char[parts.length][];
        long[] positions = new long[parts.length];
        for (int i = 1; i < parts.length; i++) {
            array[i] = parts[i].toCharArray();
            positions[i] = position;
        }
        return new QualifiedTypeReference(array, positions);
    }

    private long posNom() {
        return (position << 32) + position;
    }
}
