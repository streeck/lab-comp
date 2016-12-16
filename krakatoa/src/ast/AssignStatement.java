//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662

package ast;


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
    public void genC(PW pw, String className) {
        if (leftPart != null) {
            pw.printIdent("");
            leftPart.genC(pw, false);
            if (rightPart != null) {
                pw.print(" = ");
                rightPart.genC(pw, false);
                pw.println(";");
            }
        }
    }

    @Override
    public void genKra(PW pw) {
        if (leftPart != null) {
            pw.printIdent("");
            leftPart.genKra(pw, false);
            if (rightPart != null) {
                pw.print(" = ");
                rightPart.genKra(pw, false);
                pw.println(";");
            }
        }
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
