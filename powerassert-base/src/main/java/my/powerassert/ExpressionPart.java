package my.powerassert;

public class ExpressionPart<T> {

    public final T expression;
    public final int level;
    public final int position;

    public ExpressionPart(T expression, int level, int position) {
        this.expression = expression;
        this.level = level;
        this.position = position;
    }
}
