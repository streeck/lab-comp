//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662

package ast;

public class MessageSendToInstance extends Expr {

    private Variable var;
    private Variable instanceVariable;

    public MessageSendToInstance(Variable inLocal, Variable instanceVariable) {
        this.var = inLocal;
        this.instanceVariable = instanceVariable;
    }

    @Override
    public void genC(PW pw, boolean putParenthesis) {
        pw.print(var.getName() + "." + instanceVariable.getName());
    }

    @Override
    public void genKra(PW pw, boolean putParenthesis) {

    }

//    @Override
//    public void genKra(PW pw, boolean putParenthesis) {
//        pw.printIdent(var.getName() + "." + instanceVariable.getName());
//    }

    @Override
    public Type getType() {
        // TODO Auto-generated method stub
        return null;
    }
}
