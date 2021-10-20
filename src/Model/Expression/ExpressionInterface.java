package Model.Expression;

import Model.ADTs.DictInterface;
import Model.Value.ValueInterface;
import Exceptions.ExpressionException;

public interface ExpressionInterface {
    ValueInterface eval(DictInterface<String, ValueInterface> table) throws ExpressionException;

}
