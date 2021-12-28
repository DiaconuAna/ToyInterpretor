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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class OpenReadFileStatement implements StatementInterface{

    ExpressionInterface expression;

    public OpenReadFileStatement(ExpressionInterface e){
        this.expression = e;
    }

    public ExpressionInterface getExpression(){
        return this.expression;
    }

    public void setExpression(ExpressionInterface expr){
        this.expression = expr;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, ADTsExceptions, FileNotFoundException {
        StackInterface<StatementInterface> statementStack = state.getExecutionStack();
        DictInterface<String, ValueInterface> sym = state.getSymbolTable();
        DictInterface<StringValue, BufferedReader> filetable = state.getFileTable();
        HeapInterface<ValueInterface> heaptable = state.getHeapTable();


        //StatementInterface current_statement = statementStack.pop();
        ValueInterface val = this.expression.eval(sym, heaptable);
        if(val.getType().equals(new StringType())){
            StringValue stringVal = (StringValue)val;
            if(filetable.isDefined(stringVal)){
                throw new StatementException("File is already opened.");
            }
            else{
                BufferedReader fd;
                try {
                    fd = new BufferedReader(new FileReader(stringVal.getValue()));
                    filetable.add(stringVal, fd);
                }
                catch (IOException exp){
                    throw new StatementException("File could not be opened" + exp.getMessage());
                }
            }
        }
        else
            throw new StatementException("Expression must be of type String!");
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new OpenReadFileStatement(expression.deepCopy());
    }

    //G|- exp:string
    //--------------------------
    //G|- openRFile(exp):void, G

    @Override
    public DictInterface<String, TypeInterface> typecheck(DictInterface<String, TypeInterface> typeEnv) throws StatementException, ExpressionException, ADTsExceptions {
        TypeInterface expression_type = this.expression.typecheck(typeEnv);

        if(expression_type.equals(new StringType())){
            return typeEnv;
        }
        else
            throw new StatementException("Open Read File: expression must be of string type");
    }

    @Override
    public String toString(){
        return "Open file(" + this.expression.toString() + ") for reading";
    }
}
