package jlox;

import java.util.Vector;

public interface LoxCallable {
  public Object call(Interpreter interpreter, Vector<Object> arguments);
  public int arity();
}
