package Model.Expression;

import Exceptions.ExpressionException;
import Model.ADTs.DictInterface;
import Model.ADTs.HeapInterface;
import Model.Expression.ExpressionInterface;
import Model.Types.IntType;
import Model.Types.TypeInterface;
import Model.Value.IntValue;
import Model.Value.ValueInterface;

public class ArithmeticExpression implements ExpressionInterface{

    int operation_id;
    ExpressionInterface exp1, exp2;

    public ArithmeticExpression(char op, ExpressionInterface e1, ExpressionInterface e2){
        this.exp1 = e1;
        this.exp2 = e2;

        if(op == '+')
            this.operation_id = 1;
        else if(op == '-')
            this.operation_id = 2;
        else if(op == '*')
            this.operation_id = 3;
        else if(op == '/')
            this.operation_id = 4;

    }

    @Override
    public ValueInterface eval(DictInterface<String, ValueInterface> table, HeapInterface<ValueInterface> heap) throws ExpressionException {
        ValueInterface v1, v2;
        v1 = this.exp1.eval(table, heap);

        if(v1.getType().equals(new IntType())){
            v2 = this.exp2.eval(table, heap);
            if(v2.getType().equals(new IntType())){
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;

                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();

                if(this.operation_id == 1)
                    return new IntValue(n1 + n2);
                if(this.operation_id == 2)
                    return new IntValue(n1 - n2);
                if(this.operation_id == 3)
                    return new IntValue(n1 * n2);
                if(this.operation_id == 4)
                    if(n2 == 0)
                        throw new ExpressionException("Division by zero!");
                    else
                        return new IntValue(n1/n2);
                throw new ExpressionException("Invalid operation. Please try again!");
            }
            else
                throw new ExpressionException("Second operand is not an integer!");
        }
        else
            throw new ExpressionException("First operand is not an integer!");
    }

    @Override
    public ExpressionInterface deepCopy() {
        return new ArithmeticExpression(getOperand(), this.exp1.deepCopy(), this.exp2.deepCopy());
    }

    @Override
    public TypeInterface typecheck(DictInterface<String, TypeInterface> typeEnv) throws ExpressionException {
        TypeInterface type1, type2;
        type1 = this.exp1.typecheck(typeEnv);
        type2 = this.exp2.typecheck(typeEnv);

        if(type1.equals(new IntType())){
            if(type2.equals(new IntType())){
                return new IntType();
            }
            else
                throw new ExpressionException("2nd operand is not an integer");
        }
        else
            throw new ExpressionException("1st operand is not an integer");
    }


    @Override
    public String toString(){
        if(this.operation_id == 1){
            return exp1.toString() + "+" + exp2.toString();
        }
        else if(this.operation_id == 2){
            return exp1.toString() + "-" + exp2.toString();
        }
        else if(this.operation_id == 3){
            return exp1.toString() + "*" + exp2.toString();
        }
        else if(this.operation_id == 4){
            return exp1.toString() + "/" + exp2.toString();
        }
        else
            return "";
    }

    public ExpressionInterface getFirst(){
        return this.exp1;
    }

    public ExpressionInterface getSecond(){
        return this.exp2;
    }

    public char getOperand(){
        if(this.operation_id == 1)
            return '+';
        if(this.operation_id == 2)
            return '-';
        if(this.operation_id == 3)
            return '*';
        if(this.operation_id == 4)
            return '/';
        return ' ';
    }
}
