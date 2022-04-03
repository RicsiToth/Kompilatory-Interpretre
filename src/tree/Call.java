package tree;

import main.Instruction;
import main.VirtualMachine;

public class Call implements Syntax {

    private String name;

    public Call(String name) {
        this.name = name;
    }

    @Override
    public void execute(VirtualMachine vm) {

    }

    @Override
    public void generate(VirtualMachine vm) {
        vm.setMemValue(Instruction.CALL.ordinal());
        vm.setMemValue(vm.getSubroutine(name).getSubroutineAddr());
    }

    @Override
    public void translate(int indent) {

    }

    @Override
    public int evaluate() {
        return 0;
    }
}
