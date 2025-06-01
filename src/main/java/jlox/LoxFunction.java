package jlox;

import java.util.Vector;

public class LoxFunction implements LoxCallable {
  private final Stmt.Function declaration;
  private final Enviroment closure;
  private final boolean isInitializer;

  LoxFunction(Stmt.Function declaration, Enviroment closure,
              boolean isInitializer) {
    this.isInitializer = isInitializer;
    this.closure = closure;
    this.declaration = declaration;
  }
  LoxFunction bind(LoxInstance instance) {
    Enviroment enviroment = new Enviroment(this.closure);
    enviroment.define("This", instance);
    return new LoxFunction(declaration, enviroment, this.isInitializer);
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
    if (isInitializer)
      return closure.getAt(0, "this");
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
