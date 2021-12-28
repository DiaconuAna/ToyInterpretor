package Model.Statements;

import Exceptions.ADTsExceptions;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.ADTs.DictInterface;
import Model.ADTs.HeapInterface;
import Model.ProgramState;
import Model.ADTs.ListInterface;
import Model.Expression.ExpressionInterface;
import Model.Types.TypeInterface;
import Model.Value.ValueInterface;

public class PrintStatement implements StatementInterface{
    ExpressionInterface expr;

    public PrintStatement(ExpressionInterface e){
        this.expr = e;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, ADTsExceptions {
        ListInterface<ValueInterface> output = state.getOut();
        HeapInterface<ValueInterface> heaptable = state.getHeapTable();

        output.add(this.expr.eval(state.getSymbolTable(), heaptable));
        state.setOut(output);
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new PrintStatement(this.expr.deepCopy());
    }

    //G|- exp:t
    //---------------------------
    //G|- print(exp): void,G

    @Override
    public DictInterface<String, TypeInterface> typecheck(DictInterface<String, TypeInterface> typeEnv) throws StatementException, ExpressionException {
        this.expr.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString(){
        return "print(" +this.expr.toString()+")";
    }
}
