package Model.Statements;

import Exceptions.ADTsExceptions;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.ADTs.DictInterface;
import Model.ADTs.HeapInterface;
import Model.ADTs.StackInterface;
import Model.Expression.ExpressionInterface;
import Model.ProgramState;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Types.TypeInterface;
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
        HeapInterface<ValueInterface> heaptable = state.getHeapTable();


        //StatementInterface stmt = statementStack.pop();
        if(sym.isDefined(this.variable_name)){
            ValueInterface variable_value = sym.lookup(this.variable_name);
            if(variable_value.getType().equals(new IntType())){
                ValueInterface val = this.expression.eval(sym, heaptable);
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

        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new ReadFile(this.expression.deepCopy(), this.variable_name);
    }

    //G|- exp:string G|-id:int
    //---------------------------------
    //G|- readFile(exp,id):void,G

    @Override
    public DictInterface<String, TypeInterface> typecheck(DictInterface<String, TypeInterface> typeEnv) throws StatementException, ExpressionException, ADTsExceptions {
        TypeInterface expression_type = this.expression.typecheck(typeEnv);
        TypeInterface variable_type = typeEnv.lookup(this.variable_name);

        if(expression_type.equals(new StringType())){
            if(variable_type.equals(new IntType())){
                return typeEnv;
            }
            else
                throw new StatementException("Read File: Variable must be an integer");
        }
        else
            throw new StatementException("Read file: expression must be of type string");
    }

    @Override
    public String toString(){
        return "ReadFile( " + this.expression.toString() + " ; " + this.variable_name.toString() + ") ";
    }
}
