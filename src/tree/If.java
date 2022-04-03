package tree;

import main.Instruction;
import main.VirtualMachine;

public class If implements Syntax {

    private Syntax condition;
    private Syntax bodyForTrue;
    private Syntax bodyForFalse;

    public If(Syntax condition, Syntax bodyForTrue, Syntax bodyForFalse) {
        this.condition = condition;
        this.bodyForTrue = bodyForTrue;
        this.bodyForFalse = bodyForFalse;
    }

    @Override
    public void execute(VirtualMachine vm) {

    }

    @Override
    public void generate(VirtualMachine vm) {
        condition.generate(vm);
        vm.setMemValue(Instruction.JUMP_IF_FALSE.ordinal());
        int falseAddr = vm.getCurrentAddr();
        vm.setMemValue(0);
        bodyForTrue.generate(vm);
        if(bodyForFalse == null) {
            vm.setMemValueToAddr(falseAddr, vm.getCurrentAddr());
        } else {
            vm.setMemValue(Instruction.JUMP.ordinal());
            int trueAddr = vm.getCurrentAddr();
            vm.setMemValue(0);
            vm.setMemValueToAddr(falseAddr, vm.getCurrentAddr());
            bodyForFalse.generate(vm);
            vm.setMemValueToAddr(trueAddr, vm.getCurrentAddr());
        }
    }

    @Override
    public void translate(int indent) {

    }

    @Override
    public int evaluate() {
        return 0;
    }
}
