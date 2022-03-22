package tree.unary;

import main.VirtualMachine;
import tree.Syntax;

public class Sqrt extends UnaryOperation {

    public Sqrt(Syntax variable) {
        super(variable);
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
        return (int) Math.sqrt(variable.evaluate());
    }
}
