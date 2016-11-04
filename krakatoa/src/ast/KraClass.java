//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

import java.util.ArrayList;

/*
 * Krakatoa Class
 */
public class KraClass extends Type {

   private String name;
   private KraClass superclass;
   private InstanceVariableList instanceVariableList;
   private MethodList publicMethodList;
   private MethodList privateMethodList;

   public KraClass( String name ) {
      super(name);
      publicMethodList = new MethodList();
      privateMethodList = new MethodList();
      instanceVariableList = new InstanceVariableList();
   }

    public void genKra(PW pw) {
        pw.print("class " + this.getName());
        if (superclass != null) {
            pw.print(" extends " + superclass.getName());
        }
        pw.println(" {");
        pw.add();
        if (instanceVariableList != null) {instanceVariableList.genKra(pw);}
        if (publicMethodList != null) {publicMethodList.genKra(pw, true);}
        if (privateMethodList != null) {privateMethodList.genKra(pw, false);}
        pw.sub();
        pw.println("}");
    }

   public String getCname() {
      return getName();
   }

   public void setName(String name) {
      this.name = name;
   }

   public KraClass getSuperclass() {
      return superclass;
   }

   public void setSuperclass(KraClass superclass) {
      this.superclass = superclass;
   }

   public InstanceVariableList getInstanceVariableList() {
      return instanceVariableList;
   }

   public void setInstanceVariableList(InstanceVariableList instanceVariableList) {
      this.instanceVariableList = instanceVariableList;
   }

   public void addPublicMethod(Method m){
      publicMethodList.addElement(m);
   }

   public void addPrivateMethod(Method m){
      privateMethodList.addElement(m);
   }
   public MethodList getPublicMethodList() {
      return publicMethodList;
   }

   public void setPublicMethodList(MethodList publicMethodList) {
      this.publicMethodList = publicMethodList;
   }

   public MethodList getPrivateMethodLis() {
      return privateMethodList;
   }

   public void setPrivateMethodLis(MethodList privateMethodList) {
      this.privateMethodList = privateMethodList;
   }

   //Retorna True se o metodo já existir, ou na lista de publico, ou na lista de privados.
   public boolean existMethod(Method method) {
       if( publicMethodList.exist(method) || privateMethodList.exist(method) )
           return true;
       return false;
   }
   public boolean existMethod(String method) {
      if( publicMethodList.exist(method) || privateMethodList.exist(method) )
         return true;
      return false;
   }
   public boolean existPublicMethod(String method) {
      if (publicMethodList.exist(method))
         return true;
      return false;
   }
   public boolean existPrivateMethod(String method) {
      if (privateMethodList.exist(method))
         return true;
      return false;
   }
   public boolean existInstanceVariable(InstanceVariable v){
       return instanceVariableList.exist(v);
   }
   public boolean existInstanceVariable(String v){
       return instanceVariableList.exist(v);
   }

   public Method fetchMethod(String ident) {
     if (this.publicMethodList.exist(ident)) {
       return this.publicMethodList.getVariable(ident);
     } else {
       return this.privateMethodList.getVariable(ident);
     }
   }
   public Method fetchPublicMethod(String ident) {
     return this.publicMethodList.getVariable(ident);
   }
   public Method fetchPrivateMethod(String ident) {
     return this.privateMethodList.getVariable(ident);
   }


    //Procura um metodo na superClasses para receber uma mensagem. Se existir um método com o mesmo nome
   //Verifica se os parametros são iguais (tipos iguais na mesma ordem)
    //Retorna o Method
    public Method findMessage(String messageName) {
        KraClass aux;
        Method m = null;
        ArrayList<Method> listMethod = new ArrayList<Method>();
        aux = this.getSuperclass();
       do {
           if (aux.existPublicMethod(messageName)) {
               m = aux.fetchPublicMethod(messageName);
              return m;
           }else{
               aux = aux.getSuperclass();
           }
       }while(aux != null);
       return null;
    }


   /*/Procura um metodo na superClasses para receber uma mensagem. Se existir um método com o mesmo nome
   //Verifica se os parametros são iguais (tipos iguais na mesma ordem)
    //Retorna o Method
    public Method findMessage(String messageName, ExprList exprList) {
       KraClass aux;
        Method m = null;
       aux = this.getSuperclass();
       do {
           if (aux.existPublicMethod(messageName)) {
               m = aux.fetchPublicMethod(messageName);
               Boolean found = false;
               ArrayList<Expr> eList = exprList.getExprList();
               ArrayList<Variable> pList = m.getParamList().getParamList();
               //Verifica se a qtd de paramentros são iguais
               if (eList.size() == pList.size()){
                   for (Expr e : eList) {
                       if (found == true) break;
                       //Procura uma variavel na lista de parametros do metodo
                       for (Variable v : pList) {
                           if (e.getType() == v.getType()) {
                               found = true;
                               break;
                           }
                       }
                   }
               }else found = false;
               if(found)
                   return m;

           }else{
               aux = aux.getSuperclass();
           }
       }while(aux != null);
       return null;
    }/**/
    /*/Retorna true se os parametros
   public boolean verifyParamMessage(String messageName) {
      KraClass aux;
      aux = this.getSuperclass();
      do{
         if (aux.existMethod(messageName)){
            return true;
         }else{
            aux = aux.getSuperclass();
         }
      }while(aux != null);
      return false;
   }*/
}
