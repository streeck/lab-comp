//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.MemoryHandler;

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

    public ArrayList<String> getNames() {
        ArrayList<String> methodNames = new ArrayList<String>();

        for (Method method : methodList) {
            if (method != null) {
                methodNames.add(method.getName());
            }
        }

        return methodNames;
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
            for(Method m : methodList){
                if(m.getName().equals(name)){
                    return true;
                }
            }
        return false;
    }

    public Method getVariable(String name) {
      for (Method m : methodList) {
        if (m.getName().equals(name)) {
          return m;
        }
      }
        return null;
    }

    public void genKra(PW pw) {
        for (Method method : methodList) {
            if (method != null) {
                method.genKra(pw);
            }
        }
    }

    public void genC(PW pw, String className) {
        for (Method method : methodList) {
            if (method != null) {
                method.genC(pw, className);
            }
        }
    }

    public int fetchPosition(String name) {
        int i= 0;
        for (Method method : methodList)
            if (method.getName().equals(name))
                return i;
            else
                i++;
        return -1;
    }
}
