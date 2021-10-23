package Model.Statements;

import Exceptions.ADTsExceptions;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.ADTs.DictInterface;
import Model.Types.TypeInterface;
import Model.Value.ValueInterface;
import Model.ProgramState;

public class VarDeclStatement implements StatementInterface {
    String var_name;
    TypeInterface var_type;

    public VarDeclStatement(String name, TypeInterface type){
        this.var_name = name;
        this.var_type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressionException, ADTsExceptions {
        DictInterface<String, ValueInterface> symbolTable = state.getSymbolTable();
        ValueInterface val = this.var_type.defaultVal();
        symbolTable.add(this.var_name, val);
        return state;
    }

    @Override
    public String toString(){
        return this.var_type.toString() + " " + this.var_name;
    }
}
