package Model.Statements;

import Exceptions.ADTsExceptions;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.ADTs.HeapInterface;
import Model.ProgramState;
import Model.ADTs.DictInterface;
import Model.ADTs.StackInterface;
import Model.Expression.ExpressionInterface;
import Model.Types.TypeInterface;
import Model.Value.ValueInterface;

public class AssignmentStatement implements StatementInterface{
    String id;
    ExpressionInterface exp;

    public AssignmentStatement(String id, ExpressionInterface e){
        this.id = id;
        this.exp = e;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, ADTsExceptions {
        StackInterface<StatementInterface> stmtstack = state.getExecutionStack();
        DictInterface<String, ValueInterface> sym = state.getSymbolTable();
        HeapInterface<ValueInterface> heaptable = state.getHeapTable();

        if(sym.isDefined(this.id)){
            ValueInterface val = this.exp.eval(sym, heaptable);
            TypeInterface id_type = (sym.lookup(this.id)).getType();

            if(val.getType().equals(id_type)){
                sym.update(this.id, val);
            }
            else
                throw new StatementException("Declared variable and type of the expression do not match.");
        }
        else
            throw new StatementException("The variable " + this.id + " was not declared beforehand.");

        return null;

    }

    @Override
    public StatementInterface deepCopy() {
        return new AssignmentStatement(this.id, this.exp.deepCopy());
    }

    //G|- id:t1 G|- exp:t2 t1==t2
    //--------------------------------------
    //G|- id=exp: void,G

    @Override
    public DictInterface<String, TypeInterface> typecheck(DictInterface<String, TypeInterface> typeEnv) throws StatementException, ExpressionException {
        TypeInterface variable_type = typeEnv.lookup(id);
        TypeInterface expression_type = this.exp.typecheck(typeEnv);

        if(variable_type.equals(expression_type))
            return typeEnv;
        else
            throw new StatementException("Assignment: right hand side and left hand side are of different types");
    }

    @Override
    public String toString(){
        return this.id + " = " + this.exp.toString();
    }
}
