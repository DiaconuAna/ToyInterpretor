package Model.Types;
import Exceptions.TypeException;

import Model.Value.ValueInterface;

public interface TypeInterface {
    ValueInterface defaultVal();
    TypeInterface deepCopy();
}
