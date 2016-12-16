//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

public class WriteStatement extends Statement{
    private ExprList exprList;

    public  WriteStatement(ExprList e){
        this.setExprList(e);
    }
    @Override
    public void genC(PW pw, String className) {
        pw.printIdent("puts(");
        exprList.genC(pw, className);
        pw.println(");");
    }

    @Override
    public void genKra(PW pw) {
        pw.printIdent("write(");
        exprList.genKra(pw);
        pw.println(");");
    }

    public ExprList getExprList() {
        return exprList;
    }

    public void setExprList(ExprList exprList) {
        this.exprList = exprList;
    }
}
