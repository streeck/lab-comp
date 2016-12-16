//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

public class ObjectConstructor extends Expr{
    private KraClass object;
    private KraClass castingObj;

    public ObjectConstructor(KraClass k){
        this.setObject(k);
   }

    @Override
    public void genC(PW pw, boolean putParenthesis) {
        if (castingObj != null && object != null) {
            if (castingObj.getName() != object.getName()) {
                pw.print("(" + castingObj.getName() + " ) ");
            }
        }
        pw.print("new_" + object.getName() + "()");
    }

    @Override
    public void genKra(PW pw, boolean putParenthesis) {
        // If cast is needed
        if (castingObj != null && object != null) {
            if (castingObj.getName() != object.getName()) {
                pw.print("(" + castingObj.getName() + " ) ");
            }
        }
        pw.print("new " + object.getName() + "()");
    }

    @Override
    public Type getType() {
        return getObject();
    }

    public KraClass getObject() {
        return object;
    }

    public void setObject(KraClass object) {
        this.object = object;
    }

    public KraClass getCastingObj() {
        return castingObj;
    }

    public void setCastingObj(KraClass castingObj) {
        this.castingObj = castingObj;
    }
}
