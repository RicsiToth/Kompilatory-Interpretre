package tree;

import main.VirtualMachine;

public final class Constant implements Syntax {

    private int value;

    public Constant(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public void execute(VirtualMachine vm) {
        vm.setMemValue(value);
    }

    @Override
    public void generate(VirtualMachine vm) {
        vm.setMemValue(value);
    }

    @Override
    public void translate(int indent) {
        System.out.print(value);
    }
}
