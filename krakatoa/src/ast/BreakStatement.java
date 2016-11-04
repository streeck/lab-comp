//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662

package ast;

/**
 * Created by Angela on 28/10/2016.
 */
public class BreakStatement extends Statement{


    @Override
    public void genC(PW pw) {

    }

    @Override
    public void genKra(PW pw) {
        pw.printlnIdent("break;");
    }
}
