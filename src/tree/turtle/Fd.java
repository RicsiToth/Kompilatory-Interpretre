package tree.turtle;

import main.Instruction;
import main.VirtualMachine;
import tree.Syntax;

public final class Fd extends TurtleCommand {

    private Syntax expression;

    public Fd(Syntax expression) {
        this.expression = expression;
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.getTurtle().forward(expression.evaluate());
    }

    @Override
    public void generate(VirtualMachine vm) {
    	expression.generate(vm);
        vm.setMemValue(Instruction.FD.ordinal());
    }

    @Override
    public void translate(int indent) {
        System.out.print(new String(new char[indent]).replace("\0", " ") + "dopredu(");
        expression.translate(indent);
        System.out.println(");");
    }
}
