package sample;

public class Operators {
    /*
    * PARAMETERS:
    *       operatorName --- name of each unique operator
    *       operatorCount --- count the appearances of the operator
     */

    private String operatorName;
    private int operatorCount;

    Operators(String str) {
        operatorName = str;
        operatorCount = 1;
    }

    String getOperatorName() {
        return this.operatorName;
    }

    int getOperatorCount() {
        return this.operatorCount;
    }

    void addOperatorCount() {
        this.operatorCount++;
    }
}
