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
    public void genC(PW pw, String className) {
        int i = varList.getSize();
        int count = 0;
        Iterator<Variable> vars = varList.elements();
        while (vars.hasNext()) {
            Variable var = vars.next();
            if(var.getType() == Type.intType) {
                pw.printIdent("char __s[512];"); //Talvez de errado -- Redeclaracao
                pw.printIdent("gets(__s);");
                pw.printIdent("sscanf(__s, \"%d\", ");

                //Se for variável de instancia:
                if (var instanceof InstanceVariable) {
                    pw.print("&this->");
                    pw.print("_" + className + "_" + var.getName());
                } else {
                    pw.print("&_" + var.getName());
                }
                pw.println(");");
            }else if(var.getType()== Type.stringType){
                pw.println("char __s[512];"); //Talvez de errado -- Redeclaracao
                pw.println("gets(__s);");
                //Vc le a variavel, faz um malloc para ela e copia o valor em s
                pw.println("_"+var.getName()+" = malloc(strlen(__s) + 1);");
                pw.printIdent("strcpy(_" + var.getName() +", __s);");
            }
        }
    }

    @Override
    public void genKra(PW pw) {
        pw.printIdent("read(");
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
