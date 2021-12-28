package Model.Statements;

import Exceptions.ADTsExceptions;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.ADTs.DictInterface;
import Model.ADTs.HeapInterface;
import Model.ADTs.StackInterface;
import Model.Expression.ExpressionInterface;
import Model.ProgramState;
import Model.Types.BoolType;
import Model.Types.TypeInterface;
import Model.Value.BoolValue;
import Model.Value.StringValue;
import Model.Value.ValueInterface;

import java.io.BufferedReader;
import java.io.IOException;

public class WhileStatement implements StatementInterface{
    ExpressionInterface expression;
    StatementInterface statement;

    public WhileStatement(ExpressionInterface exp, StatementInterface stmt){
        this.expression = exp;
        this.statement = stmt;
    }


    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, ADTsExceptions, IOException {
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        DictInterface<String, ValueInterface> sym = state.getSymbolTable();
        DictInterface<StringValue, BufferedReader> filetable = state.getFileTable();
        HeapInterface<ValueInterface> heaptable = state.getHeapTable();

        ValueInterface expr_value = this.expression.eval(sym, heaptable);

        if(expr_value.getType().equals(new BoolType())){
            if(expr_value.equals(new BoolValue(true))){
                //Else Stack2={Stmt1 | while (exp1) Stmt1 | Stmt2|...}
                stack.push(new WhileStatement(this.expression, this.statement));
                stack.push(statement);
            }
        }
        else
            throw new StatementException("Expression must be of Boolean type");
        return null;
    }

    @Override
    public StatementInterface deepCopy() {
        return new WhileStatement(this.expression, this.statement);
    }

    //G|- exp:bool G|- stmt:void,G1
    //------------------------------------------
    //G|- while exp stmt:void, G

    @Override
    public DictInterface<String, TypeInterface> typecheck(DictInterface<String, TypeInterface> typeEnv) throws StatementException, ExpressionException, ADTsExceptions {
        TypeInterface expression_type = this.expression.typecheck(typeEnv);
        //DictInterface<String, TypeInterface> statement_type = this.statement.typecheck(typeEnv);

        if(expression_type.equals(new BoolType())){
            this.statement.typecheck(typeEnv.clone());
            return typeEnv;
        }
        else
            throw new StatementException("WHILE condition is not of bool type");



    }

    @Override
    public String toString(){
        return "while(" + this.expression.toString() + "){" + this.statement.toString() + "}";
    }
}
