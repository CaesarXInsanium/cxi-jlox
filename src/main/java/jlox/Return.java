package jlox;

class Return extends RuntimeException {
  private static final long serialVersionUID = 69420;
  final Object value;
  Return(Object val) {
    super();
    this.value = val;
  }
}
