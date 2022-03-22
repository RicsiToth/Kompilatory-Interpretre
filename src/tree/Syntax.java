package tree;

import main.VirtualMachine;

public interface Syntax {
    void execute(VirtualMachine vm);
    void generate(VirtualMachine vm);
    void translate(int indent);
    int evaluate();
}
