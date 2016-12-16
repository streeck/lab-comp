//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

import java.util.ArrayList;
import java.util.Iterator;

public class WriteStatement extends Statement{
    private ExprList exprList;

    public  WriteStatement(ExprList e){
        this.setExprList(e);
    }
    @Override
    public void genC(PW pw, String className) {
        ArrayList<Expr> exprs = exprList.getExprList();

        for (Expr e : exprs) {
            if(e.getType() == Type.stringType) {
                pw.printIdent("puts(\"");
                e.genC(pw, false);
                pw.println(" \");");
            } else if (e.getType() == Type.intType) {
                pw.printIdent("printf(\"%d \", ");
                e.genC(pw, false);
                pw.println(");");
            }
        }
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
