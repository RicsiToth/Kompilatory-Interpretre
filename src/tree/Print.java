package tree;

import main.Instruction;
import main.VirtualMachine;

public class Print implements Syntax {
	
	private Syntax expression;
	
	public Print(Syntax expression) {
		this.expression = expression;
	}

	@Override
	public void execute(VirtualMachine vm) {
		// TODO Auto-generated method stub

	}

	@Override
	public void generate(VirtualMachine vm) {
		expression.generate(vm);
		vm.setMemValue(Instruction.PRINT.ordinal());
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
