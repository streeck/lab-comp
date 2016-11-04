//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

/**
 * Created by Angela on 28/10/2016.
 */
public class IfStatement extends Statement {
    private Statement stmtIf;
    private Statement stmtElse;
    private Expr expr;

   public IfStatement(Statement stmtIf, Expr expr){
       this.setExpr(expr);
       this.setStmtIf(stmtIf);
       this.stmtElse = null;
    }

    @Override
    public void genC(PW pw) {

    }

    @Override
    public void genKra(PW pw) {
        pw.printIdent("if (");
        expr.genKra(pw, false);
        pw.print(") ");

        if (stmtIf instanceof CompositeStatement) {
            stmtIf.genKra(pw);
        } else {
            pw.println("");
            if (stmtIf != null) {
                pw.add();
                stmtIf.genKra(pw);
                pw.sub();
            }
        }

        if (stmtElse != null){
            pw.printlnIdent("} else {");
            pw.add();
            stmtElse.genKra(pw);
            pw.sub();
            pw.printlnIdent("}");
        }
    }

    public Statement getStmtIf() {
        return stmtIf;
    }

    public void setStmtIf(Statement stmtIf) {
        this.stmtIf = stmtIf;
    }

    public Statement getStmtElse() {
        return stmtElse;
    }

    public void setStmtElse(Statement stmtElse) {
        this.stmtElse = stmtElse;
    }

    public Expr getExpr() {
        return expr;
    }

    public void setExpr(Expr expr) {
        this.expr = expr;
    }
}
