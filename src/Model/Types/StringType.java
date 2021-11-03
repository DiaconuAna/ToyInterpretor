package Model.Types;

import Model.Value.StringValue;
import Model.Value.ValueInterface;

public class StringType implements TypeInterface{

    @Override
    public ValueInterface defaultVal() {
        return new StringValue("");
    }

    @Override
    public TypeInterface deepCopy() {
        return new StringType();
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof StringType))
            return false;
        return true;
    }

    @Override
    public String toString(){
        return "String";
    }
}
