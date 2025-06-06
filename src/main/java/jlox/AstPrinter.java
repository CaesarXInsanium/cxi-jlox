package jlox;
import jlox.Expr;

class AstPrinter implements Expr.Visitor<String> {
  // (* (- 123) (group 45.67))
  public static void main(String[] argsd) {
    Expr expression =
        new Expr.Binary(new Expr.Unary(new Token(TokenType.MINUS, "-", null, 1),
                                       new Expr.Literal(123)),
                        new Token(TokenType.STAR, "*", null, 1),
                        new Expr.Grouping(new Expr.Literal(45.67)));
    System.out.println(new AstPrinter().print(expression));
  }
  String print(Expr expr) { return expr.accept(this); }
  @Override
  public String visitSuperExpr(Expr.Super expr) {
    return new String();
  }
  @Override
  public String visitSetExpr(Expr.Set expr) {
    return "set_expression";
  }
  @Override
  public String visitGetExpr(Expr.Get expr) {
    return "get_expression";
  }
  @Override
  public String visitThisExpr(Expr.This expr) {
    return "this_expression";
  }

  @Override
  public String visitBinaryExpr(Expr.Binary expr) {
    return parenthesize(expr.operator.lexeme, expr.left, expr.right);
  }

  @Override
  public String visitGroupingExpr(Expr.Grouping expr) {
    return parenthesize("group", expr.expression);
  }

  @Override
  public String visitLiteralExpr(Expr.Literal expr) {
    if (expr.value == null)
      return "nil";
    return expr.value.toString();
  }

  @Override
  public String visitUnaryExpr(Expr.Unary expr) {
    return parenthesize(expr.operator.lexeme, expr.right);
  }
  @Override
  public String visitVariableExpr(Expr.Variable expr) {
    return "nill";
  }
  @Override
  public String visitAssignExpr(Expr.Assign expr) {
    return new String();
  }
  @Override
  public String visitLogicalExpr(Expr.Logical expr) {
    return new String();
  }
  @Override
  public String visitCallExpr(Expr.Call expr) {
    return new String();
  }

  private String parenthesize(String name, Expr... exprs) {
    StringBuilder builder = new StringBuilder();

    builder.append("(").append(name);

    for (Expr expr : exprs) {
      builder.append(" ");
      builder.append(expr.accept(this));
    }

    builder.append(")");
    return builder.toString();
  }
}
