package jlox.tool;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Exception;
import java.util.Arrays;
import java.util.Vector;

public class GenerateAst {
  public static void main(String[] args) throws IOException {
    if (args.length != 1) {
      System.err.println("Usage: generate_ast <outputdir>");
      System.exit(64);
    }
    String outputDir = args[0];
    // clang-format off
    String []exprRules = new String[]{
      "Assign   : Token name, Expr value",
      "Binary   : Expr left, Token operator, Expr right",
      "Call     : Expr callee, Token paren, Vector<Expr> arguments",
      "Get      : Expr object, Token name",
      "Grouping : Expr expression",
      "Literal  : Object value",
      "Logical  : Expr left, Token operator, Expr right",
      "Set      : Expr object, Token name, Expr value",
      "Super    : Token keyword, Token method",
      "This     : Token keyword",
      "Unary    : Token operator, Expr right",
      "Variable : Token name"
    };

    defineAst(outputDir, "Expr", exprRules);

    // clang-format off
    String stmtRules[] = new String[]{
      "Block      : Vector<Stmt> statements",
      "Class      : Token name, Expr.Variable superclass, Vector<Stmt.Function> methods",
      "Expression : Expr expression",
      "Function   : Token name, Vector<Token> params, Vector<Stmt> body",
      "If         : Expr condition, Stmt thenBranch, Stmt elseBranch",
      "Print      : Expr expression",
      "Return     : Token keyword, Expr value",
      "Var        : Token name, Expr initializer",
      "While      : Expr condition, Stmt body"
    };

    defineAst(outputDir, "Stmt", stmtRules);
  }
  
  public static void defineAst(
    String outputDir, String baseName, String[] types
  ) throws IOException {
    // TODO: check if proper target directory exists
    // delete all files in directory if they are found
    String path = outputDir + "/" + baseName + ".java";
    PrintWriter writer = new PrintWriter(path, "UTF-8");
    writer.println("package jlox;");
    writer.println();
    writer.println("import java.util.Vector;");
    writer.println();
    writer.println("public abstract class " + baseName + " {");
    defineVisitor(writer, baseName, types);

    for(String type: types){
      String className = type.split(":")[0].trim();
      String fields = type.split(":")[1].trim();
      defineType(writer, baseName, className, fields);
    }

    writer.println();
    writer.println("  abstract <R> R accept (Visitor<R> visitor);");

    writer.println("}");
    writer.close();
  }

  public static void defineType(
    PrintWriter writer, String baseName, String className, String fieldList
  ) {
    writer.println("  static class " + className + " extends " + baseName + " {");
    // constructor
    writer.println("    public " + className + "(" + fieldList + ") {");
    String[] fields  = fieldList.split(", ");
    for (String field : fields){
      String name = field.split(" ")[1];
      writer.println("      this." + name + " = " + name + ";");
    }

    writer.println("    }");
    //visitor pattern
    writer.println();
    writer.println("    @Override");
    writer.println("    <R> R accept(Visitor<R> visitor) {");
    writer.println("      return visitor.visit" + className + baseName + "(this);");
    writer.println("    }");

    //fields
    writer.println();
    for(String field: fields) {
      writer.println("    final " + field + ";");
    }

    writer.println("  }");
  }

  private static void defineVisitor(
    PrintWriter writer, String baseName, String[] types
  ) {
    writer.println("  interface Visitor<R> {");
    for (String type: types ) {
      String typeName = type.split(":")[0].trim();
      writer.println("    R visit" + typeName + baseName + "(" + typeName + " "
        + baseName.toLowerCase() + ");");
    }
    writer.println("  }");
    writer.println();
  }
}
