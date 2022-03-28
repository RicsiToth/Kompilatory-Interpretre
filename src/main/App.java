package main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import tree.*;
import tree.turtle.Fd;
import tree.turtle.Rt;
import turtle.Turtle;

public class App extends Application {

	private static void turtleUlohy(Turtle turtle) {
		turtle.drawSquare(50);
		//turtle.drawTriangle(100);
		//Fraktal zo zadania uhol musi byt pre turtle 0
		// turtle.draw("dl*pp*lz", 45, 100, 0.5);
		//Sierpinskeho trojuholnik pociatocny uhol musi byt 90
		//turtle.draw("*d*dpd*dpdd", 120, 225, 0.5);
	}

	private static void uloha2cv3(VirtualMachine vm) {
    	for(int i = 0; i < 4; i++) {
	    	vm.setMemValue(Instruction.FD.ordinal());
	    	vm.setMemValue(100);
	    	vm.setMemValue(Instruction.RT.ordinal());
	    	vm.setMemValue(90);
    	}
	}
	
	private static void uloha3cv3(VirtualMachine vm) {
		vm.setMemValue(Instruction.SET.ordinal());
    	vm.setMemValue(99);
    	vm.setMemValue(10);
    	int body_addr1 = vm.getCurrentAddr();
    	
    	vm.setMemValue(Instruction.SET.ordinal());
    	vm.setMemValue(98);
    	vm.setMemValue(4);
    	int body_addr2 = vm.getCurrentAddr();
    	vm.setMemValue(Instruction.FD.ordinal());
    	vm.setMemValue(20);
    	vm.setMemValue(Instruction.RT.ordinal());
    	vm.setMemValue(90);
		vm.setMemValue(Instruction.LOOP.ordinal());
    	vm.setMemValue(98);
    	vm.setMemValue(body_addr2);
    	
    	vm.setMemValue(Instruction.RT.ordinal());
    	vm.setMemValue(90);
    	vm.setMemValue(Instruction.FD.ordinal());
    	vm.setMemValue(20);
    	vm.setMemValue(Instruction.LT.ordinal());
    	vm.setMemValue(90);
    	vm.setMemValue(Instruction.LOOP.ordinal());
    	vm.setMemValue(99);
    	vm.setMemValue(body_addr1);
	}
	
	private static void uloha4cv3(VirtualMachine vm) {
		vm.setMemValue(Instruction.SET.ordinal());
		vm.setMemValue(4);
		vm.setMemValue(100);

		vm.setMemValue(Instruction.FD.ordinal());
		vm.setMemValue(100);
		vm.setMemValue(Instruction.RT.ordinal());
		vm.setMemValue(45);

		vm.setMemValue(Instruction.LOOP.ordinal());
		vm.setMemValue(4);
		vm.setMemValue(3);
	}
	
	private static void uloha6cv3(VirtualMachine vm) {
		vm.setMemValue(Instruction.SET.ordinal());
    	vm.setMemValue(99);
    	vm.setMemValue(10000000);
    	int body_addr = vm.getCurrentAddr();
    	vm.setMemValue(Instruction.LOOP.ordinal());
    	vm.setMemValue(99);
    	vm.setMemValue(body_addr);
	}

	private static void interpreterSpeedTest(Turtle turtle) {
		InputParser inputParser = new InputParser("op 10000000 [ ]");
		LexicalAnalyzator lexicalAnalyzator = new LexicalAnalyzator(inputParser);
		Interpreter interpreter = new Interpreter(lexicalAnalyzator, turtle);
    	long startTime = System.nanoTime();
    	interpreter.interpret();
    	long endTime = System.nanoTime();
		System.out.println("Interpreter time: " + (endTime - startTime) / 1000000 + " ms");
	}

	private static void compilerSpeedTest(Turtle turtle) {
		InputParser inputParser = new InputParser("op 10000000 [ ]");
		LexicalAnalyzator lexicalAnalyzator = new LexicalAnalyzator(inputParser);
		VirtualMachine vm = new VirtualMachine(turtle, 100);
		Compiler compiler = new Compiler(lexicalAnalyzator, vm);
		compiler.compile(99);
		vm.reset();
		long startTime = System.nanoTime();
		while(!vm.isTerminated()) {
			vm.execute();
		}
		long endTime = System.nanoTime();
		System.out.println("Virtual machine time: " + (endTime - startTime) / 1000000 + " ms");
	}

