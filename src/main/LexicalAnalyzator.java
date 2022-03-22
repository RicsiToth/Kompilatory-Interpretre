package main;

public class LexicalAnalyzator {
	private InputParser inputParser;
	private Kind kind;
	private StringBuffer token;
	private int position;
	private static char TERMINATE = '\0';
	
	public LexicalAnalyzator(InputParser inputParser) {
		this.inputParser = inputParser;
		this.inputParser.next();
	}
	
	public void scan() {
		this.token = new StringBuffer();
		while(inputParser.getLook() != TERMINATE && Character.isWhitespace(inputParser.getLook())) {
			inputParser.next();
		}
		
		if(inputParser.getLook() == TERMINATE) {
			kind = Kind.NOTHING;
			return;
		}
		
		position = inputParser.getIndex() - 1;
		kind = decideKind();

		do {
			token.append(inputParser.getLook());
			inputParser.next();
		} while(inputParser.getLook() != TERMINATE && !Character.isWhitespace(inputParser.getLook()));
	}
	
	public String getToken() {
		return token.toString();
	}
	
	public Kind getKind() {
		return kind;
	}
	
	public int getPosition() {
		return position;
	}
	
	public void rollbackInputParser(int index) {
		inputParser.rollbackTo(index);
		scan();
	}
	
	private boolean isTokenNumber() {
		try {
			Integer.parseInt(token.toString());
		} catch (NumberFormatException e) {
			try {
				Double.parseDouble(token.toString());
			} catch (NumberFormatException ex) {
				return false;
			}
		}
		return true;
	}

	private Kind decideKind() {
		if (Character.isAlphabetic(inputParser.getLook())) {
			return Kind.WORD;
		} else if (Character.isDigit(inputParser.getLook())) {
			return Kind.NUMBER;
		} else {
			return Kind.SPECIAL;
		}
	}
}
