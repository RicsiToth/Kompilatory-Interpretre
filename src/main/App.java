package main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class App extends Application {
	
	private static void uloha2cv3(VirtualMachine vm) {
		int addr = 0;
    	for(int i = 0; i < 4; i++) {
	    	addr = vm.setMemValue(addr, Instruction.FD.ordinal());
	    	addr = vm.setMemValue(addr, 100);
	    	addr = vm.setMemValue(addr, Instruction.RT.ordinal());
	    	addr = vm.setMemValue(addr, 90);
    	}
	}
	
	private static void uloha3cv3(VirtualMachine vm) {
		int addr = 0;
		addr = vm.setMemValue(addr, Instruction.SET.ordinal());
    	addr = vm.setMemValue(addr, 99);
    	addr = vm.setMemValue(addr, 10);
    	int body_addr1 = addr;
    	
    	addr = vm.setMemValue(addr, Instruction.SET.ordinal());
    	addr = vm.setMemValue(addr, 98);
    	addr = vm.setMemValue(addr, 4);
    	int body_addr2 = addr;
    	addr = vm.setMemValue(addr, Instruction.FD.ordinal());
    	addr = vm.setMemValue(addr, 20);
    	addr = vm.setMemValue(addr, Instruction.RT.ordinal());
    	addr = vm.setMemValue(addr, 90);
    	addr = vm.setMemValue(addr, Instruction.LOOP.ordinal());
    	addr = vm.setMemValue(addr, 98);
    	addr = vm.setMemValue(addr, body_addr2);
    	
    	addr = vm.setMemValue(addr, Instruction.RT.ordinal());
    	addr = vm.setMemValue(addr, 90);
    	addr = vm.setMemValue(addr, Instruction.FD.ordinal());
    	addr = vm.setMemValue(addr, 20);
    	addr = vm.setMemValue(addr, Instruction.LT.ordinal());
    	addr = vm.setMemValue(addr, 90);
    	addr = vm.setMemValue(addr, Instruction.LOOP.ordinal());
    	addr = vm.setMemValue(addr, 99);
    	addr = vm.setMemValue(addr, body_addr1);
	}
	
	private static void uloha4cv3(VirtualMachine vm) {
		int addr = 0;
		for(int i = 100; i > 0; i--) {
			addr = vm.setMemValue(addr, Instruction.FD.ordinal());
	    	addr = vm.setMemValue(addr, i);
	    	addr = vm.setMemValue(addr, Instruction.RT.ordinal());
	    	addr = vm.setMemValue(addr, 45);
		}
	}
	
	private static void uloha6cv3(VirtualMachine vm) {
		int addr = 0;
		addr = vm.setMemValue(addr, Instruction.SET.ordinal());
    	addr = vm.setMemValue(addr, 99);
    	addr = vm.setMemValue(addr, 10000000);
    	int body_addr = addr;
    	addr = vm.setMemValue(addr, Instruction.LOOP.ordinal());
    	addr = vm.setMemValue(addr, 99);
    	addr = vm.setMemValue(addr, body_addr);
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
     
        //InputParser inputParser = new InputParser("op 4 [dp 100 vp 90]");
        InputParser inputParser = new InputParser("op 10000000 [ ]");
        //InputParser inputParser = new InputParser("farba 255 0 0 4 * [dp 100 vp 90]");
        //InputParser inputParser = new InputParser("generuj dl*pp*lz 45 100 0.5");
       // InputParser inputParser = new InputParser("op 4 [dp 100 op 0 [vl 90] vp 90]");
    	LexicalAnalyztor lexicalAnalyztor = new LexicalAnalyztor(inputParser);
    	Interpreter interpreter = new Interpreter(lexicalAnalyztor, turtle);
    	long startTime1 = System.nanoTime();
    	interpreter.interpret();
    	long endTime1 = System.nanoTime();

    	//turtle.drawSquare(50);
        //turtle.drawTriangle(100);
        //Fraktal zo zadania uhol musi byt pre turtle 0
       // turtle.draw("dl*pp*lz", 45, 100, 0.5);
        //Sierpinskeho trojuholnik pociatocny uhol musi byt 90
        //turtle.draw("*d*dpd*dpdd", 120, 225, 0.5);
    	
    	VirtualMachine vm = new VirtualMachine(turtle, 100);
    	
    	//uloha2cv3(vm);
    	//uloha3cv3(vm);
    	//uloha4cv3(vm);
    	uloha6cv3(vm);
    	vm.disassemble();
    	
    	vm.reset(0);
    	long startTime2 = System.nanoTime();
    	while(!vm.isTerminated()) {
    		vm.execute();
    	}
    	long endTime2 = System.nanoTime();
    	
    	long startTime3 = System.nanoTime();
    	for(int i = 0; i < 10000000; i++);
    	long endTime3 = System.nanoTime();
    	
    	System.out.println("Virtual machine time: " + (endTime2 - startTime2) / 1000000 + " ms");
    	System.out.println("Interpreter time: " + (endTime1 - startTime1) / 1000000 + " ms");
    	System.out.println("Java time: " + (endTime3 - startTime3) / 1000000 + " ms");

    	
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
