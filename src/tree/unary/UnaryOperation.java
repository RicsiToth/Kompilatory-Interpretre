package tree.unary;

import tree.Syntax;

public abstract class UnaryOperation implements Syntax {
    protected Syntax variable;

    public UnaryOperation(Syntax variable) {
        this.variable = variable;
    }
}
