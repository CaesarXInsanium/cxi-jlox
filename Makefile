JAVA_VERSION = 17
JAVAC=javac
JAVA=java
# -g debuggin information
JAVA_COMPILE_OPTIONS = -g -Xlint:all -Xlint:unchecked -Xdiags:verbose
JAVA_OPTIONS =  -encoding utf-8

SRC=$(wildcard src/main/java/jlox/*.java)
CLASSPATH=classes
MAIN_CLASS=jlox.Lox

default: compile

compile: $(CLASSPATH) src/main/java/jlox/Expr.java src/main/java/jlox/Stmt.java
	$(JAVAC) $(JAVA_COMPILE_OPTIONS) $(SRC) -d $(CLASSPATH)

run: compile
	$(JAVA) $(JAVA_OPTIONS) -cp $(CLASSPATH) $(MAIN_CLASS)


clean: $(CLASSPATH)
	rm -rf $(CLASSPATH)
	rm src/main/java/jlox/Expr.java
	rm src/main/java/jlox/Stmt.java

$(CLASSPATH):
	mkdir $(CLASSPATH)

src/main/java/jlox/Expr.java: src/main/java/jlox/tool/GenerateAst.java
	$(JAVAC) $(JAVA_COMPILE_OPTIONS)  -d $(CLASSPATH) $^
	$(JAVA) -cp $(CLASSPATH) jlox.tool.GenerateAst src/main/java/jlox

src/main/java/jlox/Stmt.java: src/main/java/jlox/Expr.java

tags: $(SRC)
	fd --extension java | xargs ctags

lox.jar: compile
	jar -e jlox.Lox -c lox.jar -C $(CLASSPATH)  
