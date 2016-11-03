package ast;

/**
 * Created by Angela on 28/10/2016.
 */
public class AssignStatement extends Statement{
    private Expr leftPart;
    private Expr rightPart;
    private LocalVariableList varList;

    public AssignStatement(LocalVariableList varList, Expr left, Expr right) {
        this.setVarList(varList);
        this.setLeftPart(left);
        this.setRightPart(right);
    }


    @Override
    public void genC(PW pw) {

    }

    public Expr getLeftPart() {
        return leftPart;
    }

    public void setLeftPart(Expr leftPart) {
        this.leftPart = leftPart;
    }

    public Expr getRightPart() {
        return rightPart;
    }

    public void setRightPart(Expr rightPart) {
        this.rightPart = rightPart;
    }

    public LocalVariableList getVarList() {
        return varList;
    }

    public void setVarList(LocalVariableList varList) {
        this.varList = varList;
    }

    public boolean searchVariable(String name){
        if(varList.hasVariable(name)) return true;
        return false;
    }

}
