package Model.Statements;

import Exceptions.ADTsExceptions;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
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

        if(sym.isDefined(this.id)){
            ValueInterface val = this.exp.eval(sym);
            TypeInterface id_type = (sym.lookup(this.id)).getType();

            if(val.getType().equals(id_type)){
                sym.update(this.id, val);
            }
            else
                throw new StatementException("Declared variable and type of the expression do not match.");
        }
        else
            throw new StatementException("The variable " + this.id + " was not declared beforehand.");
        //TODO : come back to  set the exestack and symtable ???
        return state;

    }

    @Override
    public String toString(){
        return this.id + " = " + this.exp.toString();
    }
}
