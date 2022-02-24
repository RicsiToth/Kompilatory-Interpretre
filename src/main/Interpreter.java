package main;

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
						throw new UnknownCommandException("Command '" + lexicalAnalyztor.getToken()
								+ "' with kind '" + lexicalAnalyztor.getKind() + "' in position "
								+ lexicalAnalyztor.getPosition() + " does not exists!");
				}
				lexicalAnalyztor.scan();
			}
		} catch (UnknownCommandException | IllegalArgumentException e) {
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
				if (lexicalAnalyztor.getKind() == Kind.NUMBER) {
					turtle.forward(Double.valueOf(lexicalAnalyztor.getToken()));
				} else {
					throw new IllegalArgumentException("Expected NUMBER but got " + lexicalAnalyztor.getKind()
							+ " with value '" + lexicalAnalyztor.getToken() + "'in position " + lexicalAnalyztor.getPosition());
				}
				break;
			case "vlavo":
			case "vl":
				lexicalAnalyztor.scan();
				if (lexicalAnalyztor.getKind() == Kind.NUMBER) {
					turtle.turnLeft(Double.valueOf(lexicalAnalyztor.getToken()));
				} else {
					throw new IllegalArgumentException("Expected NUMBER but got " + lexicalAnalyztor.getKind()
							+ " with value '" + lexicalAnalyztor.getToken() + "'in position " + lexicalAnalyztor.getPosition());
				}
				break;
			case "vpravo":
			case "vp":
				lexicalAnalyztor.scan();
				if (lexicalAnalyztor.getKind() == Kind.NUMBER) {
					turtle.turnRight(Double.valueOf(lexicalAnalyztor.getToken()));
				} else {
					throw new IllegalArgumentException("Expected NUMBER but got " + lexicalAnalyztor.getKind()
							+ " with value '" + lexicalAnalyztor.getToken() + "'in position " + lexicalAnalyztor.getPosition());
				}
				break;
			case "opakuj":
			case "op":
				lexicalAnalyztor.scan();
				if (lexicalAnalyztor.getKind() == Kind.NUMBER) {
					iterate(Integer.valueOf(lexicalAnalyztor.getToken()));
				} else {
					throw new IllegalArgumentException("Expected NUMBER but got " + lexicalAnalyztor.getKind()
							+ " with value '" + lexicalAnalyztor.getToken() + "'in position " + lexicalAnalyztor.getPosition());
				}
				break;
			case "zmaz":
				turtle.clean();
				break;
			case "farba":
				lexicalAnalyztor.scan();
				int red;
				if (lexicalAnalyztor.getKind() == Kind.NUMBER) {
					red = Integer.valueOf(lexicalAnalyztor.getToken());
				} else {
					throw new IllegalArgumentException("Expected NUMBER but got " + lexicalAnalyztor.getKind()
							+ " with value '" + lexicalAnalyztor.getToken() + "'in position " + lexicalAnalyztor.getPosition());
				}
				lexicalAnalyztor.scan();
				int green;
				if (lexicalAnalyztor.getKind() == Kind.NUMBER) {
					green = Integer.valueOf(lexicalAnalyztor.getToken());
				} else {
					throw new IllegalArgumentException("Expected NUMBER but got " + lexicalAnalyztor.getKind()
							+ " with value '" + lexicalAnalyztor.getToken() + "'in position " + lexicalAnalyztor.getPosition());
				}
				lexicalAnalyztor.scan();
				int blue;
				if (lexicalAnalyztor.getKind() == Kind.NUMBER) {
					blue = Integer.valueOf(lexicalAnalyztor.getToken());
				} else {
					throw new IllegalArgumentException("Expected NUMBER but got " + lexicalAnalyztor.getKind()
							+ " with value '" + lexicalAnalyztor.getToken() + "'in position " + lexicalAnalyztor.getPosition());
				}
				turtle.setStroke(red, green, blue);
				break;
			case "bod":
				lexicalAnalyztor.scan();
				if (lexicalAnalyztor.getKind() == Kind.NUMBER) {
					turtle.drawDot(Integer.valueOf(lexicalAnalyztor.getToken()));
				} else {
					throw new IllegalArgumentException("Expected NUMBER but got " + lexicalAnalyztor.getKind()
							+ " with value '" + lexicalAnalyztor.getToken() + "'in position " + lexicalAnalyztor.getPosition());
				}
				break;
			case "generuj":

				lexicalAnalyztor.scan();
				String commands;
				if (lexicalAnalyztor.getKind() == Kind.WORD) {
					commands = lexicalAnalyztor.getToken();
				} else {
					throw new IllegalArgumentException("Expected WORD but got " + lexicalAnalyztor.getKind()
							+ " with value '" + lexicalAnalyztor.getToken() + "'in position " + lexicalAnalyztor.getPosition());
				}

				lexicalAnalyztor.scan();
				int angle;
				if (lexicalAnalyztor.getKind() == Kind.NUMBER) {
					angle = Integer.valueOf(lexicalAnalyztor.getToken());
				} else {
					throw new IllegalArgumentException("Expected NUMBER but got " + lexicalAnalyztor.getKind()
							+ " with value '" + lexicalAnalyztor.getToken() + "'in position " + lexicalAnalyztor.getPosition());
				}

				lexicalAnalyztor.scan();
				double length;
				if (lexicalAnalyztor.getKind() == Kind.NUMBER) {
					length = Double.valueOf(lexicalAnalyztor.getToken());
				} else {
					throw new IllegalArgumentException("Expected NUMBER but got " + lexicalAnalyztor.getKind()
							+ " with value '" + lexicalAnalyztor.getToken() + "'in position " + lexicalAnalyztor.getPosition());
				}

				lexicalAnalyztor.scan();
				double change;
				if (lexicalAnalyztor.getKind() == Kind.NUMBER) {
					change =  Double.valueOf(lexicalAnalyztor.getToken());
				} else {
					throw new IllegalArgumentException("Expected NUMBER but got " + lexicalAnalyztor.getKind()
							+ " with value '" + lexicalAnalyztor.getToken() + "'in position " + lexicalAnalyztor.getPosition());
				}

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

}