	private static void javaSpeedTest() {
		long startTime = System.nanoTime();
		for(int i = 0; i < 10000000; i++);
		long endTime = System.nanoTime();
		System.out.println("Java time: " + (endTime - startTime) / 1000000 + " ms");
	}

	private static void lexicalTreeSpeedTest(Turtle turtle) {
		InputParser inputParser = new InputParser("op 10000000 [ ]");
		LexicalAnalyzator lexicalAnalyzator = new LexicalAnalyzator(inputParser);
		VirtualMachine vm = new VirtualMachine(turtle, 100);
		TreeParser parser = new TreeParser(lexicalAnalyzator, vm);
		Syntax tree = parser.parse();
		long startTime = System.nanoTime();
		tree.execute(vm);
		long endTime = System.nanoTime();
		System.out.println("Lexical tree time: " + (endTime - startTime) / 1000000 + " ms");
	}

	private static void lexicalTreeProgram(Turtle turtle) {
		VirtualMachine vm = new VirtualMachine(turtle, 100);
		Syntax tree = new Repeat(new Constant(4),
				new Block(new Fd(new Constant(100)), new Rt(new Constant(90))));
		tree.execute(vm);
	}


    public void start(Stage stage) {
        Group group = new Group();
        // create a scene
        Scene scene = new Scene(group, 600, 400);
        Canvas canvas = new Canvas(600, 400);
        group.getChildren().add(canvas);
        // set the scene
        stage.setScene(scene);

        Turtle turtle = new Turtle(canvas, 300, 200, 0);
		//turtleUlohy(turtle);


        //InputParser inputParser = new InputParser("op 4 [dp 100 vp 90]");
		//vykresli L
		//InputParser inputParser = new InputParser("vp 90 dp 50 vl 180 dp 50 vp 90 dp 100");
		//Vnorene cykly
		//InputParser inputParser = new InputParser("op 10 [ op 4 [ dp 20 vp 90 ] vp 90 dp 20 vl 90 ]");
        //InputParser inputParser = new InputParser("op 10000000 [ ]");
        //InputParser inputParser = new InputParser("farba 255 0 0 4 * [dp 100 vp 90]");
        //InputParser inputParser = new InputParser("generuj dl*pp*lz 45 100 0.5");
       	//InputParser inputParser = new InputParser("op 4 [dp 100 op 0 [vl 90] vp 90]");

		//InputParser inputParser = new InputParser("sqrt | - 2 ^ 3 * 2 |");
		//InputParser inputParser = new InputParser("a = 123 vypis a");
		//InputParser inputParser = new InputParser("a = 123 b = 12 c = a a = b b = c vypis a vypis b");
        //InputParser inputParser = new InputParser("a = 10 a = a * a vypis a");
        InputParser inputParser = new InputParser("n = 3 opakuj 2 [ opakuj n [ dopredu 300 / n vpravo 360 / n ] n = n + 1 ]");
    	LexicalAnalyzator lexicalAnalyzator = new LexicalAnalyzator(inputParser);
/*
		interpreterSpeedTest(turtle);
		compilerSpeedTest(turtle);
		javaSpeedTest();
		lexicalTreeSpeedTest(turtle);
/*
		VirtualMachine vm = new VirtualMachine(turtle, 100);
		TreeParser parser = new TreeParser(lexicalAnalyzator);
		Syntax tree = parser.parse();
		tree.execute(vm);
		tree.translate(0);
		//Compiler compiler = new Compiler(lexicalAnalyztor, vm);
    	//uloha2cv3(vm);
    	//uloha3cv3(vm);
    	//uloha4cv3(vm);
    	//uloha6cv3(vm);
    	//compiler.compile(99);

		//vm.disassemble();
    	/*
    	vm.reset(0);
    	while(!vm.isTerminated()) {
    		vm.execute();
    	}*/

		//lexicalTreeProgram(turtle);

		//ExpressionInterpreter expressionInterpreter = new ExpressionInterpreter(lexicalAnalyzator);
		//TreeParser treeParser = new TreeParser(lexicalAnalyzator);
		//Syntax expression = treeParser.parseExpression();
		//System.out.println(expression.evaluate());
		
		VirtualMachine vm = new VirtualMachine(turtle, 1000);
		TreeParser treeParser = new TreeParser(lexicalAnalyzator, vm);
		Syntax program = treeParser.parse();
		vm.initMemForVariables();
		program.generate(vm);
		
		vm.reset();
    	while(!vm.isTerminated()) {
    		vm.execute();
    	}
		
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
