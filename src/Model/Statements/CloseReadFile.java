package Model.Statements;

import Exceptions.ADTsExceptions;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.ADTs.DictInterface;
import Model.ADTs.HeapInterface;
import Model.ADTs.StackInterface;
import Model.Expression.ExpressionInterface;
import Model.ProgramState;
import Model.Types.StringType;
import Model.Types.TypeInterface;
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
        HeapInterface<ValueInterface> heaptable = state.getHeapTable();

        // StatementInterface stmt = stmtstack.pop();
        ValueInterface val = this.expression.eval(sym, heaptable);
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
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new CloseReadFile(this.expression.deepCopy());
    }


    //G|- exp:string
    //--------------------------
    //G|- closeRFile(exp):void, G

    @Override
    public DictInterface<String, TypeInterface> typecheck(DictInterface<String, TypeInterface> typeEnv) throws StatementException, ExpressionException, ADTsExceptions {
        TypeInterface expression_type = this.expression.typecheck(typeEnv);

        if(expression_type.equals(new StringType())){
            return typeEnv;
        }
        else
            throw new StatementException("Expression for closing the file must be of string type");
    }

    @Override
    public String toString(){
        return "Close ReadFile(" + this.expression.toString() + ")";
    }
}
