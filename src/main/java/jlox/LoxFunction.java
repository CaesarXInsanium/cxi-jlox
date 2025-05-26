package jlox;

import java.util.Vector;

public class LoxFunction implements LoxCallable {
  private final Stmt.Function declaration;
  private final Enviroment closure;

  LoxFunction(Stmt.Function declaration, Enviroment closure) {
    this.closure = closure;
    this.declaration = declaration;
  }
  @Override
  public Object call(Interpreter interpreter, Vector<Object> arguments) {
    Enviroment enviroment = new Enviroment(this.closure);
    for (int i = 0; i < declaration.params.size(); i++) {
      enviroment.define(declaration.params.get(i).lexeme, arguments.get(i));
    }
    try {
      interpreter.executeBlock(declaration.body, enviroment);
    } catch (Return returnValue) {
      return returnValue.value;
    }
    return null;
  }
  @Override
  public int arity() {
    return declaration.params.size();
  }
  @Override
  public String toString() {
    return "<fn " + declaration.name.lexeme + ">";
  }
}
