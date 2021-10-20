package Model.Types;

import Model.Value.ValueInterface;
import Model.Value.IntValue;

public class IntType implements TypeInterface{

    @Override
    public ValueInterface defaultVal() {
        return new IntValue(0);
    }

    @Override
    public TypeInterface deepCopy() {
        return new IntType();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof IntType))
            return false;
        return true;

    }

    @Override
    public String toString(){
        return "int";
    }
}
