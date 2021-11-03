package Model.Statements;

import Exceptions.ADTsExceptions;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.ADTs.DictInterface;
import Model.ADTs.StackInterface;
import Model.Expression.ExpressionInterface;
import Model.ProgramState;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.ValueInterface;
import com.sun.jdi.IntegerValue;

import javax.swing.plaf.nimbus.State;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadFile implements StatementInterface{

    ExpressionInterface expression;
    String variable_name;

    public ReadFile(ExpressionInterface exp, String var_name){
        this.expression = exp;
        this.variable_name = var_name;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, ADTsExceptions, IOException {
        StackInterface<StatementInterface> statementStack = state.getExecutionStack();
        DictInterface<String, ValueInterface> sym = state.getSymbolTable();
        DictInterface<StringValue, BufferedReader> filetable = state.getFileTable();

        //StatementInterface stmt = statementStack.pop();
        if(sym.isDefined(this.variable_name)){
            ValueInterface variable_value = sym.lookup(this.variable_name);
            if(variable_value.getType().equals(new IntType())){
                ValueInterface val = this.expression.eval(sym);
                if(val.getType().equals(new StringType())){
                    BufferedReader fd;
                    StringValue stringValue = (StringValue)val;
                    fd = filetable.lookup(stringValue);
                    if (fd == null)
                        throw new StatementException("No entry associated to the given value in filetable");
                    else{
                        String line = fd.readLine();
                        IntValue variableValue;
                        if(line == null)
                            variableValue = new IntValue();
                        else{
                            variableValue = new IntValue(Integer.parseInt(line));
                        }
                        sym.update(this.variable_name, variableValue);
                    }
                }
                else
                    throw new StatementException("Expression must be of String Value");
            }
            else
                throw new StatementException("Variable type must be an int!");
        }
        else
            throw new StatementException("Variable not defined!");

        return state;
    }

    @Override
    public String toString(){
        return "ReadFile( " + this.expression.toString() + " ; " + this.variable_name.toString() + ") ";
    }
}
