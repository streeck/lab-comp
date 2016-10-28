//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

/**
 * Created by Angela on 28/10/2016.
 */
public class ReadStatement extends Statement{
    private VariableList varList;

    public ReadStatement(VariableList v){
        this.setVarList(v);
    }
    @Override
    public void genC(PW pw) {

    }

    public VariableList getVarList() {
        return varList;
    }

    public void setVarList(VariableList varList) {
        this.varList = varList;
    }
}
