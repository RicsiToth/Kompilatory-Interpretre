package tree.unary;

import main.Instruction;
import main.VirtualMachine;
import tree.Syntax;

public class Minus extends UnaryOperation {

    public Minus(Syntax variable) {
        super(variable);
    }

    @Override
    public void execute(VirtualMachine vm) {

    }

    @Override
    public void generate(VirtualMachine vm) {
    	variable.generate(vm);
    	vm.setMemValue(Instruction.MINUS.ordinal());
    }

    @Override
    public void translate(int indent) {

    }

    @Override
    public int evaluate() {
        return -variable.evaluate();
    }
}
