package main;

import com.sun.xml.internal.ws.util.StringUtils;

public class Compiler {
	private LexicalAnalyztor lexicalAnalyztor;
	private VirtualMachine virtualMachine;
	private int addr = 0;
	
	public Compiler(LexicalAnalyztor lexicalAnalyztor, VirtualMachine virtualMachine) {
		this.lexicalAnalyztor = lexicalAnalyztor;
		this.lexicalAnalyztor.scan();
		this.virtualMachine = virtualMachine;
	}
	
	public void compile(int endOfMem) {
		try {
			while (lexicalAnalyztor.getKind() != Kind.NOTHING) {
				switch (lexicalAnalyztor.getKind()) {
					case WORD:
						compileWord(endOfMem);
						break;
					case NUMBER:
						compileNumber(endOfMem);
						break;
					default:
						return;
				}
				lexicalAnalyztor.scan();
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e);
		}
	}

	private void compileNumber(int endOfMem) {
		Integer count = Integer.valueOf(lexicalAnalyztor.getToken());
		lexicalAnalyztor.scan();
		switch (lexicalAnalyztor.getToken()) {
			case "*":
				iterate(count, endOfMem);
				break;
		}
	}

	private void compileWord(int endOfMem) {
		switch (lexicalAnalyztor.getToken()) {
			case "dopredu":
			case "dp":
				lexicalAnalyztor.scan();
				check(Kind.NUMBER, "");
				addr = virtualMachine.setMemValue(addr, Instruction.FD.ordinal());
				addr = virtualMachine.setMemValue(addr, Integer.valueOf(lexicalAnalyztor.getToken()));
				break;
			case "vlavo":
			case "vl":
				lexicalAnalyztor.scan();
				check(Kind.NUMBER, "");
				addr = virtualMachine.setMemValue(addr, Instruction.LT.ordinal());
				addr = virtualMachine.setMemValue(addr, Integer.valueOf(lexicalAnalyztor.getToken()));
				break;
			case "vpravo":
			case "vp":
				lexicalAnalyztor.scan();
				check(Kind.NUMBER, "");
				addr = virtualMachine.setMemValue(addr, Instruction.RT.ordinal());
				addr = virtualMachine.setMemValue(addr, Integer.valueOf(lexicalAnalyztor.getToken()));
				break;
			case "opakuj":
			case "op":
				lexicalAnalyztor.scan();
				check(Kind.NUMBER, "");
				iterate(Integer.valueOf(lexicalAnalyztor.getToken()), endOfMem);
				break;
			case "zmaz":
				lexicalAnalyztor.scan();
				addr = virtualMachine.setMemValue(addr, Instruction.CLEAR.ordinal());
				break;
			case "farba":
				lexicalAnalyztor.scan();
				check(Kind.NUMBER, "");
				int red = Integer.valueOf(lexicalAnalyztor.getToken());
		
				lexicalAnalyztor.scan();
				check(Kind.NUMBER, "");
				int green = Integer.valueOf(lexicalAnalyztor.getToken());
				
				lexicalAnalyztor.scan();
				check(Kind.NUMBER, "");
				int blue = Integer.valueOf(lexicalAnalyztor.getToken());

				addr = virtualMachine.setMemValue(addr, Instruction.COLOR.ordinal());
				addr = virtualMachine.setMemValue(addr, red);
				addr = virtualMachine.setMemValue(addr, green);
				addr = virtualMachine.setMemValue(addr, blue);
				break;
			case "bod":
				lexicalAnalyztor.scan();
				check(Kind.NUMBER, "");
				addr = virtualMachine.setMemValue(addr, Instruction.DOT.ordinal());
				addr = virtualMachine.setMemValue(addr, Integer.valueOf(lexicalAnalyztor.getToken()));
				break;
			case "generuj":
				lexicalAnalyztor.scan();
				String commands;
				check(Kind.WORD, "");
				commands = lexicalAnalyztor.getToken();

				lexicalAnalyztor.scan();
				check(Kind.NUMBER, "");
				int	angle = Integer.valueOf(lexicalAnalyztor.getToken());

				lexicalAnalyztor.scan();
				check(Kind.NUMBER, "");
				double length = Double.valueOf(lexicalAnalyztor.getToken());

				lexicalAnalyztor.scan();
				check(Kind.NUMBER, "");
				double change =  Double.valueOf(lexicalAnalyztor.getToken());

				//String generatedCode = generate(commands, angle, length, change);
				//System.out.println(generatedCode);
		}
	}
	
	private void iterate(int count, int endOfMem) {
		lexicalAnalyztor.scan();
		check(Kind.SPECIAL, "[");
		lexicalAnalyztor.scan();
		addr = virtualMachine.setMemValue(addr, Instruction.SET.ordinal());
		addr = virtualMachine.setMemValue(addr, endOfMem);
		addr = virtualMachine.setMemValue(addr, count);
		int bodyAddr = addr;
		compile(endOfMem - 1);
		check(Kind.SPECIAL, "]");
		addr = virtualMachine.setMemValue(addr, Instruction.LOOP.ordinal());
		addr = virtualMachine.setMemValue(addr, endOfMem);
		addr = virtualMachine.setMemValue(addr, bodyAddr);
	}

	private String generate(String commands, double angle, double length, double change) {
		StringBuffer sb = new StringBuffer();
		if(length < 1) {
			return "";
		}
		for(char command : commands.toCharArray()) {
			switch (command) {
				case 'd':
					sb.append("dp " + length + " ");
					break;
				case 'p':
					sb.append("vp " + angle + " ");
					break;
				case 'l':
					sb.append("vl " + angle + " ");
					break;
				case '*':
					sb.append(generate(commands, angle, length * change, change));
					break;
				default:
					break;
			}
		}
		return sb.toString();
	}
	
	private void check(Kind kind, String value) {
		if (lexicalAnalyztor.getKind() != kind) {
			throw new IllegalArgumentException("Expected " + kind + " but got " + lexicalAnalyztor.getKind()
			+ " with value '" + lexicalAnalyztor.getToken() + "'in position " + lexicalAnalyztor.getPosition());
		}
		if(kind == Kind.SPECIAL && !value.isEmpty()){
			if(!value.equals("]") && !value.equals("[")) {
				throw new IllegalArgumentException("Expected opening or closing bracket but got " + lexicalAnalyztor.getKind()
						+ " with value '" + lexicalAnalyztor.getToken() + "'in position " + lexicalAnalyztor.getPosition());
			}
		}

	}
}
