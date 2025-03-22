package jlox;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Vector;
import java.util.stream.Stream;

public class Lox {
  public static final Interpreter interpreter = new Interpreter();
    static boolean hadError = false;
    static boolean hadRuntimeError = false;
    
    public static void main(String[] args) {
        try {
            if (args.length > 1) {
                System.out.println("Usage: jlox [script]");
                System.exit(64);
            } else if (args.length == 1) {
                runFile(args[0]);

            } else {
                runPrompt();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Program Exit");
        }
    }

    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));

        if (hadError)
            System.exit(65);
        if(hadRuntimeError) System.exit(70);
    }

    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;) {
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null)
                break;
            run(line);
            hadError = false;
        }
    }

    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        Vector<Token> tokens = scanner.scanTokens();
        for (Token token : tokens) {
            System.out.println(token);
        }

        Parser parser  = new Parser(tokens);
        Vector<Stmt> statements  = parser.parse();
        if (hadError) return;
        interpreter.interpret(statements);
    }

    static void error(int line, String message) {
        report(line, "", message);
    }


    private static void report(int line, String where, String message) {
        System.err.println("[line " + line + "] Error " + where + ": " + message);
        hadError = true;
    }
    static void error(Token token, String message){
      if (token.type == TokenType.EOF){
        report(token.line, "at end ", message);
      } else {
        report(token.line, "at '" + token.lexeme + "'", message);
      }
    }
    static void runtimeError(RuntimeError error){
      System.err.println(error.getMessage() + "\n[line " + error.token.line + "]");
      hadRuntimeError = true;
    }
}
