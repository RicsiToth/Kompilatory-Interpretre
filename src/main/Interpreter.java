package main;

import turtle.Turtle;

public class Interpreter {
	
	private LexicalAnalyzator lexicalAnalyzator;
	private Turtle turtle;
	
	public Interpreter(LexicalAnalyzator lexicalAnalyzator, Turtle turtle) {
		this.lexicalAnalyzator = lexicalAnalyzator;
		this.lexicalAnalyzator.scan();
		this.turtle = turtle;
	}
	
	public void interpret() {
		try {
			while (lexicalAnalyzator.getKind() != Kind.NOTHING) {
				switch (lexicalAnalyzator.getKind()) {
					case WORD:
						interpretWord();
						break;
					case NUMBER:
						interpretNumber();
						break;
					case SPECIAL:
						if(lexicalAnalyzator.getToken().equals("]")){
							return;
						}
					default:
						return;
				}
				lexicalAnalyzator.scan();
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e);
		}
	}

	private void interpretNumber() {
		Integer count = Integer.valueOf(lexicalAnalyzator.getToken());
		lexicalAnalyzator.scan();
		switch (lexicalAnalyzator.getToken()) {
			case "*":
				iterate(count);
				break;
		}
	}

	private void interpretWord() {
		switch (lexicalAnalyzator.getToken()) {
			case "dopredu":
			case "dp":
				lexicalAnalyzator.scan();
				check(Kind.NUMBER);
				turtle.forward(Double.valueOf(lexicalAnalyzator.getToken()));
				break;
			case "vlavo":
			case "vl":
				lexicalAnalyzator.scan();
				check(Kind.NUMBER);
				turtle.turnLeft(Double.valueOf(lexicalAnalyzator.getToken()));
				break;
			case "vpravo":
			case "vp":
				lexicalAnalyzator.scan();
				check(Kind.NUMBER);
				turtle.turnRight(Double.valueOf(lexicalAnalyzator.getToken()));
				break;
			case "opakuj":
			case "op":
				lexicalAnalyzator.scan();
				check(Kind.NUMBER);
				iterate(Integer.valueOf(lexicalAnalyzator.getToken()));
				break;
			case "zmaz":
				turtle.clean();
				break;
			case "farba":
				lexicalAnalyzator.scan();
				check(Kind.NUMBER);
				int red = Integer.valueOf(lexicalAnalyzator.getToken());
		
				lexicalAnalyzator.scan();
				check(Kind.NUMBER);
				int green = Integer.valueOf(lexicalAnalyzator.getToken());
				
				lexicalAnalyzator.scan();
				check(Kind.NUMBER);
				int blue = Integer.valueOf(lexicalAnalyzator.getToken());
				
				turtle.setStroke(red, green, blue);
				break;
			case "bod":
				lexicalAnalyzator.scan();
				check(Kind.NUMBER);
				turtle.drawDot(Integer.valueOf(lexicalAnalyzator.getToken()));
				break;
			case "generuj":
				lexicalAnalyzator.scan();
				String commands;
				check(Kind.WORD);
				commands = lexicalAnalyzator.getToken();

				lexicalAnalyzator.scan();
				check(Kind.NUMBER);
				int	angle = Integer.valueOf(lexicalAnalyzator.getToken());

				lexicalAnalyzator.scan();
				check(Kind.NUMBER);
				double length = Double.valueOf(lexicalAnalyzator.getToken());

				lexicalAnalyzator.scan();
				check(Kind.NUMBER);
				double change =  Double.valueOf(lexicalAnalyzator.getToken());

				String generatedCode = generate(commands, angle, length, change);
				System.out.println(generatedCode);
		}
	}
	
	private void iterate(int count) {
		lexicalAnalyzator.scan();
		if(lexicalAnalyzator.getToken().equals("[")) {
			lexicalAnalyzator.scan();
			int start = lexicalAnalyzator.getPosition();
			for(int i = 0; i < count; i++) {
				lexicalAnalyzator.rollbackInputParser(start);
				interpret();
			}
			while(!lexicalAnalyzator.getToken().equals("]")){
				lexicalAnalyzator.scan();
			}
		} else {
			throw new IllegalArgumentException("Expected SYMBOL '[' but got " + lexicalAnalyzator.getKind()
					+ " with value '" + lexicalAnalyzator.getToken() + "'in position " + lexicalAnalyzator.getPosition());
		}
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
	
	private void check(Kind kind) {
		if (lexicalAnalyzator.getKind() != kind) {
			throw new IllegalArgumentException("Expected " + kind + " but got " + lexicalAnalyzator.getKind()
			+ " with value '" + lexicalAnalyzator.getToken() + "'in position " + lexicalAnalyzator.getPosition());
		}
	}

}
