package jlox.tool;
import java.lang.Exception;
import java.io.IOException;

public class GenerateAst {
  public static void main(String[] args) throws IOException {
    if (args.length!= 1) {
      System.err.println("Usage: generate_ast <outputdir>");
      System.exit(64);
    }

  }
}
