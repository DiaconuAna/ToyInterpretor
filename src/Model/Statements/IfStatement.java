package Model.Statements;

import Exceptions.ADTsExceptions;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.ADTs.DictInterface;
import Model.ADTs.HeapInterface;
import Model.ADTs.StackInterface;
import Model.Expression.ExpressionInterface;
import Model.ProgramState;
import Model.Types.BoolType;
import Model.Types.TypeInterface;
import Model.Value.BoolValue;
import Model.Value.ValueInterface;

public class IfStatement implements StatementInterface{
    ExpressionInterface expr;
    StatementInterface thenStatement;
    StatementInterface elseStatement;

    public IfStatement(ExpressionInterface e, StatementInterface thenS, StatementInterface elseS){
        this.expr = e;
        this.elseStatement = elseS;
        this.thenStatement = thenS;
    }

    @Override
    public String toString(){
        return "IF(" + this.expr.toString() + ")THEN(" + this.thenStatement.toString() + ")ELSE(" + this.elseStatement.toString() + "))";
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, ADTsExceptions {
        DictInterface<String, ValueInterface> symbolTable = state.getSymbolTable();
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        HeapInterface<ValueInterface> heaptable = state.getHeapTable();

        ValueInterface condition = this.expr.eval(symbolTable, heaptable);

        if(condition.getType().equals(new BoolType())){
            if(condition.equals(new BoolValue(true)))
                stack.push(this.thenStatement);
            else
                stack.push(this.elseStatement);
        }
        else
            throw new StatementException("Condition output should be a boolean.");

        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new IfStatement(expr.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
    }

    //G|- e : bool
    //G|- s1:void,G1
    //G|- s2:void,G2
    //---------------------------
    //G|- if e then s1 else s2 : void,G

    @Override
    public DictInterface<String, TypeInterface> typecheck(DictInterface<String, TypeInterface> typeEnv) throws StatementException, ExpressionException, ADTsExceptions {
        TypeInterface expression_type = this.expr.typecheck(typeEnv);

        if(expression_type.equals(new BoolType())){
            thenStatement.typecheck(typeEnv.clone());
            elseStatement.typecheck(typeEnv.clone());
            return typeEnv;
        }
        else
            throw new StatementException("The IF condition is not of bool type");
    }

}
