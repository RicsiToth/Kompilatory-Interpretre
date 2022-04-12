package tree.identifier;

import main.Instruction;
import main.VirtualMachine;
import tree.Syntax;

public class Variable extends Identifier {

	protected int position;
	
	public Variable(String name, int position) {
		super(name);
		this.position = position;
	}

	@Override
	public void execute(VirtualMachine vm) {
		// TODO Auto-generated method stub

	}

	@Override
	public void generate(VirtualMachine vm) {
	}
	
	public void generateSet(VirtualMachine vm) {
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

	public void setPosition(int position) {
		this.position = position;
	}

	public int getPosition() {
		return position;
	}
}
