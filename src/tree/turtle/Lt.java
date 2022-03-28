package tree.turtle;

import main.Instruction;
import main.VirtualMachine;
import tree.Syntax;

public final class Lt extends TurtleCommand {

    private Syntax expression;

    public Lt(Syntax expression) {
        this.expression = expression;
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.getTurtle().turnLeft(expression.evaluate());
    }

    @Override
    public void generate(VirtualMachine vm) {
        expression.generate(vm);
        vm.setMemValue(Instruction.LT.ordinal());
    }

    @Override
    public void translate(int indent) {
        System.out.print(new String(new char[indent]).replace("\0", " "));
        System.out.print("dolava(");
        expression.translate(indent);
        System.out.println(");");
    }
}
