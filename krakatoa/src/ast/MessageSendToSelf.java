//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;


public class MessageSendToSelf extends MessageSend {
    
    public Type getType() { 
        return null;
    }
    
    public void genC( PW pw, boolean putParenthesis ) {
    }

    @Override
    public void genKra(PW pw, boolean putParenthesis) {

    }
}