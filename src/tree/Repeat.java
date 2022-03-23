package tree;

import main.Instruction;
import main.VirtualMachine;

public final class Repeat implements Syntax {

    private Constant count;
    private Block block;

    public Repeat(Constant count, Block block) {
        this.count = count;
        this.block = block;
    }

    @Override
    public void execute(VirtualMachine vm) {
        for(int i = 0; i < count.getValue(); i++) {
            block.execute(vm);
        }
    }

    @Override
    public void generate(VirtualMachine vm) {
       // int endOfMem = vm.getEndAddr();
        vm.setMemValue(Instruction.SET.ordinal());
      //  vm.setMemValue(endOfMem);
        count.generate(vm);
        int addr = vm.getCurrentAddr();
        block.generate(vm);
     //   vm.setEndAddr(endOfMem);
        vm.setMemValue(Instruction.LOOP.ordinal());
       // vm.setMemValue(endOfMem);
        vm.setMemValue(addr);
    }

    @Override
    public void translate(int indent) {
        System.out.print(new String(new char[indent]).replace("\0", " "));
        System.out.print("for (int i = 0; i < ");
        count.translate(indent);
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
