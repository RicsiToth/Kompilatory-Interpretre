package main;

import java.util.HashMap;
import java.util.Map;

import tree.identifier.Identifier;
import tree.identifier.Subroutine;
import tree.identifier.Variable;
import turtle.Turtle;

public class VirtualMachine {
	private int pc = 0;
	private int addr = 0;
	private int top;
	private int frame;
	private boolean terminated = false;
	private int[] mem;
	private final Map<String, Identifier> globals = new HashMap<>();
	private Map<String, Variable> locals = null;
	private int globalVariableAddr = 2;
	private int localVariableDelta;

	private Turtle turtle;
	
	public VirtualMachine(Turtle turtle, int length) {
		this.turtle = turtle;
		mem = new int[length];
		top = length;
		frame = top;
	}

	public void reset() {
		pc = 0;
		terminated = false;
		top = mem.length;
		frame = top;
	}
	
	public boolean isTerminated() {
		return terminated;
	}
	
	public void setMemValue(int value) {
		mem[addr] = value;
		addr++;
	}

	public void setMemValueToAddr(int addr, int value) {
		mem[addr] = value;
	}
	
	public void initMemForVariables() {
		setMemValue(Instruction.JUMP.ordinal());
		setMemValue(globalVariableAddr);
		addr = globalVariableAddr;
	}

	public Map<String, Identifier> getGlobals() {
		return globals;
	}

	public Identifier getGlobalIdentifier(String name) {
		return globals.get(name);
	}

	public Map<String, Variable> getLocals() {
		return locals;
	}

	public Variable getLocalVariable(String name) {
		return locals.get(name);
	}

	public void addGlobal(String name, Identifier identifier) {
		globals.put(name, identifier);
	}

	public void setLocals(Map<String, Variable> locals) {
		this.locals = locals;
	}

	public int getGlobalVariableAddr() {
		return globalVariableAddr;
	}

	public void setGlobalVariableAddr(int globalVariableAddr) {
		this.globalVariableAddr = globalVariableAddr;
	}

	public int getLocalVariableDelta() {
		return localVariableDelta;
	}

	public void setLocalVariableDelta(int localVariableDelta) {
		this.localVariableDelta = localVariableDelta;
	}

	public int getCurrentAddr() {
		return addr;
	}

	public Turtle getTurtle() {
		return turtle;
	}
	
