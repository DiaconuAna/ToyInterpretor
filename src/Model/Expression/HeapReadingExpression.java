package Model.Expression;

import Exceptions.ExpressionException;
import Model.ADTs.DictInterface;
import Model.ADTs.HeapInterface;
import Model.Types.ReferenceType;
import Model.Types.TypeInterface;
import Model.Value.ReferenceValue;
import Model.Value.ValueInterface;

public class HeapReadingExpression implements ExpressionInterface{
    ExpressionInterface expression;

    public HeapReadingExpression(ExpressionInterface expr){
        this.expression = expr;
    }

    @Override
    public ValueInterface eval(DictInterface<String, ValueInterface> table, HeapInterface<ValueInterface> heap) throws ExpressionException {
        ValueInterface expr_eval = this.expression.eval(table, heap);
        if(expr_eval instanceof ReferenceValue){
            ReferenceValue val = (ReferenceValue)expr_eval;
            int address = val.getAddress();
            if(heap.isAddress(address)){
                return heap.getValue(address);
            }
            else
                throw new ExpressionException("Key could not be found in Heap");
        }
        else
            throw new ExpressionException("Expression must be of Reference Value");
    }

    @Override
    public ExpressionInterface deepCopy() {
        return new HeapReadingExpression(this.expression);
    }

    @Override
    public TypeInterface typecheck(DictInterface<String, TypeInterface> typeEnv) throws ExpressionException {
        TypeInterface type = this.expression.typecheck(typeEnv);

        if(type instanceof ReferenceType){
            ReferenceType ref_type = (ReferenceType)type;
            return ref_type.getInner();
        }
        else
            throw new ExpressionException("The ReadHeap argument is not of reference type");
    }

    @Override
    public String toString(){
        return "rH(" + this.expression.toString() + ")";

    }
}
