package tree.identifier;

import main.Instruction;
import main.VirtualMachine;

public class LocalVariable extends Variable {

    public LocalVariable(String name, int position) {
        super(name, position);
    }

    @Override
    public void generate(VirtualMachine vm) {
        vm.setMemValue(Instruction.GET_LOCAL.ordinal());
        vm.setMemValue(position);
    }

    public void generateSet(VirtualMachine vm) {
        vm.setMemValue(Instruction.SET_LOCAL.ordinal());
        vm.setMemValue(position);
    }
}
