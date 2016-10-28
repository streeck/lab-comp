package ast;

/**
 * Created by Angela on 28/10/2016.
 */
public class AssignStatement extends Statement{
    private Expr leftPart;
    private Expr rightPart;
    private LocalVariableList varList;

    public AssignStatement(LocalVariableList varList, Expr left, Expr right) {
        this.varList = varList;
        this.leftPart = left;
        this.rightPart = right;
    }


    @Override
    public void genC(PW pw) {

    }
}
