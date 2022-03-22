package tree.unary;

import main.VirtualMachine;
import tree.Syntax;

public class Not extends UnaryOperation {

    public Not(Syntax variable) {
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
        int result = variable.evaluate();
        if(result == 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
