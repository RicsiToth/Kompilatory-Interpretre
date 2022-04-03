package tree;

import main.Instruction;
import main.VirtualMachine;

public class While implements Syntax {

    private Syntax condition;
    private Syntax body;

    public While(Syntax condition, Syntax body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void execute(VirtualMachine vm) {

    }

    @Override
    public void generate(VirtualMachine vm) {
        int conditionAddr = vm.getCurrentAddr();
        condition.generate(vm);
        vm.setMemValue(Instruction.JUMP_IF_FALSE.ordinal());
        int jumpToAddr = vm.getCurrentAddr();
        vm.setMemValue(0);
        body.generate(vm);
        vm.setMemValue(Instruction.JUMP.ordinal());
        vm.setMemValue(conditionAddr);
        vm.setMemValueToAddr(jumpToAddr, vm.getCurrentAddr());
    }

    @Override
    public void translate(int indent) {

    }

    @Override
    public int evaluate() {
        return 0;
    }
}
