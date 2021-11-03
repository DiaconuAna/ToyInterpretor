package Model.Expression;

import Exceptions.ExpressionException;
import Model.ADTs.DictInterface;
import Model.Types.IntType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.ValueInterface;

public class RelationalExpression implements ExpressionInterface{

    int operation_id;
    ExpressionInterface exp1, exp2;

    public RelationalExpression(String relation, ExpressionInterface e1, ExpressionInterface e2) {
        this.exp1 = e1;
        this.exp2 = e2;

        switch (relation) {
            case ">" -> this.operation_id = 1;
            case ">=" -> this.operation_id = 2;
            case "<" -> this.operation_id = 3;
            case "<=" -> this.operation_id = 4;
            case "==" -> this.operation_id = 5;
            case "!=" -> this.operation_id = 6;
            default -> this.operation_id = -1;
        }
    }

    @Override
    public ValueInterface eval(DictInterface<String, ValueInterface> table) throws ExpressionException {
        ValueInterface v1, v2;

        v1 = this.exp1.eval(table);

        if(v1.getType().equals(new IntType())){
            v2 = this.exp2.eval(table);

            if(v2.getType().equals(new IntType())){
                IntValue val1 = (IntValue)v1;
                IntValue val2 = (IntValue)v2;

                int n1, n2;
                n1 = val1.getVal();
                n2 = val2.getVal();

                switch(this.operation_id){
                    case 1: return new BoolValue(n1 > n2);
                    case 2: return new BoolValue(n1 >= n2);
                    case 3: return new BoolValue(n1 < n2);
                    case 4: return new BoolValue(n1 <= n2);
                    case 5: return new BoolValue(n1 == n2);
                    case 6: return new BoolValue(n1 != n2);
                    default:
                        throw new ExpressionException("Invalid operation. Please try again");
                }
            }
            else
                throw new ExpressionException("Second operand must be an integer!");
        }
        else
            throw new ExpressionException("First operand must be an integer!");
    }

    @Override
    public String toString(){
        return switch (this.operation_id) {
            case 1 -> this.exp1.toString() + ">" + this.exp2.toString();
            case 2 -> this.exp1.toString() + ">=" + this.exp2.toString();
            case 3 -> this.exp1.toString() + "<" + this.exp2.toString();
            case 4 -> this.exp1.toString() + "<=" + this.exp2.toString();
            case 5 -> this.exp1.toString() + "==" + this.exp2.toString();
            case 6 -> this.exp1.toString() + "!=" + this.exp2.toString();
            default -> " ";
        };
    }
}
