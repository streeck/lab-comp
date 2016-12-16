//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662

package ast;


public class BreakStatement extends Statement{


    @Override
    public void genC(PW pw, String className) {

    }

    @Override
    public void genKra(PW pw) {
        pw.printlnIdent("break;");
    }
}
