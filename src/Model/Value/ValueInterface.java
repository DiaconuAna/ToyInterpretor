package Model.Value;
import Model.Types.TypeInterface;

public interface ValueInterface {
    TypeInterface getType();
    ValueInterface deepCopy();
}
