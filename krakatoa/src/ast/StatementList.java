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



}
