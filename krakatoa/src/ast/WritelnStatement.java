//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;


import java.util.ArrayList;

//Colocar um \n no final do genC
public class WritelnStatement extends Statement{
    private ExprList exprList;

    public WritelnStatement(ExprList e){
        this.setExprList(e);
    }

    @Override
    public void genC(PW pw, String className) {
        ArrayList<Expr> exprs = exprList.getExprList();

        for (Expr e : exprs) {
            if(e.getType() == Type.stringType) {
                pw.printIdent("puts(\"");
                e.genC(pw, false);
                pw.println("\");");
            } else if (e.getType() == Type.intType) {
                pw.printIdent("printf(\"%d\", ");
                e.genC(pw, false);
                pw.println(");");
            }
        }
        pw.printlnIdent("printf(\"\\n\");");
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
