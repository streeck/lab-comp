//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Angela on 28/10/2016.
 */
public class StatementList {
    private ArrayList<Statement> stmtList;

    public StatementList(){
       this.stmtList = new ArrayList<Statement>();
    }
    public void addElement(Statement stmt) {
        stmtList.add(stmt);
    }

    public Iterator<Statement> elements() {
        return this.stmtList.iterator();
    }

    public int getSize() {
        return stmtList.size();
    }


    public boolean searchReturn() {
        for(Statement s: stmtList){
           if(s instanceof ReturnStatement) return true;
        }

        return false;
    }

    public boolean searchVariable(String variableName) {
        for(Statement s: stmtList){
            if(s instanceof AssignStatement){
                AssignStatement assign = (AssignStatement)s;
                if(assign.searchVariable(variableName))
                    return true;
            }
        }
        return false;
    }

    public Variable getVariable(String name) {
        for(Statement s: stmtList){
            if(s instanceof AssignStatement){
                Variable v = ((AssignStatement) s).getVarList().getVariable(name);
                    return v;
            }
        }
        return null;
    }

    public void genKra(PW pw) {
        if (!stmtList.isEmpty()) {
            for (Statement stmt : stmtList) {
                if (stmt != null) {
                    stmt.genKra(pw);
                }
            }
        }

    }
}
