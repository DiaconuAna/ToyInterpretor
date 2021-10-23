package Model.Statements;

import Exceptions.ADTsExceptions;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.ADTs.DictInterface;
import Model.ADTs.StackInterface;
import Model.Expression.ExpressionInterface;
import Model.ProgramState;
import Model.Types.BoolType;
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
        ValueInterface condition = this.expr.eval(symbolTable);

        if(condition.getType().equals(new BoolType())){
            if(condition.equals(new BoolValue(true)))
                stack.push(this.thenStatement);
            else
                stack.push(this.elseStatement);
        }
        else
            throw new StatementException("Condition output should be a boolean.");

        return state;
    }

}
