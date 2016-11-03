//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.MemoryHandler;

/**
 * Created by Angela on 27/10/2016.
 */
public class MethodList {
    private ArrayList<Method> methodList;

    public MethodList() {
        methodList = new ArrayList<Method>();
    }

    public void addElement(Method m) {
        methodList.add(m);
    }

    public Iterator<Method> elements() {
        return this.methodList.iterator();
    }

    public int getSize() {
        return methodList.size();
    }

    //Retorna true se o metodo já existir no vetor.
    //Caso contrário retorna falso.
    public boolean exist(Method method) {
        if(!methodList.isEmpty())
            if(methodList.contains(method))
                return true;

        return false;
    }

    public boolean exist(String name){
        //if(!methodList.isEmpty()){
            for(Method m : methodList){
                if(m.getName().equals(name))
                    return true;
            }
        //}
        return false;
    }
}
