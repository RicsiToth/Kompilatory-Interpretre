package tree.binary.arithmetic;

import main.VirtualMachine;
import tree.Syntax;
import tree.binary.BinaryOperation;

public class Pow extends BinaryOperation {

    public Pow(Syntax left, Syntax right) {
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
        return (int) Math.pow(left.evaluate(), right.evaluate());
    }
}
