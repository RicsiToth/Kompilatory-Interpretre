package tree;

import main.Instruction;
import main.VirtualMachine;

public final class Lt implements Syntax {

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
        vm.setMemValue(Instruction.LT.ordinal());
        angle.generate(vm);
    }

    @Override
    public void translate(int indent) {
        System.out.print(new String(new char[indent]).replace("\0", " "));
        System.out.print("dolava(");
        angle.translate(indent);
        System.out.println(");");
    }
}
