package tree.binary;

import tree.Syntax;

public abstract class BinaryOperation implements Syntax {
    protected Syntax left;
    protected Syntax right;

    public BinaryOperation(Syntax left, Syntax right) {
        this.left = left;
        this.right = right;
    }
}
