package Model.Value;

import Model.Types.ReferenceType;
import Model.Types.TypeInterface;

public class ReferenceValue implements ValueInterface{

    int address;
    TypeInterface locationType;

    public ReferenceValue(int addr, TypeInterface inner){
        this.address = addr;
        this.locationType = inner;
    }

    public int getAddress(){
        return this.address;
    }

    public TypeInterface getLocationType(){
        return this.locationType;
    }

    @Override
    public TypeInterface getType() {
        return new ReferenceType(this.locationType);
    }

    @Override
    public ValueInterface deepCopy() {
        return new ReferenceValue(this.address, this.locationType);
    }

    @Override
    public String toString(){
        return "(" + this.address + ", " + this.locationType.toString() + ")";
    }
}
