package Model.Expression;

import Model.ADTs.DictInterface;
import Exceptions.ExpressionException;
import Model.ADTs.HeapInterface;
import Model.Types.TypeInterface;
import Model.Value.ValueInterface;


public class ValueExpression implements ExpressionInterface{
    ValueInterface val;

    public ValueExpression(ValueInterface v){
        this.val = v;
    }

    @Override
    public ValueInterface eval(DictInterface<String, ValueInterface> table, HeapInterface<ValueInterface> heap) throws ExpressionException {
        return this.val;
    }

    @Override
    public ExpressionInterface deepCopy() {
        return new ValueExpression(this.val.deepCopy());
    }

    @Override
    public TypeInterface typecheck(DictInterface<String, TypeInterface> typeEnv) throws ExpressionException {
        return this.val.getType();
    }


    public TypeInterface getType(){
        return this.val.getType();
    }

    @Override
    public String toString(){
        return this.val.toString();
    }
}
