//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

public class MessageSendToSuper extends MessageSend {
    private KraClass classSender;
    private Method methodMessage;
    private ExprList exprList;


    public MessageSendToSuper(KraClass currentClass, Method methodMessage, ExprList exprList) {
        this.setClassSender(currentClass);
        this.setMethodMessage(methodMessage);
        this.setExprList(exprList);
    }

    //Retorna o tipo do metodo
    public Type getType() { 
        return methodMessage.getType();
    }

    public void genC( PW pw, boolean putParenthesis ) {
        
    }

    @Override
    public void genKra(PW pw, boolean putParenthesis) {
        if (putParenthesis) {
            pw.print("(");
        }
        pw.print("super." + methodMessage.getName() + "(");
        exprList.genKra(pw);
        pw.print(")");
        if (putParenthesis) {
            pw.print(")");
        }
    }

    public KraClass getClassSender() {
        return classSender;
    }

    public void setClassSender(KraClass classSender) {
        this.classSender = classSender;
    }

    public Method getMethodMessage() {
        return methodMessage;
    }

    public void setMethodMessage(Method methodMessage) {
        this.methodMessage = methodMessage;
    }

    public ExprList getExprList() {
        return exprList;
    }

    public void setExprList(ExprList exprList) {
        this.exprList = exprList;
    }
}