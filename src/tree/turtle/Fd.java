package tree.turtle;

import main.Instruction;
import main.VirtualMachine;
import tree.Constant;

public final class Fd extends TurtleCommand {

    private Constant length;

    public Fd(Constant length) {
        this.length = length;
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.getTurtle().forward(length.getValue());
    }

    @Override
    public void generate(VirtualMachine vm) {
        vm.setMemValue(Instruction.FD.ordinal());
        length.generate(vm);
    }

    @Override
    public void translate(int indent) {
        System.out.print(new String(new char[indent]).replace("\0", " ") + "dopredu(");
        length.translate(indent);
        System.out.println(");");
    }
}
