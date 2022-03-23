package tree;

import main.VirtualMachine;

public class Assign implements Syntax {
	
	private Variable variable;
	private Syntax expression;
	
	public Assign(Variable variable, Syntax expression) {
		this.variable = variable;
		this.expression = expression;
	}

	@Override
	public void execute(VirtualMachine vm) {
		// TODO Auto-generated method stub

	}

	@Override
	public void generate(VirtualMachine vm) {
		expression.generate(vm);
		variable.generateSet(vm);
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
