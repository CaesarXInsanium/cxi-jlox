package jlox;

class RuntimeError extends RuntimeException {
  private static final long serialVersionUID = 42L;
  final Token token;
  RuntimeError(Token token, String message) {
    super(message);
    this.token = token;
  }
}
