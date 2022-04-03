package tree.binary.relational;

import main.Instruction;
import main.VirtualMachine;
import tree.Syntax;
import tree.binary.BinaryOperation;

public class Greater extends BinaryOperation {

    public Greater(Syntax left, Syntax right) {
        super(left, right);
    }

    @Override
    public void execute(VirtualMachine vm) {

    }

    @Override
    public void generate(VirtualMachine vm) {
        left.generate(vm);
        right.generate(vm);
        vm.setMemValue(Instruction.GREATER.ordinal());
    }

    @Override
    public void translate(int indent) {

    }

    @Override
    public int evaluate() {
        if (left.evaluate() > right.evaluate()) {
            return 1;
        } else {
            return 0;
        }
    }
}
