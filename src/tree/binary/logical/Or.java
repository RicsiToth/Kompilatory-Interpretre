package tree.binary.logical;

import main.VirtualMachine;
import tree.Syntax;
import tree.binary.BinaryOperation;

public class Or extends BinaryOperation {

    public Or(Syntax left, Syntax right) {
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
        if (left.evaluate() == 1 || right.evaluate() == 1) {
            return 1;
        } else {
            return 0;
        }
    }
}
