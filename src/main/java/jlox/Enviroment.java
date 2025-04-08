package jlox;
import java.util.HashMap;
import java.util.Map;

public class Enviroment{
  final Enviroment enclosing;
  private final HashMap<String, Object> values = new HashMap<String, Object>();

  Enviroment(){
    enclosing = null;
  }
  Enviroment(Enviroment parent){
    this.enclosing = parent;
  }

  void define(String name, Object value){
    values.put(name, value);
  }

  Object get(Token name){
    if (values.containsKey(name.lexeme)){
      return values.get(name.lexeme);
    }   
    if (this.enclosing != null) return enclosing.get(name);
    throw new RuntimeError(name, "Undefined variable '" + name.lexeme + "'.");
  }
  void assign(Token name, Object value){

    if(values.containsKey(name.lexeme)){
      values.put(name.lexeme, value);
      return;
    }
    if(this.enclosing != null){
      enclosing.assign(name, value);
    }
    throw new RuntimeError(name, "Undefined Variable '" + name + "'. Attempted assignment.");
  }
}
