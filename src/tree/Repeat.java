package tree;

import main.Instruction;
import main.VirtualMachine;

public final class Repeat implements Syntax {

    private Syntax countExpression;
    private Block block;

    public Repeat(Syntax countExpression, Block block) {
        this.countExpression = countExpression;
        this.block = block;
    }

    @Override
    public void execute(VirtualMachine vm) {
        for(int i = 0; i < countExpression.evaluate(); i++) {
            block.execute(vm);
        }
    }

    @Override
    public void generate(VirtualMachine vm) {
        countExpression.generate(vm);
        int addr = vm.getCurrentAddr();
        block.generate(vm);
        vm.setMemValue(Instruction.LOOP.ordinal());
        vm.setMemValue(addr);
    }

    @Override
    public void translate(int indent) {
        System.out.print(new String(new char[indent]).replace("\0", " "));
        System.out.print("for (int i = 0; i < ");
        countExpression.translate(indent);
        System.out.println("; i++) {");
        block.translate(indent + 4);
        System.out.print(new String(new char[indent]).replace("\0", " "));
        System.out.println("}");
    }

    @Override
    public int evaluate() {
        return 0;
    }
}
