//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

import java.util.ArrayList;

public class MessageSendToVariable extends MessageSend {

    public MessageSendToVariable(Variable var, Method method,
                                 ExprList exprList) {
        this.var = var;
        this.method = method;
        this.exprList = exprList;
    }

    public Type getType() {
        if (method != null) {
            return method.getType();
        } else {
            return Type.undefinedType;
        }
    }

    public void genC(PW pw, boolean putParenthesis) {
        pw.print("((" + method.getType().getCname() + " (*) ");
        pw.print("(_class_" + var.getType().getCname() + " *");


        if (exprList != null) {
            int size = exprList.getSize();
            if (size > 0) {
                pw.print(", ");
            }
            ArrayList<Expr> localExprList = exprList.getExprList();
            for (Expr expr : localExprList) {
                pw.print(expr.getType().getCname());
                if (--size > 0) {
                    pw.print(", ");
                }
            }

            KraClass localClass = (KraClass) var.getType();
        int i = localClass.fetchPosition(method.getName());
            pw.print(")) _" + var.getName());
        pw.print("->vt[" + i + "]) (_" + var.getName());

            if (exprList.getSize() > 0) {
                pw.print(", ");
            }

            exprList.genC(pw);
            pw.print(")");
        }
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    @Override
    public void genKra(PW pw, boolean putParenthesis) {
        if (putParenthesis) {
            pw.print("(");
        }

        pw.print(var.getName() + ".");
        if (method != null) {
            pw.print(method.getName() + "()");
//            exprList.genKra(pw);
//            pw.print(")");
        }


        if (putParenthesis) {
            pw.print(")");
        }
    }

    private Variable var;
    private Method method;
    private ExprList exprList;
}
