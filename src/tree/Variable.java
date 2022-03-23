package tree;

import main.Instruction;
import main.VirtualMachine;

public class Variable implements Syntax {
	
	private String name;
	
	public Variable(String name) {
		this.name = name;
	}

	@Override
	public void execute(VirtualMachine vm) {
		// TODO Auto-generated method stub

	}

	@Override
	public void generate(VirtualMachine vm) {
		vm.setMemValue(Instruction.GET.ordinal());
		vm.setMemValue(vm.getVariable(name));
	}
	
	public void generateSet(VirtualMachine vm) {
		vm.setMemValue(Instruction.SET.ordinal());
		vm.setMemValue(vm.getVariable(name));
	}

	@Override
	public void translate(int indent) {
		// TODO Auto-generated method stub

	}

	@Override
	public int evaluate() {
		// TODO Auto-generated method stub
		return 0;
	}

}
