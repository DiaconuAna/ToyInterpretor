package Model.Types;

import Model.Value.ValueInterface;
import Model.Value.BoolValue;

public class BoolType implements TypeInterface{
    @Override
    public ValueInterface defaultVal() {
        return new BoolValue(false);
    }

    @Override
    public TypeInterface deepCopy() {
        return new BoolType();
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof BoolType))
            return false;
        return true;
    }

    @Override
    public String toString(){
        return "bool";
    }
}
