//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

/**
 * Created by Angela on 28/10/2016.
 */
public class WriteStatement extends Statement{
    private ExprList exprList;

    public  WriteStatement(ExprList e){
        this.setExprList(e);
    }
    @Override
    public void genC(PW pw) {

    }

    public ExprList getExprList() {
        return exprList;
    }

    public void setExprList(ExprList exprList) {
        this.exprList = exprList;
    }
}
