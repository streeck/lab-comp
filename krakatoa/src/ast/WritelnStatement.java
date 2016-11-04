//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;


//Colocar um \n no final do genC
public class WritelnStatement extends Statement{
    private ExprList exprList;

    public WritelnStatement(ExprList e){
        this.setExprList(e);
    }

    @Override
    public void genC(PW pw) {

    }

    @Override
    public void genKra(PW pw) {
        pw.printIdent("writeln(");
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
