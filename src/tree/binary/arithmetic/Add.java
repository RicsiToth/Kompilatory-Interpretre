package tree.binary.arithmetic;

import main.Instruction;
import main.VirtualMachine;
import tree.Syntax;
import tree.binary.BinaryOperation;

public class Add extends BinaryOperation {

    public Add(Syntax left, Syntax right) {
        super(left, right);
    }

    @Override
    public void execute(VirtualMachine vm) {

    }

    @Override
    public void generate(VirtualMachine vm) {
    	left.generate(vm);
    	right.generate(vm);
    	vm.setMemValue(Instruction.ADD.ordinal());
    }

    @Override
    public void translate(int indent) {

    }

    @Override
    public int evaluate() {
        return left.evaluate() + right.evaluate();
    }
}
