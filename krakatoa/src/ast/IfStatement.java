//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;


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
    public void genC(PW pw, String className) {
        pw.printIdent("if ( ");

        if (expr.getType() == Type.booleanType) {
            expr.genC(pw, false);
//            pw.print(" != false");
        }
        else {
            expr.genC(pw, false);
        }
        pw.println(" ) {");

        if (stmtIf instanceof CompositeStatement) {
            pw.print("");
            stmtIf.genC(pw, className);
        } else {
            pw.println("");
            if (stmtIf != null) {
                pw.add();
                stmtIf.genC(pw, className);
                pw.sub();
            }
        }

//        pw.add();
//        stmtIf.genC(pw, className);
//        pw.sub();

        if (stmtElse != null) {
            pw.printlnIdent("} else {");

            pw.add();
            stmtElse.genC(pw, className);
            pw.sub();
        }
        pw.printlnIdent("}");
    }

    @Override
    public void genKra(PW pw) {
        pw.printIdent("if (");
        expr.genKra(pw, false);
        pw.print(") ");

        if (stmtIf instanceof CompositeStatement) {
            pw.print("");
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
            pw.printIdent("else ");
            stmtElse.genKra(pw);
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