	public void execute() {
		int index;
		switch(Instruction.getInstruction(mem[pc])) {
			case PUSH:
				pc++;
				top--;
				mem[top] = mem[pc];
				pc++;
				break;
			case AND:
				pc++;
				mem[top + 1] *= mem[top];
				mem[top] = 0;
				top++;
				break;
			case OR:
				pc++;
				mem[top + 1] = (mem[top + 1] + mem[top]) % 2;
				mem[top] = 0;
				top++;
				break;
			case NOT:
				pc++;
				mem[top] = (mem[top] + 1) % 2;
				break;
			case LOWER:
				pc++;
				mem[top + 1] = mem[top + 1] < mem[top] ? 1 : 0;
				mem[top] = 0;
				top++;
				break;
			case LOWER_EQUAL:
				pc++;
				mem[top + 1] = mem[top + 1] <= mem[top] ? 1 : 0;
				mem[top] = 0;
				top++;
				break;
			case EQUAL:
				pc++;
				mem[top + 1] = mem[top + 1] == mem[top] ? 1 : 0;
				mem[top] = 0;
				top++;
				break;
			case DIFFERENT:
				pc++;
				mem[top + 1] = mem[top + 1] != mem[top] ? 1 : 0;
				mem[top] = 0;
				top++;
				break;
			case GREATER:
				pc++;
				mem[top + 1] = mem[top + 1] > mem[top] ? 1 : 0;
				mem[top] = 0;
				top++;
				break;
			case GREATER_EQUAL:
				pc++;
				mem[top + 1] = mem[top + 1] >= mem[top] ? 1 : 0;
				mem[top] = 0;
				top++;
				break;
			case MINUS:
				pc++;
				mem[top] *= -1;
				break;
			case ADD:
				pc++;
				mem[top + 1] += mem[top];
				mem[top] = 0;
				top++;
				break;
			case SUB:
				pc++;
				mem[top + 1] -= mem[top];
				mem[top] = 0;
				top++;
				break;
			case MUL:
				pc++;
				mem[top + 1] *= mem[top];
				mem[top] = 0;
				top++;
				break;
			case DIV:
				pc++;
				mem[top + 1] /= mem[top];
				mem[top] = 0;
				top++;
				break;
			case GET:
				pc++;
				index = mem[pc];
				pc++;
				top--;
				mem[top] = mem[index];
				break;
			case SET:
				pc++;
				//Address where the counter goes
				index = mem[pc];
				pc++;
				//Setting the counter to the address
				mem[index] = mem[top];
				mem[top] = 0;
				top++;
				break;	
			case PRINT:
				pc++;
				System.out.println(mem[top]);
				mem[top] = 0;
				top++;
				break;
			case JUMP:
				pc = mem[pc + 1];
				break;
			case FD:
				pc++;
				turtle.forward(mem[top]);
				mem[top] = 0;
				top++;
				break;
			case LT:
				pc++;
				turtle.turnLeft(mem[top]);
				mem[top] = 0;
				top++;
				break;
			case RT:
				pc++;
				turtle.turnRight(mem[top]);
				mem[top] = 0;
				top++;
				break;
			case LOOP:
				pc++;
				//Get the address where the counter is
				mem[top]--;
				if(mem[top] > 0) {
					pc = mem[pc];
				} else {
					mem[top] = 0;
					pc++;
					top++;
				}
				break;
			case DOT:
				pc++;
				turtle.drawDot(mem[pc]);
				pc++;
				break;
			case CLEAR:
				turtle.clean();
				pc++;
				break;
			case COLOR:
				pc++;
				int red = mem[pc];
				pc++;
				int green = mem[pc];
				pc++;
				int blue = mem[pc];
				turtle.setStroke(red, green, blue);
				pc++;
				break;
			case JUMP_IF_FALSE:
				pc++;
				if(mem[top] == 0) {
					pc = mem[pc];
				} else {
					pc++;
				}
				mem[top] = 0;
				top++;
				break;
			case CALL:
				pc++;
				top--;
				mem[top] = pc + 1;
				top--;
				mem[top] = frame;
				frame = top;
				pc = mem[pc];
				break;
			case RETURN:
				pc++;
				top = frame + 2 + mem[pc];
				pc = mem[frame + 1];
				frame = mem[frame];
				break;
			case GET_LOCAL:
				pc++;
				index = frame + mem[pc];
				pc++;
				top--;
				mem[top] = mem[index];
				break;
			case SET_LOCAL:
				pc++;
				index = frame + mem[pc];
				pc++;
				mem[index] = mem[top];
				top++;
				break;
			default:
				terminated = true;
				return;
		}
	}
	
	public void disassemble() {
		reset();
		int index;
		while(!terminated) {
			int prevPc = pc;
			switch(Instruction.getInstruction(mem[pc])) {
			case FD:
				pc++;
				System.out.println(prevPc + "   FD   " + mem[pc]);
				pc++;
				break;
			case LT:
				pc++;
				System.out.println(prevPc + "   LT   " + mem[pc]);
				pc++;
				break;
			case RT:
				pc++;
				System.out.println(prevPc + "   RT   " + mem[pc]);
				pc++;
				break;
			case SET:
				pc++;
				//Address where the counter goes
				index = mem[pc];
				pc++;
				//Setting the counter to the address
				System.out.println(prevPc + "   SET   [" + index  + "], "+ mem[pc]);
				pc++;
				break;
			case LOOP:
				pc++;
				//Get the address where the counter is
				index = mem[pc];
				pc++;
				System.out.println(prevPc + "   LOOP   [" + index  + "], "+ mem[pc]);
				pc++;
				break;
			case DOT:
				pc++;
				System.out.println(prevPc + "   DOT   " + mem[pc]);
				pc++;
				break;
			case CLEAR:
				System.out.println(prevPc + "   CLEAR");
				pc++;
				break;
			case COLOR:
				pc++;
				int red = mem[pc];
				pc++;
				int green = mem[pc];
				pc++;
				int blue = mem[pc];
				System.out.println(prevPc + "   COLOR   " + red + ", " + green + ", " + blue);
				pc++;
				break;
			default:
				terminated = true;
				break;
			}	
		}
		reset();
	}
	
}
