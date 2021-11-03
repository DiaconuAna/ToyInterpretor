package Model.Value;

import Model.Types.StringType;
import Model.Types.TypeInterface;
import com.sun.jdi.IntegerValue;

public class StringValue implements ValueInterface {
    String val;

    public StringValue(){
        this.val = "";
    }

    public StringValue(String str){
        this.val = str;
    }

    public String getValue(){
        return this.val;
    }

    @Override
    public TypeInterface getType() {
        return new StringType();
    }

    @Override
    public ValueInterface deepCopy() {
        return new StringValue(this.val);
    }

    @Override
    public String toString(){
        return this.val;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof StringValue))
            return false;

        StringValue o_val = (StringValue) obj;
        return o_val.val == this.val;
    }
}
