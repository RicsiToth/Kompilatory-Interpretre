package tree.binary.arithmetic;

import main.VirtualMachine;
import tree.Syntax;
import tree.binary.BinaryOperation;

public class Div extends BinaryOperation {

    public Div(Syntax left, Syntax right) {
        super(left, right);
    }

    @Override
    public void execute(VirtualMachine vm) {

    }

    @Override
    public void generate(VirtualMachine vm) {

    }

    @Override
    public void translate(int indent) {

    }

    @Override
    public int evaluate() {
        return left.evaluate() / right.evaluate();
    }
}
