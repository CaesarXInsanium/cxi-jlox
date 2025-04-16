package jlox;

import java.util.*;

public class Token {
  final TokenType type;
  final String lexeme;
  final Object literal;
  final int line;

  public Token(TokenType type, String lexeme, Object literal, int line) {
    this.type = type;
    this.lexeme = lexeme;
    this.literal = literal;
    this.line = line;
  }

  public String toString() {
    Formatter fmt = new Formatter();
    String format_string =
        "Type: TOKEN_%-18s Object: %-6s Line: %3d Lexeme: %-10s";
    String result = fmt.format(format_string, this.type, this.literal,
                               this.line, this.lexeme)
                        .toString();
    return result;
  }
}
