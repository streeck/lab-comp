//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;


//Será util para gerar um ";" no codigo...
public class NullStatement extends Statement {
    @Override
    public void genC(PW pw, String className) {

    }

    public void genKra(PW pw) {
        pw.println(";");
    }
}
