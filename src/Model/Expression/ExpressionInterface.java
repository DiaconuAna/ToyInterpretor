package Model.Expression;

import Model.ADTs.DictInterface;
import Model.ADTs.HeapInterface;
import Model.Types.TypeInterface;
import Model.Value.ValueInterface;
import Exceptions.ExpressionException;

public interface ExpressionInterface {
    ValueInterface eval(DictInterface<String, ValueInterface> table, HeapInterface<ValueInterface> heap) throws ExpressionException;
    ExpressionInterface deepCopy();
    TypeInterface typecheck(DictInterface<String, TypeInterface> typeEnv) throws ExpressionException;
}
