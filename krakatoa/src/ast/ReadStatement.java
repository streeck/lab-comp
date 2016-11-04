//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;


import java.util.Iterator;

public class ReadStatement extends Statement{
    private VariableList varList;

    public ReadStatement(VariableList v){
        this.setVarList(v);
    }
    @Override
    public void genC(PW pw) {

    }

    @Override
    public void genKra(PW pw) {
        pw.print("read(");
        int i = varList.getSize();
        int count = 0;
        Iterator<Variable> vars = varList.elements();
        while (vars.hasNext()) {
            Variable var = vars.next();
            pw.print(var.getName());
            if (count != i) {
                pw.print(", ");
            }
            count++;
        }
        pw.println(");");
    }

    public VariableList getVarList() {
        return varList;
    }

    public void setVarList(VariableList varList) {
        this.varList = varList;
    }
}
