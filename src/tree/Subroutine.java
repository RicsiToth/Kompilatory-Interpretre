package tree;

import main.Instruction;
import main.VirtualMachine;

public class Subroutine implements Syntax {

    private Syntax body;
    private int bodyAddr;

    @Override
    public void execute(VirtualMachine vm) {

    }

    @Override
    public void generate(VirtualMachine vm) {
        vm.setMemValue(Instruction.JUMP.ordinal());
        vm.setMemValue(0);
        bodyAddr = vm.getCurrentAddr();
        body.generate(vm);
        vm.setMemValue(Instruction.RETURN.ordinal());
        vm.setMemValueToAddr(bodyAddr - 1, vm.getCurrentAddr());
    }

    @Override
    public void translate(int indent) {

    }

    @Override
    public int evaluate() {
        return 0;
    }

    public int getSubroutineAddr() {
        return bodyAddr;
    }

    public void setBody(Syntax body) {
        this.body = body;
    }
}
