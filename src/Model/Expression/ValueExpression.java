package Model.Expression;

import Model.ADTs.DictInterface;
import Exceptions.ExpressionException;
import Model.Types.TypeInterface;
import Model.Value.ValueInterface;


public class ValueExpression implements ExpressionInterface{
    ValueInterface val;

    public ValueExpression(ValueInterface v){
        this.val = v;
    }

    @Override
    public ValueInterface eval(DictInterface<String, ValueInterface> table) throws ExpressionException {
        return this.val;
    }

    public TypeInterface getType(){
        return this.val.getType();
    }

    @Override
    public String toString(){
        return this.val.toString();
    }
}
