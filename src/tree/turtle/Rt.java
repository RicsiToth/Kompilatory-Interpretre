package tree.turtle;

import main.Instruction;
import main.VirtualMachine;
import tree.Syntax;

public final class Rt extends TurtleCommand {

    private Syntax expression;

    public Rt(Syntax expression) {
        this.expression = expression;
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.getTurtle().turnRight(expression.evaluate());
    }

    @Override
    public void generate(VirtualMachine vm) {
        expression.generate(vm);
        vm.setMemValue(Instruction.RT.ordinal());
    }

    @Override
    public void translate(int indent) {
        System.out.print(new String(new char[indent]).replace("\0", " ") + "doprava(");
        expression.translate(indent);
        System.out.println(");");
    }
}
