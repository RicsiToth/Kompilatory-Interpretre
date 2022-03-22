package tree.unary;

import main.VirtualMachine;
import tree.Syntax;

public class Braces extends UnaryOperation {

    public Braces(Syntax variable) {
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
        return variable.evaluate();
    }
}
