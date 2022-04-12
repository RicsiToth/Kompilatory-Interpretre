package tree.identifier;

import main.Instruction;
import main.VirtualMachine;

public class GlobalVariable extends Variable {

    public GlobalVariable(String name, int position) {
        super(name, position);
    }

    @Override
    public void generate(VirtualMachine vm) {
        vm.setMemValue(Instruction.GET.ordinal());
        vm.setMemValue(position);
    }

    public void generateSet(VirtualMachine vm) {
        vm.setMemValue(Instruction.SET.ordinal());
        vm.setMemValue(position);
    }
}
