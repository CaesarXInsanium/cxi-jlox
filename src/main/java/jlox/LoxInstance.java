package jlox;

import java.util.HashMap;
import java.util.Map;

class LoxInstance {
  private LoxClass klass;
  private final Map<String, Object> fields = new HashMap<String, Object>();
  LoxInstance(LoxClass klass) { this.klass = klass; }
  @Override
  public String toString() {
    return "ClassInstance<" + this.klass.name + ">";
  }
  Object get(Token name) {
    if (fields.containsKey(name.lexeme)) {
      return fields.get(name.lexeme);
    }
    LoxFunction method = klass.findMethod(name.lexeme);
    if (method != null)
      return method.bind(this);

    throw new RuntimeError(name, "Undefined property being acessed: <" +
                                     name.lexeme + "> 404 not found.");
  }
  void set(Token name, Object value) { this.fields.put(name.lexeme, value); }
}
