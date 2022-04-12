package tree;

import main.VirtualMachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Block implements Syntax {

    private final List<Syntax> instructions;

    public Block() {
        this.instructions = new ArrayList<>();
    }

    public Block(Syntax... instructions) {
        this.instructions = new ArrayList<>(Arrays.asList(instructions));
    }

    public void add(Syntax syntax) {
        instructions.add(syntax);
    }

    @Override
    public void execute(VirtualMachine vm) {
        instructions.forEach(instruction -> instruction.execute(vm));
    }

    @Override
    public void generate(VirtualMachine vm) {
        instructions.forEach(instruction -> instruction.generate(vm));
    }

    @Override
    public void translate(int indent) {
        instructions.forEach(instruction -> instruction.translate(indent));
    }

    @Override
    public int evaluate() {
        return 0;
    }

    public int getSize() {
        return instructions.size();
    }
}
