package Model.Statements;

import Exceptions.ADTsExceptions;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.ADTs.DictInterface;
import Model.ADTs.StackInterface;
import Model.Expression.ExpressionInterface;
import Model.ProgramState;
import Model.Types.StringType;
import Model.Value.StringValue;
import Model.Value.ValueInterface;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseReadFile implements StatementInterface{
    ExpressionInterface expression;

    public CloseReadFile(ExpressionInterface expression){
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, ADTsExceptions, IOException {
        StackInterface<StatementInterface> stmtstack = state.getExecutionStack();
        DictInterface<StringValue, BufferedReader> filetable = state.getFileTable();
        DictInterface<String, ValueInterface> sym = state.getSymbolTable();

       // StatementInterface stmt = stmtstack.pop();
        ValueInterface val = this.expression.eval(sym);
        if(val.getType().equals(new StringType())){
            StringValue stringValue = (StringValue)val;
            BufferedReader fd;
            fd = filetable.lookup(stringValue);
            if (fd == null)
                throw new StatementException("No entry associated to the given value in filetable");
            else{
                fd.close();
                filetable.remove(stringValue);
            }
        }
        else
            throw new StatementException("Expression must be a string");
        return state;
    }

    @Override
    public String toString(){
        return "Close ReadFile(" + this.expression.toString() + ")";
    }
}
