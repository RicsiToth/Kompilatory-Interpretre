package tree.turtle;

import main.Instruction;
import main.VirtualMachine;
import tree.Constant;

public final class Rt extends TurtleCommand {

    private Constant angle;

    public Rt(Constant angle) {
        this.angle = angle;
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.getTurtle().turnRight(angle.getValue());
    }

    @Override
    public void generate(VirtualMachine vm) {
        vm.setMemValue(Instruction.RT.ordinal());
        angle.generate(vm);
    }

    @Override
    public void translate(int indent) {
        System.out.print(new String(new char[indent]).replace("\0", " ") + "doprava(");
        angle.translate(indent);
        System.out.println(");");
    }
}
