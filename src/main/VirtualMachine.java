package main;

import java.util.HashMap;
import java.util.Map;

import tree.Subroutine;
import turtle.Turtle;

public class VirtualMachine {
	private int pc = 0;
	private int addr = 0;
	private int top;
	private boolean terminated = false;
	private int[] mem;
	private final Map<String, Integer> variables = new HashMap<>();
	private final Map<String, Subroutine> subroutines = new HashMap<>();
	private Turtle turtle;
	
	public VirtualMachine(Turtle turtle, int length) {
		this.turtle = turtle;
		mem = new int[length];
		top = length;
	}

	public void reset() {
		this.pc = 0;
		terminated = false;
		top = mem.length;
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
		setMemValue(2 + getVariablesLength());
		addr += getVariablesLength();
	}
	
	public void addVariable(String name, Integer value) {
		variables.put(name, value);
	}
	
	public Integer getVariable(String name) {
		return variables.get(name);
	}
	
	public int getVariablesLength() {
		return variables.keySet().size();
	}

	public Subroutine getSubroutine(String name) {
		return subroutines.get(name);
	}

	public void addSubroutine(String name, Subroutine subroutine) {
		subroutines.put(name, subroutine);
	}

	/*public int getEndAddr() {
		endAddr--;
		return endAddr + 1;
	}

	public void setEndAddr(int endAddr) {
		this.endAddr = endAddr;
	}*/

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
				pc = mem[pc];
				break;
			case RETURN:
				pc = mem[top];
				mem[top] = 0;
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
