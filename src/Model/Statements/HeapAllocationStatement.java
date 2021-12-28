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

public class HeapAllocationStatement implements StatementInterface{

    String variableName;
    ExpressionInterface expression;

    public HeapAllocationStatement(String var_name, ExpressionInterface expr){
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
            if(sym.lookup(variableName).getType() instanceof ReferenceType){
                ValueInterface expr_val = expression.eval(sym, heaptable);
                TypeInterface variable_type = ((ReferenceType) sym.lookup(variableName).getType()).getInner();
                if(expr_val.getType().equals(variable_type)){
                    int address = heaptable.allocate(expr_val);

                    //update the reference value associated to the variable name such that the new reference value
                    //has the same location type as the result of the expression and the address is equal to the new key generated
                    //in the heap at the previous step
                    sym.update(variableName, new ReferenceValue(address, expr_val.getType()));
                }
                else
                    throw new StatementException("Expression and variable are of different types");
            }
            else
                throw new StatementException("The variable is not of reference type!");
        }
        else
            throw new StatementException("The variable is not declared!");
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new HeapAllocationStatement(variableName, expression);
    }


    //G|- exp:t G|- id:Ref t
    //-----------------------------
    //G|- new(id,exp):void, G

    @Override
    public DictInterface<String, TypeInterface> typecheck(DictInterface<String, TypeInterface> typeEnv) throws StatementException, ExpressionException, ADTsExceptions {
        TypeInterface variable_type = typeEnv.lookup(this.variableName);
        TypeInterface expression_type = this.expression.typecheck(typeEnv);

        if(variable_type.equals(new ReferenceType(expression_type))){
            return  typeEnv;
        }
        else
            throw new StatementException("Heap Allocation: right hand side and left hand side are of different types");
    }

    @Override
    public String toString(){
        return "new(" + this.variableName + ", " + this.expression.toString() + ")";
    }
}
