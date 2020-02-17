package sample;

public class Operands {
    /*
     * PARAMETERS:
     *       operandName --- name of each unique operator
     *       operandCount --- count the appearances of the operator
     */

    private String operandName;
    private int operandCount;

    Operands(String str) {
        operandName = str;
        operandCount = 1;
    }

    String getOperandName() {
        return this.operandName;
    }

    int getOperandCount() {
        return this.operandCount;
    }

    void addOperandCount(int count) {
        this.operandCount++;
    }
}
