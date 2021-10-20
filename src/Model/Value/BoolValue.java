package Model.Value;

import Model.Types.BoolType;
import Model.Types.TypeInterface;

public class BoolValue implements ValueInterface{

    boolean val;

    public BoolValue(){this.val = false;}
    public BoolValue(boolean i){this.val = i;}

    public boolean getValue(){
        return this.val;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof BoolType))
            return false;
        BoolValue obj_val = (BoolValue) obj;
        return obj_val.val == this.val;

    }

    @Override
    public String toString(){
         if(this.val)
             return "true";
         else
             return "false";
    }

    @Override
    public TypeInterface getType() {
        return new BoolType();
    }

    @Override
    public ValueInterface deepCopy() {
        return new BoolValue(this.val);
    }
}
