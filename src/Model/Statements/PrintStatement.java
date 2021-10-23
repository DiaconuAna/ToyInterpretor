package Model.Statements;

import Exceptions.ADTsExceptions;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.ProgramState;
import Model.ADTs.ListInterface;
import Model.Expression.ExpressionInterface;
import Model.Value.ValueInterface;

public class PrintStatement implements StatementInterface{
    ExpressionInterface expr;

    public PrintStatement(ExpressionInterface e){
        this.expr = e;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, ADTsExceptions {
        ListInterface<ValueInterface> output = state.getOut();
        output.add(this.expr.eval(state.getSymbolTable()));
        state.setOut(output);
        return state;
    }

    @Override
    public String toString(){
        return "print(" +this.expr.toString()+")";
    }
}
