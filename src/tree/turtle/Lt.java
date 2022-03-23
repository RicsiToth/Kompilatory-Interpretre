package tree.turtle;

import main.Instruction;
import main.VirtualMachine;
import tree.Constant;

public final class Lt extends TurtleCommand {

    private Constant angle;

    public Lt(Constant angle) {
        this.angle = angle;
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.getTurtle().turnLeft(angle.getValue());
    }

    @Override
    public void generate(VirtualMachine vm) {
    	angle.generate(vm);
        vm.setMemValue(Instruction.LT.ordinal());
    }

    @Override
    public void translate(int indent) {
        System.out.print(new String(new char[indent]).replace("\0", " "));
        System.out.print("dolava(");
        angle.translate(indent);
        System.out.println(");");
    }
}
