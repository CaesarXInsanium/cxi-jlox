package jlox;
import java.util.HashMap;
import java.util.Map;

public class Enviroment {
  final Enviroment enclosing;
  private final HashMap<String, Object> values = new HashMap<String, Object>();

  Enviroment() { enclosing = null; }
  Enviroment(Enviroment parent) { this.enclosing = parent; }

  void define(String name, Object value) { values.put(name, value); }

  // goes up until it finds the targeted enviroment up the chain.
  Enviroment ancestor(int distance) {
    Enviroment enviroment = this;
    for (int i = 0; i < distance; i++) {
      enviroment = enviroment.enclosing;
    }
    return enviroment;
  }

  Object getAt(int distance, String name) {
    return ancestor(distance).values.get(name);
  }

  void assignAt(int distance, Token name, Object value) {
    ancestor(distance).values.put(name.lexeme, value);
  }

  Object get(Token name) {
    if (values.containsKey(name.lexeme)) {
      return values.get(name.lexeme);
    }
    if (this.enclosing != null)
      return enclosing.get(name);
    throw new RuntimeError(name, "Undefined variable '" + name.lexeme + "'.");
  }
  void assign(Token name, Object value) {

    if (values.containsKey(name.lexeme)) {
      values.put(name.lexeme, value);
      return;
    }
    if (this.enclosing != null) {
      enclosing.assign(name, value);
    }
    throw new RuntimeError(name, "Undefined Variable '" + name +
                                     "'. Attempted assignment.");
  }
}
