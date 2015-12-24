package my.powerassert;

import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;

import java.lang.reflect.Array;
import java.util.Arrays;

class TreeFactory {

    private final Factory factory;
    private final Names names;
    private int position;

    TreeFactory(Factory factory, Names names) {
        this.factory = factory;
        this.names = names;
    }

    void setPosition(int position) {
        this.position = position;
    }

    JCLiteral literal(String value) {
        return augment(factory.Literal(TypeTag.CLASS, value));
    }

    JCLiteral literal(int value) {
        return augment(factory.Literal(TypeTag.INT, value));
    }

    JCNewClass new_(String clazz, JCExpression... arguments) {
        return augment(factory.NewClass(null /* encl */
                , null /* typeargs */
                , fullyQualifiedName(clazz)
                , toJavacList(arguments)
                , null /* def */));
    }

    JCVariableDecl var(String name, String clazz, JCExpression initValue) {
        return augment(factory.VarDef(factory.Modifiers(0, List.<JCAnnotation>nil()) /* mods */
                , name(name) /* name */
                , fullyQualifiedName(clazz) /* vartype */
                , initValue /* init */));
    }

    JCMethodInvocation call(String variable, String method, JCExpression... arguments) {
        return augment(factory.Apply(List.<JCExpression>nil() /* typeargs */
                , access(variable, method) /* meth */
                , toJavacList(arguments) /* args */));
    }

    JCTry tryCatch(JCCatch catch_, JCStatement... body) {
        return augment(factory.Try(block(body) /* body */
                , toJavacList(catch_) /* catchers */
                , null /* finalizer */));
    }

    JCCatch catch_(String variable, String clazz) {
        JCVariableDecl variableDecl = var(variable, clazz, null);
        return augment(factory.Catch(variableDecl, factory.Block(0, List.<JCStatement>nil())));
    }

    JCThrow throw_(JCExpression thrown) {
        return augment(factory.Throw(thrown));
    }

    JCIf if_(JCExpression expression, JCStatement then, JCStatement else_) {
        return augment(factory.If(augment(factory.Parens(expression))
                , then, else_));
    }

    JCBlock block(JCStatement... statements) {
        return augment(factory.Block(0, toJavacList(statements)));
    }

    JCStatement exec(JCExpression expression) {
        return augment(factory.Exec(expression));
    }

    private JCFieldAccess access(String variable, String fieldOrMethod) {
        return augment(factory.Select(factory.Ident(name(variable)), name(fieldOrMethod)));
    }

    private <T extends JCTree> T augment(T tree) {
        tree.setPos(position);
        return tree;
    }

    private Name name(String stringName) {
        return names.fromString(stringName);
    }

    private JCExpression fullyQualifiedName(String dotSeparatedName) {
        String[] parts = dotSeparatedName.split("\\.");
        JCExpression value = factory.Ident(names.fromString(parts[0]));
        for (int i = 1; i < parts.length; i++) {
            value = factory.Select(value, names.fromString(parts[i]));
        }
        return value;
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> toJavacList(T... list) {
        switch (list.length) {
            case 0: return List.nil();
            case 1: return List.of(list[0]);
            case 2: return List.of(list[0], list[1]);
            case 3: return List.of(list[0], list[1], list[2]);
            default: {
                T[] remainder = Arrays.copyOfRange(list, 3, list.length);
                return List.of(list[0], list[1], list[2], remainder);
            }
        }
    }
}
