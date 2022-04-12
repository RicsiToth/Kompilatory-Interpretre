package tree;

import main.Instruction;
import main.VirtualMachine;

public class RepeatInterval implements Syntax {

    private String variableName;
    private Syntax from;
    private Syntax to;
    private Syntax body;

    public RepeatInterval(String variableName, Syntax from, Syntax to, Syntax body) {
        this.variableName = variableName;
        this.from = from;
        this.to = to;
        this.body = body;
    }

    @Override
    public void execute(VirtualMachine vm) {

    }

    // TODO REPAIR THIS
    @Override
    public void generate(VirtualMachine vm) {
      /*  from.generate(vm);

        vm.setMemValue(Instruction.JUMP.ordinal());
        int jumpToCondition = vm.getCurrentAddr();
        vm.setMemValue(0);

        // incrementing variable
        int incrementAddr = vm.getCurrentAddr();
        vm.setMemValue(Instruction.GET.ordinal());
        vm.setMemValue(vm.getVariable(variableName));

        vm.setMemValue(Instruction.PUSH.ordinal());
        vm.setMemValue(1);

        vm.setMemValue(Instruction.ADD.ordinal());

        vm.setMemValue(Instruction.SET.ordinal());
        vm.setMemValue(vm.getVariable(variableName));

        // checking if variable is greater than the limit
        vm.setMemValueToAddr(jumpToCondition, vm.getCurrentAddr());
        vm.setMemValue(Instruction.GET.ordinal());
        vm.setMemValue(vm.getVariable(variableName));

        to.generate(vm);

        vm.setMemValue(Instruction.LOWER_EQUAL.ordinal());

        vm.setMemValue(Instruction.JUMP_IF_FALSE.ordinal());
        int jumpToAddr = vm.getCurrentAddr();
        vm.setMemValue(0);
        body.generate(vm);
        vm.setMemValue(Instruction.JUMP.ordinal());
        vm.setMemValue(incrementAddr);
        vm.setMemValueToAddr(jumpToAddr, vm.getCurrentAddr());*/
    }

    @Override
    public void translate(int indent) {

    }

    @Override
    public int evaluate() {
        return 0;
    }
}
