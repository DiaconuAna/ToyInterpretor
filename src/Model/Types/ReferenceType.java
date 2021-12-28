package Model.Types;

import Model.Value.ReferenceValue;
import Model.Value.ValueInterface;

public class ReferenceType implements TypeInterface{

    TypeInterface inner;

    public ReferenceType(TypeInterface inner){
        this.inner = inner;
    }

    public TypeInterface getInner(){
        return this.inner;
    }

    @Override
    public boolean equals(Object another){
        if(another instanceof ReferenceType)
            return inner.equals(((ReferenceType) another).getInner());
        else
            return false;
    }

    @Override
    public ValueInterface defaultVal() {
        return new ReferenceValue(0, inner);
    }

    @Override
    public TypeInterface deepCopy() {
        return new ReferenceType(this.inner);
    }

    @Override
    public String toString(){
        return "Ref(" + inner.toString() + ")";
    }
}
