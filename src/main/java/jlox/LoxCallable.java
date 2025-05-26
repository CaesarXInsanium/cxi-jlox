package jlox;

import java.util.Vector;

public interface LoxCallable {
  Object call(Interpreter interpreter, Vector<Object> arguments);
  int arity();
}
