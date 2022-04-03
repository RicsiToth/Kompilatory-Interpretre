package tree.ternary;

import tree.Syntax;

public abstract class TernaryOperation implements Syntax {
    protected Syntax operand1;
    protected Syntax operand2;
    protected Syntax operand3;

    public TernaryOperation(Syntax operand1, Syntax operand2, Syntax operand3) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operand3 = operand3;
    }
}
