JAVA_VERSION = 17
JAVAC=javac
JAVA=java
# -g debuggin information
JAVA_COMPILE_OPTIONS = -g
JAVA_OPTIONS = -Xlint:all -encoding utf-8

SRC=$(wildcard src/main/java/jlox/*.java)
CLASSPATH=classes
MAIN_CLASS=jlox.Lox

default: compile

compile: $(CLASSPATH) src/main/java/jlox/Expr.java
	$(JAVAC) $(SRC) -d $(CLASSPATH)

run: compile
	$(JAVA) $(JAVA_OPTIONS) -cp $(CLASSPATH) $(MAIN_CLASS)


clean: $(CLASSPATH)
	rm -rf $(CLASSPATH)
	rm src/main/java/jlox/Expr.java

$(CLASSPATH):
	mkdir $(CLASSPATH)

src/main/java/jlox/Expr.java: src/main/java/jlox/tool/GenerateAst.java
	$(JAVAC) $(JAVA_COMPILE_OPTIONS)  -d $(CLASSPATH) $^
	$(JAVA) -cp $(CLASSPATH) jlox.tool.GenerateAst src/main/java/jlox

tags: $(SRC)
	fd --extension java | ctags -
