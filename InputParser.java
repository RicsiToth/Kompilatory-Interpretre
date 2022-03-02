package main;

public class InputParser {
	private static char TERMINATE = '\0';
	private char look;
	private char[] input;
	private int index = 0;
	
	public InputParser(String input) {
		this.input = input.toCharArray();
		this.index = 0;
	}
	
	public void next() {
		if(index >= input.length) {
			look = TERMINATE;
		} else {
			look = input[index];
			index++;
		}
	}
	
	public char getLook() {
		return look;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void rollbackTo(int index) {
		this.index = index;
		next();
	}

}
