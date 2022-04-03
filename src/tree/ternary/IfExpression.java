package tree.ternary;

import main.Instruction;
import main.VirtualMachine;
import tree.Syntax;

public class IfExpression extends TernaryOperation {

    public IfExpression(Syntax condition, Syntax ifTrue, Syntax ifFalse) {
        super(condition, ifTrue, ifFalse);
    }

    @Override
    public void execute(VirtualMachine vm) {

    }

    @Override
    public void generate(VirtualMachine vm) {
        operand1.generate(vm);
        vm.setMemValue(Instruction.JUMP_IF_FALSE.ordinal());
        int falseAddr = vm.getCurrentAddr();
        vm.setMemValue(0);
        operand2.generate(vm);
        vm.setMemValue(Instruction.JUMP.ordinal());
        int trueAddr = vm.getCurrentAddr();
        vm.setMemValue(0);
        vm.setMemValueToAddr(falseAddr, vm.getCurrentAddr());
        operand3.generate(vm);
        vm.setMemValueToAddr(trueAddr, vm.getCurrentAddr());
    }

    @Override
    public void translate(int indent) {

    }

    @Override
    public int evaluate() {
        if(operand1.evaluate() == 1) {
            return operand2.evaluate();
        } else {
            return operand3.evaluate();
        }
    }
}
