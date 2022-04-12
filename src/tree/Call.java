package tree;

import main.Instruction;
import main.VirtualMachine;
import tree.identifier.Subroutine;

public class Call implements Syntax {

    private Subroutine subroutine;
    private Block arguments;

    public Call(Subroutine subroutine, Block arguments) {
        this.subroutine = subroutine;
        this.arguments = arguments;
    }

    @Override
    public void execute(VirtualMachine vm) {

    }

    @Override
    public void generate(VirtualMachine vm) {
        arguments.generate(vm);
        vm.setMemValue(Instruction.CALL.ordinal());
        vm.setMemValue(subroutine.getSubroutineAddr());
    }

    @Override
    public void translate(int indent) {

    }

    @Override
    public int evaluate() {
        return 0;
    }
}
