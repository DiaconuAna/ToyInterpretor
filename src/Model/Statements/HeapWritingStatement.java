package Model.Statements;

import Exceptions.ADTsExceptions;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.ADTs.DictInterface;
import Model.ADTs.HeapInterface;
import Model.ADTs.StackInterface;
import Model.Expression.ExpressionInterface;
import Model.ProgramState;
import Model.Types.ReferenceType;
import Model.Types.TypeInterface;
import Model.Value.ReferenceValue;
import Model.Value.StringValue;
import Model.Value.ValueInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Ref;

public class HeapWritingStatement implements StatementInterface{
    String variableName;
    ExpressionInterface expression;

    public HeapWritingStatement(String var_name, ExpressionInterface expr){
        this.variableName = var_name;
        this.expression = expr;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, ADTsExceptions, IOException {
        StackInterface<StatementInterface> statementStack = state.getExecutionStack();
        DictInterface<String, ValueInterface> sym = state.getSymbolTable();
        DictInterface<StringValue, BufferedReader> filetable = state.getFileTable();
        HeapInterface<ValueInterface> heaptable = state.getHeapTable();

        if(sym.isDefined(variableName)){
            ValueInterface variable_value = sym.lookup(variableName);
            if(variable_value.getType() instanceof ReferenceType){
                ReferenceValue value = (ReferenceValue)variable_value;
                int address = value.getAddress();

                if(heaptable.isAddress(address)){
                    TypeInterface location = value.getLocationType();

                    if(this.expression.eval(sym, heaptable).getType().equals(location)){
                        ValueInterface expr_eval = this.expression.eval(sym, heaptable);
                        heaptable.update(address, expr_eval);
                    }
                    else
                        throw new StatementException("Expression type differs from variable inner type");
                }
                else
                    throw new StatementException("The address is not in the heap");
            }
            else
                throw new StatementException("Variable is not of Reference Type");
        }
        else
            throw new StatementException("Variable is not defined");
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new HeapWritingStatement(this.variableName, this.expression);
    }


    //G|- exp:t G|- id:Ref t
    //-----------------------------
    //G|- wH(id,exp):void, G

    @Override
    public DictInterface<String, TypeInterface> typecheck(DictInterface<String, TypeInterface> typeEnv) throws StatementException, ExpressionException, ADTsExceptions {
        TypeInterface variable_type = typeEnv.lookup(this.variableName);
        TypeInterface expression_type = this.expression.typecheck(typeEnv);

        if(variable_type.equals(new ReferenceType(expression_type))){
            return typeEnv;
        }
        else
            throw new StatementException("Heap Write: right hand side and left hand side are of different types");
    }

    @Override
    public String toString(){
        return "wH(" + this.variableName + "," + this.expression.toString() + ")";
    }
}
