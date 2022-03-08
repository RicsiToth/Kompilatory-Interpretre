package main;

import turtle.Turtle;

public class Interpreter {
	
	private LexicalAnalyztor lexicalAnalyztor;
	private Turtle turtle;
	
	public Interpreter(LexicalAnalyztor lexicalAnalyztor, Turtle turtle) {
		this.lexicalAnalyztor = lexicalAnalyztor;
		this.lexicalAnalyztor.scan();
		this.turtle = turtle;
	}
	
	public void interpret() {
		try {
			while (lexicalAnalyztor.getKind() != Kind.NOTHING) {
				switch (lexicalAnalyztor.getKind()) {
					case WORD:
						interpretWord();
						break;
					case NUMBER:
						interpretNumber();
						break;
					case SPECIAL:
						if(lexicalAnalyztor.getToken().equals("]")){
							return;
						}
					default:
						return;
				}
				lexicalAnalyztor.scan();
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e);
		}
	}

	private void interpretNumber() {
		Integer count = Integer.valueOf(lexicalAnalyztor.getToken());
		lexicalAnalyztor.scan();
		switch (lexicalAnalyztor.getToken()) {
			case "*":
				iterate(count);
				break;
		}
	}

	private void interpretWord() {
		switch (lexicalAnalyztor.getToken()) {
			case "dopredu":
			case "dp":
				lexicalAnalyztor.scan();
				check(Kind.NUMBER);
				turtle.forward(Double.valueOf(lexicalAnalyztor.getToken()));
				break;
			case "vlavo":
			case "vl":
				lexicalAnalyztor.scan();
				check(Kind.NUMBER);
				turtle.turnLeft(Double.valueOf(lexicalAnalyztor.getToken()));
				break;
			case "vpravo":
			case "vp":
				lexicalAnalyztor.scan();
				check(Kind.NUMBER);
				turtle.turnRight(Double.valueOf(lexicalAnalyztor.getToken()));
				break;
			case "opakuj":
			case "op":
				lexicalAnalyztor.scan();
				check(Kind.NUMBER);
				iterate(Integer.valueOf(lexicalAnalyztor.getToken()));
				break;
			case "zmaz":
				turtle.clean();
				break;
			case "farba":
				lexicalAnalyztor.scan();
				check(Kind.NUMBER);
				int red = Integer.valueOf(lexicalAnalyztor.getToken());
		
				lexicalAnalyztor.scan();
				check(Kind.NUMBER);
				int green = Integer.valueOf(lexicalAnalyztor.getToken());
				
				lexicalAnalyztor.scan();
				check(Kind.NUMBER);
				int blue = Integer.valueOf(lexicalAnalyztor.getToken());
				
				turtle.setStroke(red, green, blue);
				break;
			case "bod":
				lexicalAnalyztor.scan();
				check(Kind.NUMBER);
				turtle.drawDot(Integer.valueOf(lexicalAnalyztor.getToken()));
				break;
			case "generuj":
				lexicalAnalyztor.scan();
				String commands;
				check(Kind.WORD);
				commands = lexicalAnalyztor.getToken();

				lexicalAnalyztor.scan();
				check(Kind.NUMBER);
				int	angle = Integer.valueOf(lexicalAnalyztor.getToken());

				lexicalAnalyztor.scan();
				check(Kind.NUMBER);
				double length = Double.valueOf(lexicalAnalyztor.getToken());

				lexicalAnalyztor.scan();
				check(Kind.NUMBER);
				double change =  Double.valueOf(lexicalAnalyztor.getToken());

				String generatedCode = generate(commands, angle, length, change);
				System.out.println(generatedCode);
		}
	}
	
	private void iterate(int count) {
		lexicalAnalyztor.scan();
		if(lexicalAnalyztor.getToken().equals("[")) {
			lexicalAnalyztor.scan();
			int start = lexicalAnalyztor.getPosition();
			for(int i = 0; i < count; i++) {
				lexicalAnalyztor.rollbackInputParser(start);
				interpret();
			}
			while(!lexicalAnalyztor.getToken().equals("]")){
				lexicalAnalyztor.scan();
			}
		} else {
			throw new IllegalArgumentException("Expected SYMBOL '[' but got " + lexicalAnalyztor.getKind()
					+ " with value '" + lexicalAnalyztor.getToken() + "'in position " + lexicalAnalyztor.getPosition());
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
		if (lexicalAnalyztor.getKind() != kind) {
			throw new IllegalArgumentException("Expected " + kind + " but got " + lexicalAnalyztor.getKind()
			+ " with value '" + lexicalAnalyztor.getToken() + "'in position " + lexicalAnalyztor.getPosition());
		}
	}

}
