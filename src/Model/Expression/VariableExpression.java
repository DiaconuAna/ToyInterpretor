package Model.Expression;

import Exceptions.ExpressionException;
import Model.ADTs.DictInterface;
import Model.ADTs.HeapInterface;
import Model.Expression.ExpressionInterface;
import Model.Types.TypeInterface;
import Model.Value.ValueInterface;

public class VariableExpression implements ExpressionInterface {
    String var_id;

    public VariableExpression(String id){
        this.var_id = id;
    }

    @Override
    public ValueInterface eval(DictInterface<String, ValueInterface> table , HeapInterface<ValueInterface> heap) throws ExpressionException {
        if(!(table.isDefined(this.var_id)))
            throw new ExpressionException("Nonexistent element. Please try again!");
        return table.lookup(this.var_id);
    }

    @Override
    public ExpressionInterface deepCopy() {
        return new VariableExpression(this.var_id);
    }

    @Override
    public TypeInterface typecheck(DictInterface<String, TypeInterface> typeEnv) throws ExpressionException {
        return typeEnv.lookup(var_id);
    }

    @Override
    public String toString(){
        return this.var_id;
    }
}
